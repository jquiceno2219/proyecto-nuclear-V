<script setup lang="ts">

import type {ParkingFacility} from "@/models/ParkingFacility";
import {computed, onMounted, ref} from "vue";
import ParkingFacilityService from "@/services/ParkingFacilityService";

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

// Crear un nuevo
const createParking = async () => {
  try {
    await ParkingFacilityService.createParking(newParking.value);
    resetForm(); // Reiniciar el formulario
    await loadParkings(); // Recargar parkings
  } catch (error) {
    console.error('Error al crear rol:', error);
  }
};

// Iniciar la edición
const editParking = (parking: ParkingFacility) => {
  editingParking.value = { ...parking }; // Clonar el rol para editarlo
};

// Actualizar un rol
const updateParking = async () => {
  if (editingParking.value) {
    try {
      await ParkingFacilityService.updateParking(editingParking.value.id, editingParking.value);
      resetForm(); // Reiniciar el formulario
      await loadParkings(); // Recargar parkings
    } catch (error) {
      console.error('Error al actualizar rol:', error);
    }
  }
};

// Alternar estado
const toggleParkingStatus = async (parking: ParkingFacility) => {
  try {
    const updatedParking = await ParkingFacilityService.toggleParkingStatus(parking.id);
    const index = parkings.value.findIndex(r => r.id === updatedParking.id);
    if (index !== -1) {
      parkings.value[index] = updatedParking; // Actualizar  en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de rol:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newParking.value = { address: "", coordX: "", coordY: "", id: 0, name: "", nit: "", phoneNumber: "", status: true};
  editingParking.value = null; // Reiniciar el rol en edición
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
  <div class="parking-list">
    <h2>Parkings List</h2>
    <ul v-if="activeParkings.length">
      <li v-for="parking in activeParkings" :key="parking.id">
        <strong>Name:</strong> {{ parking.name }}<br>
        <strong>Address:</strong> {{ parking.address }}<br>
        <strong>Phone Number:</strong> {{ parking.nit }}<br>
        <strong>Coordenates X:</strong> {{ parking.coordX }}<br>
        <strong>Coordenates:</strong> {{ parking.coordY }}<br>

        <button @click="toggleParkingStatus(parking)">Delete</button>
        <button @click="editParking(parking)">Edit</button>
        <hr />
      </li>
    </ul>
    <p v-else>No active parkings available.</p>
  </div>

  <div class="parking-form">
    <h2>{{ editingParking ? 'Edit Parking Facility' : 'Create Parking Facility' }}</h2>
    <input v-model="currentParking.name" placeholder="Parking Facility Name" />
    <input v-model="currentParking.address" placeholder="Parking Facility Address" />
    <input v-model="currentParking.phoneNumber" placeholder="Parking Facility Contact Number" />
    <input v-model="currentParking.coordX" placeholder="Parking Facility X Coordinate" />
    <input v-model="currentParking.coordY" placeholder="Parking Facility Y Coordinate" />
    <input type="checkbox" v-model="currentParking.status" />
    <label for="status">Active</label>
    <button @click="editingParking ? updateParking() : createParking()">
      {{ editingParking ? 'Update Parking Facility' : 'Create Parking Facility' }}
    </button>
    <button @click="resetForm" v-if="editingParking">Cancel</button>
  </div>
</template>


<style scoped>
.parking-list, .parking-form {
  margin: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.parking-list h2, .parking-form h2 {
  margin-bottom: 10px;
}

.parking-list ul {
  list-style-type: none;
  padding: 0;
}

.parking-form li {
  margin-bottom: 10px;
}
</style>