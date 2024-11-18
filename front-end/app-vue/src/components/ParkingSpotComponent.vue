<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import parkingSpotService from "@/services/ParkingSpotService";
import type {ParkingSpot} from "@/models/ParkingSpot";
import type {ParkingFacility} from "@/models/ParkingFacility";
import ParkingFacilityService from "@/services/ParkingFacilityService";

/**
 * Este componente Vue gestiona la creación, edición y visualización de espacios de estacionamiento.
 * Al montarse, carga la lista de espacios de estacionamiento y las instalaciones de estacionamiento asociadas desde servicios externos.
 * Proporciona una interfaz para crear nuevos espacios y editar los existentes, asegurando que todos los campos requeridos estén completos.
 * Los usuarios pueden alternar el estado de los espacios, ya sea para eliminarlos o recuperarlos.
 * Además, incluye un formulario que permite seleccionar la instalación de estacionamiento a la que pertenece cada espacio.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de espacios de estacionamiento.
 */

const spots = ref<ParkingSpot[]>([]);
const newSpot = ref<ParkingSpot>({ id: 0, parkingFacility: { id: 0 }, spotNumber: 0, status: true });
const editingSpot = ref<ParkingSpot | null>(null);

const parkings = ref<ParkingFacility[]>([]);

onMounted(async () => {
  await loadSpots();
  await loadParkings();
});

// Cargar spots desde el servicio
const loadSpots = async () => {
  try {
    spots.value = await parkingSpotService.getSpot();
  } catch (error) {
    console.error('Error al cargar spots:', error);
  }
};

const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar car parks:', error);
  }
};

// Crear un nuevo spot
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
  return spot.spotNumber && spot.parkingFacility?.id;
};

// Iniciar la edición
const editSpot = (spot: ParkingSpot) => {
  editingSpot.value = { ...spot }; // Clonar el spot para editarlo
};

// Actualizar un spot
const updateSpot = async () => {
  if (editingSpot.value) {
    try {
      if (!validateSpotForm()) {
        alert('Please complete all required fields');
        return;
      }
      const spotToSend = {
        id: 0,
        spotNumber: editingSpot.value.spotNumber,
        status: editingSpot.value.status,
        parkingFacility: { id: editingSpot.value.parkingFacility.id }
      };
      await parkingSpotService.updateSpot(editingSpot .value.id, spotToSend);
      await loadSpots();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar spot:', error);
    }
  }
};

// Alternar estado de un spot
const toggleSpotStatus = async (spot: ParkingSpot) => {
  try {
    const updatedSpot = await parkingSpotService.toggleSpotStatus(spot.id);
    const index = spots.value.findIndex(r => r.id === updatedSpot.id);
    if (index !== -1) {
      spots.value[index] = updatedSpot; // Actualizar el spot en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de spot:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newSpot.value = { id: 0, parkingFacility: { id: 0 }, spotNumber: 0, status: true };
  editingSpot.value = null; // Reiniciar el spot en edición
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
  <div class="container">
    <div class="spot-list">
      <h2>Parking Spots List</h2>
      <table v-if="activeSpots.length">
        <thead>
        <tr>
          <th>Spot Number</th>
          <th>Parking Facility</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="spot in activeSpots" :key="spot.id">
          <td>{{ spot.spotNumber }}</td>
          <td>{{ parkings.find(p => p.id === spot.parkingFacility.id)?.name || 'N/A' }}</td>
          <td>{{ spot.status ? 'Active' : 'Inactive' }}</td>
          <td>
            <button class="action-button" @click="toggleSpotStatus(spot)">
              {{ spot.status ? 'Delete' : 'Recover' }}
            </button>
            <button class="action-button" @click="editSpot(spot)">Edit</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-else>No active spots available.</p>
    </div>

    <div class="spot-form">
      <h2>{{ editingSpot ? 'Edit Parking Spot' : 'Create Parking Spot' }}</h2>
      <form @submit.prevent="editingSpot ? updateSpot() : createSpot()">
        <div class="form-group">
          <input class="form-input" v-model="currentSpot.spotNumber" placeholder="Parking Spot Number" required />
        </div>
        <div class="form-group">
          <select class="form-input" v-model="currentSpot.parkingFacility.id" required>
            <option disabled value="">Select Parking Facility</option>
            <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
              {{ parkingFacility.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <input type="checkbox" id="status" v-model="currentSpot.status" />
          <label for="status">Active</label>
        </div>
        <button class="submit-button" type="submit">
          {{ editingSpot ? 'Update Parking Spot' : 'Create Parking Spot' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingSpot">
          Cancel
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 10px;
  width: 150%;
}

.spot-list, .spot-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.spot-list h2, .spot-form h2 {
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: rgba(0, 0, 0, 0.99);
}

.form-group {
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

.action-button, .submit-button, .cancel-button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.action-button {
  background-color: #679ee8;
  color: white;
}

.action-button:hover {
  background-color: #569ae7;
}

.submit-button {
  background-color: #96f6ab;
  color: white;
}

.submit-button:hover {
  background-color: #72e58c;
}

.cancel-button {
  background-color: #dc3545;
  color: white;
}

.cancel-button:hover {
  background-color: #c82333;
}
</style>