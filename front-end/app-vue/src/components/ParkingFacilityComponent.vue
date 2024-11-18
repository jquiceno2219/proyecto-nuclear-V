<script setup lang="ts">
import type { ParkingFacility } from "@/models/ParkingFacility";
import { computed, onMounted, ref } from "vue";
import ParkingFacilityService from "@/services/ParkingFacilityService";

/**
 * Este componente Vue gestiona las instalaciones de estacionamiento, permitiendo crear, editar y eliminar registros de estacionamientos.
 * Al montarse, carga la lista de estacionamientos existentes desde un servicio externo.
 * Proporciona un formulario para crear o editar instalaciones de estacionamiento, asegurando que todos los campos requeridos estén completos.
 * La interfaz muestra una tabla con las instalaciones de estacionamiento activas y permite realizar acciones como editar el estado de cada instalación o modificar sus detalles.
 * Los estilos CSS garantizan una presentación clara y organizada, mejorando la experiencia del usuario en la gestión de estacionamientos.
 */

const parkings = ref<ParkingFacility[]>([]);
const newParking = ref<ParkingFacility>({
  address: "", coordX: "", coordY: "", id: 0, name: "", nit: "", phoneNumber: "", status: true
});
const editingParking = ref<ParkingFacility | null>(null);

onMounted(async () => {
  await loadParkings();
});

// Cargar parkings desde el servicio
const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar parkings:', error);
  }
};

// Crear un nuevo parking
const createParking = async () => {
  try {
    await ParkingFacilityService.createParking(newParking.value);
    resetForm(); // Reiniciar el formulario
    await loadParkings(); // Recargar parkings
  } catch (error) {
    console.error('Error al crear parking:', error);
  }
};

// Iniciar la edición de un parking
const editParking = (parking: ParkingFacility) => {
  editingParking.value = { ...parking }; // Clonar el parking para editarlo
};

// Actualizar un parking
const updateParking = async () => {
  if (editingParking.value) {
    try {
      await ParkingFacilityService.updateParking(editingParking.value.id, editingParking.value);
      resetForm(); // Reiniciar el formulario
      await loadParkings(); // Recargar parkings
    } catch (error) {
      console.error('Error al actualizar parking:', error);
    }
  }
};

// Alternar estado de un parking
const toggleParkingStatus = async (parking: ParkingFacility) => {
  try {
    const updatedParking = await ParkingFacilityService.toggleParkingStatus(parking.id);
    const index = parkings.value.findIndex(r => r.id === updatedParking.id);
    if (index !== -1) {
      parkings.value[index] = updatedParking; // Actualizar el parking en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de parking:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newParking.value = { address: "", coordX: "", coordY: "", id: 0, name: "", nit: "", phoneNumber: "", status: true };
  editingParking.value = null; // Reiniciar el parking en edición
};

// Computed property para filtrar parkings activos
const activeParkings = computed(() => {
  return parkings.value.filter(parking => parking.status);
});

// Computed properties para manejar el formulario
const currentParking = computed(() => {
  return editingParking.value || newParking.value;
});
</script>

<template>
  <div class="container">
    <div class="parking-list">
      <h2>Parkings List</h2>
      <table v-if="activeParkings.length">
        <thead>
        <tr>
          <th>Name</th>
          <th>Address</th>
          <th>Phone Number</th>
          <th>Coordinates X</th>
          <th>Coordinates Y</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="parking in activeParkings" :key="parking.id">
          <td>{{ parking.name }}</td>
          <td>{{ parking.address }}</td>
          <td>{{ parking.phoneNumber }}</td>
          <td>{{ parking.coordX }}</td>
          <td>{{ parking.coordY }}</td>
          <td>{{ parking.status ? 'Active' : 'Inactive' }}</td>
          <td>
            <button class="action-button" @click="toggleParkingStatus(parking)">
              {{ parking.status ? 'Delete' : 'Recover' }}
            </button>
            <button class="action-button" @click="editParking(parking)">Edit</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-else>No active parkings available.</p>
    </div>

    <div class="parking-form">
      <h2>{{ editingParking ? 'Edit Parking Facility' : 'Create Parking Facility' }}</h2>
      <form @submit.prevent="editingParking ? updateParking() : createParking()">
        <div class="form-group">
          <input class="form-input" v-model="currentParking.name" placeholder="Parking Facility Name" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentParking.address" placeholder="Parking Facility Address" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentParking.phoneNumber" placeholder="Parking Facility Contact Number" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentParking.coordX" placeholder="Parking Facility X Coordinate" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentParking.coordY" placeholder="Parking Facility Y Coordinate" required />
        </div>
        <div class="form-group">
          <input type="checkbox" id="status" v-model="currentParking.status" />
          <label for="status">Active</label>
        </div>
        <button class="submit-button" type="submit">
          {{ editingParking ? 'Update Parking Facility' : 'Create Parking Facility' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingParking">
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

.parking-list, .parking-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.parking-list h2, .parking-form h2 {
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