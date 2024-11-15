<script setup lang="ts">
import type { DailySchedule } from "@/models/DailySchedule";
import { computed, onMounted, ref } from "vue";
import type { Schedule } from "@/models/Schedule";
import type { DayOfWeek } from "@/models/DayOfWeek";
import { ParkingFacility } from "@/models/ParkingFacility";
import ScheduleService from "@/services/ScheduleService";
import ParkingFacilityService from "@/services/ParkingFacilityService";
import DayOfWeekService from "@/services/DayOfWeekService";
import DailyScheduleService from "@/services/DailyScheduleService";
import dailyScheduleService from "@/services/DailyScheduleService";
import dayOfWeekService from "@/services/DayOfWeekService";

const dailys = ref<DailySchedule[]>([]);
const newDaily = ref<DailySchedule>({
  dayOfWeek: {id: 0}, id: 0, parkingFacility: {id: 0}, schedule: {id: 0}, status: true
});
const editingDaily = ref<DailySchedule | null>(null);

const schedules = ref<Schedule[]>([]);
const daysWeek = ref<DayOfWeek[]>([]);
const parkings = ref<ParkingFacility[]>([]);

onMounted(async () => {
  await Promise.all([
    loadDailys(),
    loadSchedules(),
    loadDaysWeek(),
    loadParkings()
  ]);
});

const loadDailys = async () => {
  try {
    dailys.value = await dailyScheduleService.getDaily();

  } catch (error) {
    console.error('Error al cargar dailys:', error);
  }
};

const loadSchedules = async () => {
  try {
    schedules.value = await ScheduleService.getSchedule();
  } catch (error) {
    console.error('Error al cargar schedules:', error);
  }
};

const loadDaysWeek = async () => {
  try {
    daysWeek.value = await DayOfWeekService.getDayOfWeek();
  } catch (error) {
    console.error('Error al cargar días de la semana:', error);
  }
};

const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar parking facilities:', error);
  }
};

const createDaily = async () => {
  try {
    if (!validateDailyForm()) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }

    const selectedDayOfWeek = daysWeek.value.find(day => day.id === currentDaily.value.dayOfWeek.id);

    const dailyToSend = {
      id: 0,
      schedule: { id: currentDaily.value.schedule.id },
      dayOfWeek: { id: currentDaily.value.dayOfWeek.id},
      parkingFacility: { id: currentDaily.value.parkingFacility.id },
      status: currentDaily.value.status
    };

    if (!dailyToSend.dayOfWeek.id) {
      alert('Por favor seleccione un día de la semana válido.');
      return;
    }

    await dailyScheduleService.createDaily(dailyToSend);
    await loadDailys();
    resetForm();
  } catch (error) {
    console.error('Error creando daily:', error);
    alert('Error creando daily: ' + (error as Error).message);
  }
};

const validateDailyForm = () => {
  const daily = currentDaily.value;
  return daily.schedule?.id && daily.dayOfWeek?.id && daily.parkingFacility?.id;
};

const editDaily = (daily: DailySchedule) => {
  editingDaily.value = { ...daily };
};

const updateDaily = async () => {
  if (editingDaily.value) {
    try {
      if (!validateDailyForm()) {
        alert('Por favor complete todos los campos requeridos');
        return;
      }

      const selectedDayOfWeek = daysWeek.value.find(day => day.id === currentDaily.value.dayOfWeek.id);

      const dailyToSend = {
        id: editingDaily.value.id,
        schedule: { id: currentDaily.value.schedule.id },
        dayOfWeek: { id: currentDaily.value.dayOfWeek.id},
        parkingFacility: { id: currentDaily.value.parkingFacility.id },
        status: currentDaily.value.status
      };

      if (!dailyToSend.dayOfWeek.id) {
        alert('Por favor seleccione un día de la semana válido.');
        return;
      }

      await DailyScheduleService.updateDaily(editingDaily.value.id, dailyToSend);
      await loadDailys();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar daily:', error);
      alert('Error al actualizar daily: ' + (error as Error).message);
    }
  }
};

const toggleDailyStatus = async (daily: DailySchedule) => {
  try {
    const updatedDaily = await DailyScheduleService.toggleDailyStatus(daily.id);
    const index = dailys.value.findIndex(r => r.id === updatedDaily.id);
    if (index !== -1) {
      dailys.value[index] = updatedDaily;
    }
    await loadDailys(); // Recargar la lista después de cambiar el estado
  } catch (error) {
    console.error('Error al alternar estado de daily:', error);
    alert('Error al cambiar estado de daily: ' + (error as Error).message);
  }
};

