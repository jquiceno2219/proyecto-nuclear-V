<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {DayOfWeek} from "@/models/DayOfWeek";
import dayOfWeekService from "@/services/DayOfWeekService";

const dayWeeks = ref<DayOfWeek[]>([]);
const newDayWeek = ref<DayOfWeek>({
  dayNumber: 0, dayTitle: "", id: 0, status: true
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
  if (validateDayWeek(newDayWeek.value)) {
    try {
      // Asegurarse de que dayNumber sea un número
      const dayWeekToCreate = {
        ...newDayWeek.value,
        dayNumber: parseInt(newDayWeek.value.dayNumber.toString())
      };
      await dayOfWeekService.createDayOfWeek(dayWeekToCreate);
      resetForm();
      await loadDayWeeks();
    } catch (error) {
      console.error('Error al crear:', error);
    }
  } else {
    console.error('Invalid dayNumber:', newDayWeek.value.dayNumber);
  }
};

const editDayWeek = (dayWeek: DayOfWeek) => {
  // Asegurarse de que dayNumber sea un número al editar
  editingDayWeek.value = {
    ...dayWeek,
    dayNumber: parseInt(dayWeek.dayNumber.toString())
  };
};

const updateDayWeek = async () => {
  if (editingDayWeek.value && validateDayWeek(editingDayWeek.value)) {
    try {
      // Asegurarse de que dayNumber sea un número
      const dayWeekToUpdate = {
        ...editingDayWeek.value,
        dayNumber: parseInt(editingDayWeek.value.dayNumber.toString())
      };
      await dayOfWeekService.updateDayOfWeek(dayWeekToUpdate.id, dayWeekToUpdate);
      resetForm();
      await loadDayWeeks();
    } catch (error) {
      console.error('Error al actualizar:', error);
    }
  } else {
    console.error('Invalid dayNumber:', editingDayWeek.value?.dayNumber);
  }
};

const validateDayWeek = (dayWeek: DayOfWeek): boolean => {
  const dayNumber = parseInt(dayWeek.dayNumber.toString());
  return !isNaN(dayNumber) && dayNumber > 0 && dayNumber <= 7;
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
    dayNumber: 1,
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
        <strong>Número de día:</strong> {{ dayWeek.dayNumber }}<br>
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
        v-model.number="currentDayWeek.dayNumber"
        type="number"
        min="1"
        max="7"
        placeholder="Número de día (1-7)"
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
  width: 100%;
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