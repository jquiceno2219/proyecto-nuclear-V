<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import parkingSpotService from "@/services/ParkingSpotService";
import type {ParkingSpot} from "@/models/ParkingSpot";
import type {ParkingFacility} from "@/models/ParkingFacility";
import ParkingFacilityService from "@/services/ParkingFacilityService";


const spots = ref<ParkingSpot[]>([]);
const newSpot = ref<ParkingSpot>({id: 0, parkingFacility: {id: 0}, spotNumber: 0, status: true});
const editingSpot = ref<ParkingSpot | null>(null);

const parkings = ref<ParkingFacility[]>([]);

onMounted(async () => {
  await loadSpots();
  await loadParkings()
});

// Cargar spots desde el servicio
const loadSpots = async () => {
  try {
    spots.value = await parkingSpotService.getSpot();
  } catch (error) {
    console.error('Error al cargar spots:', error);
  }
};

const loadParkings= async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar car parks:', error);
  }
};

// Crear un nuevo rol
const createSpot = async () => {
  try {
    if (!validateSpotForm()) {
      alert('Please complete all required fields');
      return;
    }
    const spotToSend = {
      id: 0,
      spotNumber: newSpot.value.spotNumber,
      status: newSpot.value.status,
      parkingFacility: { id: newSpot.value.parkingFacility.id }
    };

    await parkingSpotService.createSpot(spotToSend);
    await loadSpots();
    resetForm();
  } catch (error) {
    console.error('Error al crear spot:', error);
  }
};

const validateSpotForm = () => {
  const spot = currentSpot.value;
  return spot.spotNumber &&
      spot.parkingFacility?.id
};

// Iniciar la edición
const editspot = (spot: ParkingSpot) => {
  editingSpot.value = { ...spot }; // Clonar el rol para editarlo
};

// Actualizar un rol
const updatespot = async () => {
  if (editingSpot.value) {
    try {
      if (!validateSpotForm()) {
        alert('Please complete all required fields');
        return;
      }
      const spotToSend = {
        id: 0,
        spotNumber: newSpot.value.spotNumber,
        status: newSpot.value.status,
        parkingFacility: {id: newSpot.value.parkingFacility.id}
      };
      await parkingSpotService.updateSpot(editingSpot.value.id, spotToSend);
      await loadSpots();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar spot:', error);
      alert('Error al actualizar spot: ' + (error as Error).message);
    }
  }
};

// Alternar estado de un rol
const toggleSpotStatus = async (spot: ParkingSpot) => {
  try {
    const updatedSpot = await parkingSpotService.toggleSpotStatus(spot.id);
    const index = spots.value.findIndex(r => r.id === updatedSpot.id);
    if (index !== -1) {
      spots.value[index] = updatedSpot; // Actualizar el rol en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de spot:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newSpot.value = {id: 0, parkingFacility: {id: 0}, spotNumber: 0, status: true};
  editingSpot.value = null; // Reiniciar el rol en edición
};

// Computed property para filtrar spots activos
const activeSpots = computed(() => {
  return spots.value.filter(spot => spot.status);
});

// Computed properties para manejar el formulario
const currentSpot = computed(() => {
  return editingSpot.value || newSpot.value;
});
</script>

<template>
  <div class="spot-list">
    <h2>spots List</h2>
    <ul v-if="activeSpots.length">
      <li v-for="spot in activeSpots" :key="spot.id">
        <div class="spot-details">
        <strong>Spot Number:</strong> {{ spot.spotNumber }}<br>
        <strong>Parking Facility:</strong>
        <span>{{ parkings.find(p => p.id === spot.parkingFacility.id)?.name || 'N/A' }}</span><br>
        </div>
        <button @click="toggleSpotStatus(spot)">
          {{ spot.status ? 'Delete' : 'Recuperate' }}
        </button>
        <button @click="editspot(spot)">Edit</button>
        <hr />
      </li>
    </ul>
    <p v-else>No active spots available.</p>
  </div>

  <div class="spot-form">
    <h2>{{ editingSpot ? 'Edit ParkingSpot' : 'Create ParkingSpot' }}</h2>
    <input v-model="currentSpot.spotNumber" placeholder="Parking Spot Number" />

    <select v-model="currentSpot .parkingFacility.id" required>
      <option disabled value="">Select Parking Facility</option>
      <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
        {{ parkingFacility.name }}
      </option>
    </select>

    <input type="checkbox" v-model="currentSpot.status" />
    <label for="status">Active</label>
    <button @click="editingSpot ? updatespot() : createSpot()">
      {{ editingSpot ? 'Update ParkingSpot' : 'Create ParkingSpot' }}
    </button>
    <button @click="resetForm" v-if="editingSpot">Cancel</button>
  </div>
</template>

<style scoped>
.spot-list, .spot-form {
  margin: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.spot-list h2, .spot-form h2 {
  margin-bottom: 10px;
}

.spot-list ul {
  list-style-type: none;
  padding: 0;
}

.spot-list li {
  margin-bottom: 10px;
}
</style>