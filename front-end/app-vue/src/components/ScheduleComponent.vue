<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import scheduleService from "@/services/ScheduleService";
import type {Schedule} from "@/models/Schedule";

/**
 * Este componente Vue gestiona los horarios, permitiendo crear, editar y eliminar registros de horarios.
 * Al montarse, carga la lista de horarios existentes desde un servicio externo.
 * Proporciona un formulario para crear o editar horarios, permitiendo al usuario ingresar la hora de inicio, la hora de finalización y el estado del horario.
 * La interfaz muestra una lista de horarios activos y permite realizar acciones como editar o eliminar cada horario.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de horarios.
 */

const schedules = ref<Schedule[]>([]);
const newSchedule = ref<Schedule>({endTime: 0, id: 0, startTime: 0, status: true});
const editingSchedule = ref<Schedule | null>(null); // Para almacenar el que se está editando

onMounted(async () => {
  await loadSchedules();
});

// Cargar schedules desde el servicio
const loadSchedules = async () => {
  try {
    schedules.value = await scheduleService.getSchedule();
  } catch (error) {
    console.error('Error al cargar schedules:', error);
  }
};

// Función para convertir HH:mm a minutos
const timeToMinutes = (time: string): number => {
  const [hours, minutes] = time.split(':').map(Number);
  return hours * 60 + minutes;
};

// Función para convertir minutos a HH:mm
const minutesToTime = (minutes: number): string => {
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return `${String(hours).padStart(2, '0')}:${String(mins).padStart(2, '0')}`;
};

// Crear un nuevo rol
const createSchedule = async () => {
  try {
    // Convertir los tiempos a minutos
    newSchedule.value.startTime = timeToMinutes(newSchedule.value.startTime as unknown as string);
    newSchedule.value.endTime = timeToMinutes(newSchedule.value.endTime as unknown as string);

    await scheduleService.createSchedule(newSchedule.value);
    resetForm(); // Reiniciar el formulario
    await loadSchedules(); // Recargar schedules
  } catch (error) {
    console.error('Error al crear rol:', error);
  }
};

// Iniciar la edición de un rol
const editSchedule = (schedule: Schedule) => {
  editingSchedule.value = { ...schedule }; // Clonar el rol para editarlo
};

// Actualizar un rol
const updateSchedule = async () => {
  if (editingSchedule.value) {
    try {
      // Convertir los tiempos a minutos
      editingSchedule.value.startTime = timeToMinutes(editingSchedule.value.startTime as unknown as string);
      editingSchedule.value.endTime = timeToMinutes(editingSchedule.value.endTime as unknown as string);

      await scheduleService.updateSchedule(editingSchedule.value.id, editingSchedule.value);
      resetForm(); // Reiniciar el formulario
      await loadSchedules(); // Recargar schedules
    } catch (error) {
      console.error('Error al actualizar rol:', error);
    }
  }
};

// Alternar estado de un rol
const toggleScheduleStatus = async (schedule: Schedule) => {
  try {
    const updatedSchedule = await scheduleService.toggleScheduleStatus(schedule.id);
    const index = schedules.value.findIndex(r => r.id === updatedSchedule.id);
    if (index !== -1) {
      schedules.value[index] = updatedSchedule; // Actualizar en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de rol:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newSchedule.value = {endTime: 0, id: 0, startTime: 0, status: true};
  editingSchedule.value = null; // Reiniciar en edición
};

// Computed property para filtrar schedules activos
const activeSchedules = computed(() => {
  return schedules.value.filter(schedule => schedule.status);
});

// Computed properties para manejar el formulario
const currentSchedule = computed(() => {
  return editingSchedule.value || newSchedule.value;
});
</script>

<template>
  <div class="schedule-list">
    <h2>Schedules List</h2>
    <ul v-if="activeSchedules.length">
      <li v-for="schedule in activeSchedules" :key="schedule.id">
        <strong>Start Time:</strong> {{ minutesToTime(schedule.startTime) }}<br>
        <strong>End Time:</strong> {{ minutesToTime(schedule.endTime) }}<br>
        <button @click ="toggleScheduleStatus(schedule)">Delete</button>
        <button @click="editSchedule(schedule)">Edit</button>
        <hr />
      </li>
    </ul>
    <p v-else>No active schedules available.</p>
  </div>

  <div class="schedule-form">
    <h2>{{ editingSchedule ? 'Edit Schedule' : 'Create Schedule' }}</h2>
    <input type="time" v-model="currentSchedule.startTime" placeholder="Start Time" />
    <input type="time" v-model="currentSchedule.endTime" placeholder="End Time" />
    <input type="checkbox" v-model="currentSchedule.status" />
    <label for="status">Active</label>
    <button @click="editingSchedule ? updateSchedule() : createSchedule()">
      {{ editingSchedule ? 'Update Schedule' : 'Create Schedule' }}
    </button>
    <button @click="resetForm" v-if="editingSchedule">Cancel</button>
  </div>
</template>

<style scoped>
.schedule-list, .schedule-form {
  margin: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.schedule-list h2, .schedule-form h2 {
  margin-bottom: 10px;
}

.schedule-list ul {
  list-style-type: none;
  padding: 0;
}

.schedule-list li {
  margin-bottom: 10px;
}
</style>