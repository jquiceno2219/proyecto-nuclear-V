<script setup lang="ts">
import type {DailySchedule} from "@/models/DailySchedule";
import {onMounted, ref} from "vue";
import type {Schedule} from "@/models/Schedule";
import type {DayOfWeek} from "@/models/DayOfWeek";
import type {ParkingFacility} from "@/models/ParkingFacility";
import ScheduleService from "@/services/ScheduleService";
import ParkingFacilityService from "@/services/ParkingFacilityService";
import DayOfWeekService from "@/services/DayOfWeekService";
import DailyScheduleService from "@/services/DailyScheduleService";

/**
 * Este componente Vue gestiona la creación y visualización de horarios diarios para diferentes instalaciones de estacionamiento.
 * Al montarse, carga las listas de horarios, días de la semana y instalaciones de estacionamiento desde servicios externos.
 * Proporciona una interfaz para crear nuevos horarios diarios, asegurando que todos los campos requeridos estén completos mediante validación.
 * La interfaz muestra una tabla con los horarios diarios existentes, incluyendo detalles del horario, día de la semana y nombre de la instalación de estacionamiento.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de horarios diarios.
 */

const dailys = ref<DailySchedule[]>([]);
const newDaily = ref<DailySchedule>({
  id: 0,
  dayWeek: { id: 0 , dayTitle: "" },
  parkingFacility: { id: 0 },
  schedule: { id: 0 }
});

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
    dailys.value = await DailyScheduleService.getDaily();
    console.log('Dailys cargados:', dailys.value);
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

    // Imprimir los valores originales para depuración
    console.log('Valores originales:', {
      scheduleId: newDaily.value.schedule.id,
      dayWeekId: newDaily.value.dayWeek.id,
      parkingFacilityId: newDaily.value.parkingFacility.id
    });

    const dailyToSend = {
      schedule: { id: newDaily.value.schedule.id }, // Mantener el ID del horario
      dayWeek: { id: newDaily.value.dayWeek.id , dayTitle: newDaily.value.dayWeek.dayTitle}, // Mantener el ID del día de la semana
      parkingFacility: { id: newDaily.value.parkingFacility.id } // Mantener el ID del estacionamiento
    };

    // Imprimir los valores que se van a enviar
    console.log('Daily a enviar:', dailyToSend);

    await DailyScheduleService.createDaily(dailyToSend);
    await loadDailys();
    resetForm();
  } catch (error) {
    console.error('Error creando daily:', error);
    alert('Error creando daily: ' + (error as Error).message);
  }
};

const validateDailyForm = () => {
  const daily = newDaily.value;
  return daily.schedule?.id && daily.dayWeek?.id && daily.parkingFacility?.id;
};

const resetForm = () => {
  newDaily.value = {
    id: 0,
    dayWeek: { id: 0 , dayTitle: ""},
    parkingFacility: { id: 0 },
    schedule: { id: 0 }
  };
};

const getScheduleTime = (dailyScheduleId: number) => {
  const schedule = schedules.value.find(s => s.id === dailyScheduleId);
  return schedule ? `${schedule.startTime} - ${schedule.endTime}` : 'N/A';
};
const getDayWeekTitle = (dayWeekId: number) => {
  const dayWeek = daysWeek.value.find(d => d.id === dayWeekId);
  return dayWeek ? ` ${dayWeek.dayTitle}` : 'N/A';
};
</script>

<template>
  <div class="container">
    <div class="daily-list">
      <h2>Lista de Horarios Diarios</h2>
      <table class="daily-table">
        <thead>
        <tr>
          <th>Horario</th>
          <th>Día de la Semana</th>
          <th>Estacionamiento</th>
        </tr>
        </thead>
        <tbody>
        <tr v-if="dailys.length" v-for="daily in dailys" :key="daily.id">
          <td>
            {{
              schedules.find(s => s.id === daily.schedule?.id)?.startTime + ' - ' +
              schedules.find(s => s.id === daily.schedule?.id)?.endTime || 'N/A'
            }}
          </td>
          <td>
            {{ daysWeek.find(r => r.id === daily.dayWeek.id)?.dayTitle || 'N/A' }}

          </td>
          <td>
            {{ parkings.find(p => p.id === daily.parkingFacility?.id)?.name || 'N/A' }}
          </td>
        </tr>
        <tr v-else>
          <td colspan="3">No hay horarios diarios disponibles.</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="daily-form">
      <h2>Crear Horario Diario</h2>
      <form @submit.prevent="createDaily()">
        <div class="form-group">
          <label>Horario:</label>
          <select v-model="newDaily.schedule.id" required>
            <option value="">Seleccionar Horario</option>
            <option v-for="schedule in schedules" :key="schedule.id" :value="schedule.id">
              {{ schedule.startTime }} - {{ schedule.endTime }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>Día de la Semana:</label>
          <select v-model="newDaily.dayWeek.id" required>
            <option value="">Seleccionar Día</option>
            <option v-for="dayWeek in daysWeek" :key="dayWeek.id" :value="dayWeek.id">
              Día {{ dayWeek.dayWeekNumber }} - {{ dayWeek.dayTitle }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>Estacionamiento:</label>
          <select v-model="newDaily.parkingFacility.id" required>
            <option value="">Seleccionar Estacionamiento</option>
            <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
              {{ parkingFacility.name }}
            </option>
          </select>
        </div>

        <div class="form-actions">
          <button type="submit" class="submit-button">Crear Horario</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  width: 150%;
}

.daily-list, .daily-form {
  flex: 1;
  margin: 10px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.daily-list h2, .daily-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 1.5em;
}

.daily-table {
  width: 100%;
  border-collapse: collapse;
}

.daily-table th, .daily-table td {
  border: 1px solid #dee2e6;
  padding: 10px;
  text-align: left;
}

.daily-table th {
  background-color: rgb(255, 255, 255);
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

.submit-button:hover {
  background-color: #218838;
}
</style>