const resetForm = () => {
  newDaily.value = { dayOfWeek: {id: 0}, id: 0, parkingFacility: {id: 0}, schedule: {id: 0}, status: true
  };
  editingDaily.value = null;
};

const activeDailys = computed(() => {
  return dailys.value.filter(daily => daily.status);
});

const currentDaily = computed(() => {
  return editingDaily.value || newDaily.value;
});

// Función para obtener el día de la semana formateado
const getDayOfWeekInfo = (dayId: number) => {
  const day = daysWeek.value.find(d => d.id === dayId);
  return day ? `Día ${day.dayNumber} - ${day.dayTitle}` : 'N/A';
};
</script>

<template>
  <div class="daily-list">
    <h2>Lista de Horarios Diarios</h2>
    <ul v-if="activeDailys.length">
      <li v-for="daily in activeDailys" :key="daily.id" class="daily-item">
        <div class="daily-details">
          <div class="detail-row">
            <strong>Horario:</strong>
            <span>{{
                schedules.find(s => s.id === daily.schedule.id)?.startTime + ' - ' +
                schedules.find(s => s.id === daily.schedule.id)?.endTime || 'N/A'
              }}</span>
          </div>
          <div class="detail-row">
            <strong>Día de la Semana:</strong>
            <span>{{ daysWeek.find(d=>d.id === daily.dayOfWeek.id)?.dayTitle }}</span>
          </div>
          <div class="detail-row">
            <strong>Estacionamiento:</strong>
            <span>{{ parkings.find(p => p.id === daily.parkingFacility.id)?.name || 'N/A' }}</span>
          </div>
        </div>
        <div class="daily-actions">
          <button @click="toggleDailyStatus(daily)" class="action-button delete">
            {{ daily.status ? 'Eliminar' : 'Recuperar' }}
          </button>
          <button @click="editDaily(daily)" class="action-button edit">Editar</button>
        </div>
      </li>
    </ul>
    <p v-else>No hay horarios diarios activos disponibles.</p>
  </div>

  <div class="daily-form">
    <h2>{{ editingDaily ? 'Editar Horario Diario' : 'Crear Horario Diario' }}</h2>
    <form @submit.prevent="editingDaily ? updateDaily() : createDaily()">
      <div class="form-group">
        <label>Horario:</label>
        <select v-model="currentDaily.schedule.id" required>
          <option value="">Seleccionar Horario</option>
          <option v-for="schedule in schedules" :key="schedule.id" :value="schedule.id">
            {{ schedule.startTime }} - {{ schedule.endTime }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Día de la Semana:</label>
        <select v-model="currentDaily.dayOfWeek.id" required>
          <option value="">Seleccionar Día</option>
          <option v-for="dayOfWeek in daysWeek" :key="dayOfWeek.id" :value="dayOfWeek.id">
            Día {{ dayOfWeek.dayNumber }} - {{ dayOfWeek.dayTitle }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Estacionamiento:</label>
        <select v-model="currentDaily.parkingFacility.id" required>
          <option value="">Seleccionar Estacionamiento</option>
          <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
            {{ parkingFacility.name }}
          </option>
        </select>
      </div>

      <div class="form-group checkbox">
        <input type="checkbox" id="status" v-model="currentDaily.status" />
        <label for="status">Activo</label>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-button">
          {{ editingDaily ? 'Actualizar Horario' : 'Crear Horario' }}
        </button>
        <button type="button" @click="resetForm" v-if="editingDaily" class="cancel-button">
          Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.daily-list, .daily-form {
  margin: 20px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;

  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.daily-list h2, .daily-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 1.5em;
}

.daily-list ul {
  list-style-type: none;
  padding: 0;
}

.daily-item {
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
  border: 1px solid #dee2e6;
}

.daily-details {
  margin-bottom: 15px;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  padding: 4px 0;
}

.detail-row strong {
  color: #495057;
  min-width: 150px;
}

.daily-actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.action-button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s;
}

.action-button.delete {
  background-color: #dc3545;
  color: white;
}

.action-button.edit {
  background-color: #007bff;
  color: white;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: #495057;
  font-weight: 500;
}

select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  background-color: white;
  font-size: 1em;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.submit-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}
</style>