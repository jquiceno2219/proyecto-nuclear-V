<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {DayOfWeek} from "@/models/DayOfWeek";
import dayOfWeekService from "@/services/DayOfWeekService";

/**
 * Este componente Vue gestiona la creación, edición y visualización de días de la semana.
 * Al montarse, carga la lista de días de la semana desde un servicio externo.
 * Proporciona una interfaz para crear nuevos días y editar los existentes, asegurando que todos los campos requeridos estén completos mediante validación.
 * La interfaz muestra una lista de días activos, permitiendo al usuario editar o eliminar días de la semana.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de días de la semana.
 */

const dayWeeks = ref<DayOfWeek[]>([]);
const newDayWeek = ref<DayOfWeek>({
  dayWeekNumber: 0, dayTitle: "", id: 0, status: true
});
const editingDayWeek = ref<DayOfWeek | null>(null);

onMounted(async () => {
  await loadDayWeeks();
});

const loadDayWeeks = async () => {
  try {
    dayWeeks.value = await dayOfWeekService.getDayOfWeek();
  } catch (error) {
    console.error('Error al cargar dayWeeks:', error);
  }
};

const createDayWeek = async () => {
  try {
    if (!validateDaysForm()) {
      alert('Please complete all required fields');
      return;
    }
    console.log('Day to send:', newDayWeek.value); // Agregar esta línea
    const dayToSend = {
      id: 0,
      dayWeekNumber: newDayWeek.value.dayWeekNumber,
      dayTitle: newDayWeek.value.dayTitle,
      status: newDayWeek.value.status
    };
    await dayOfWeekService.createDayOfWeek(dayToSend);
    await loadDayWeeks();
    resetForm();
  } catch (error) {
    console.error('Error al crear day:', error);
  }
};

const validateDaysForm = () => {
  const day = currentDayWeek.value;
  return day.dayWeekNumber > 0 && day.dayTitle;
};

const editDayWeek = (dayWeek: DayOfWeek) => {
  editingDayWeek.value = {...dayWeek};
};

const updateDayWeek = async () => {
  if (editingDayWeek.value) {
    try {
      if (!validateDaysForm()) {
        alert('Please complete all required fields');
        return;
      }
      const dayToSend = {
        id: 0,
        dayWeekNumber: editingDayWeek.value.dayWeekNumber,
        dayTitle: editingDayWeek.value.dayTitle,
        status: editingDayWeek.value.status
      };
      await dayOfWeekService.updateDayOfWeek(editingDayWeek.value.id, dayToSend);
      await loadDayWeeks();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar day:', error);
    }
  }
};

const toggleDayWeekStatus = async (dayWeek: DayOfWeek) => {
  try {
    const updatedDayWeek = await dayOfWeekService.toggleDayOfWeekStatus(dayWeek.id);
    const index = dayWeeks.value.findIndex(r => r.id === updatedDayWeek.id);
    if (index !== -1) {
      dayWeeks.value[index] = updatedDayWeek;
    }
  } catch (error) {
    console.error('Error al alternar estado:', error);
  }
};

const resetForm = () => {
  newDayWeek.value = {
    dayWeekNumber: 0,
    dayTitle: "",
    id: 0,
    status: true
  };
  editingDayWeek.value = null;
};

const activeDayWeeks = computed(() => {
  return dayWeeks.value.filter(dayWeek => dayWeek.status);
});

const currentDayWeek = computed(() => {
  return editingDayWeek.value || newDayWeek.value;
});
</script>

<template>
  <div class="dayWeek-list">
    <h2>Lista de Días de la Semana</h2>
    <ul v-if="activeDayWeeks.length">
      <li v-for="dayWeek in activeDayWeeks" :key="dayWeek.id">
        <strong>Número de día:</strong> {{ dayWeek.dayWeekNumber }}<br>
        <strong>Título del día:</strong> {{ dayWeek.dayTitle }}<br>
        <button @click="toggleDayWeekStatus(dayWeek)">Eliminar</button>
        <button @click="editDayWeek(dayWeek)">Editar</button>
        <hr />
      </li>
    </ul>
    <p v-else>No hay días activos disponibles.</p>
  </div>

  <div class="dayWeek-form">
    <h2>{{ editingDayWeek ? 'Editar Día' : 'Crear Día' }}</h2>
    <input
        v-model.number="currentDayWeek.dayWeekNumber"
        placeholder="Número de día (1-7)" required
    />
    <input
        v-model="currentDayWeek.dayTitle"
        placeholder="Título del día"
    />
    <div>
      <input
          type="checkbox"
          id="status"
          v-model="currentDayWeek.status"
      />
      <label for="status">Activo</label>
    </div>
    <button @click="editingDayWeek ? updateDayWeek() : createDayWeek()">
      {{ editingDayWeek ? 'Actualizar Día' : 'Crear Día' }}
    </button>
    <button v-if="editingDayWeek" @click="resetForm">Cancelar</button>
  </div>
</template>

<style scoped>
.dayWeek-list, .dayWeek-form {
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.dayWeek-list h2, .dayWeek-form h2 {
  margin-bottom: 15px;
  color: #333;
}

.dayWeek-list ul {
  list-style-type: none;
  padding: 0;
}

.dayWeek-list li {
  margin-bottom: 15px;
  padding: 10px;

  border-radius: 4px;
}

.dayWeek-form input[type="number"],
.dayWeek-form input[type="text"] {
  display: block;
  width: 80%;
  margin-bottom: 10px;
  padding: 8px;
  border-radius: 4px;
}

.dayWeek-form button {
  margin-right: 10px;
  padding: 8px 16px;
  background-color: #4CAF50;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dayWeek-form button:last-child {
  background-color: #f44336;
}

button:hover {
  opacity: 0.9;
}
</style>
