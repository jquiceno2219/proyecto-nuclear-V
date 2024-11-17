<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import type { VehicleTypeDto } from '@/models/VehicleTypeDto.ts';
import vehicleTypeService from '@/services/VehicleTypeService';

const vehicleTypes = ref<VehicleTypeDto[]>([]);
const newVehicleType = ref<VehicleTypeDto>({
  id: 0,
  name: '',
  status: true,
});
const editingVehicleType = ref<VehicleTypeDto | null>(null);

onMounted(async () => {
  await loadVehicleTypes();
});

const loadVehicleTypes = async () => {
  try {
    vehicleTypes.value = await vehicleTypeService.listVehicleTypes();
  } catch (error) {
    console.error('Error loading vehicle types:', error);
  }
};

const createVehicleType = async () => {
  try {
    await vehicleTypeService.createVehicleType(newVehicleType.value);
    await loadVehicleTypes();
    resetForm();
  } catch (error) {
    console.error('Error creating vehicle type:', error);
    alert('Error creating vehicle type: ' + (error as Error).message);
  }
};

const updateVehicleType = async () => {
  if (editingVehicleType.value) {
    try {
      await vehicleTypeService.updateVehicleType(editingVehicleType.value.id, editingVehicleType.value);
      await loadVehicleTypes();
      resetForm();
    } catch (error) {
      console.error('Error updating vehicle type:', error);
      alert('Error updating vehicle type: ' + (error as Error).message);
    }
  }
};

const toggleVehicleTypeStatus = async (vehicleType: VehicleTypeDto) => {
  try {
    const updatedVehicleType = await vehicleTypeService.toggleVehicleTypeStatus(vehicleType.id);
    const index = vehicleTypes.value.findIndex((vt) => vt.id === updatedVehicleType.id);
    if (index !== -1) {
      vehicleTypes.value[index] = updatedVehicleType;
    }
  } catch (error) {
    console.error('Error toggling vehicle type status:', error);
    alert('Error toggling vehicle type status: ' + (error as Error).message);
  }
};

const editVehicleType = (vehicleType: VehicleTypeDto) => {
  editingVehicleType.value = { ...vehicleType };
};

const resetForm = () => {
  newVehicleType.value = { id: 0, name: '', status: true };
  editingVehicleType.value = null;
};

const currentVehicleType = computed(() => editingVehicleType.value || newVehicleType.value);
</script>

<template>
  <div class="vehicle-types">
    <h2>Vehicle Types</h2>
    <ul>
      <li v-for="vehicleType in vehicleTypes" :key="vehicleType.id">
        <span>- {{ vehicleType.name }} ({{ vehicleType.status ? 'Active' : 'Inactive' }}) </span>
        <button @click="editVehicleType(vehicleType)">Edit</button>
        <button @click="toggleVehicleTypeStatus(vehicleType)">
          {{ vehicleType.status ? 'Deactivate' : 'Activate' }}
        </button>
      </li>
    </ul>
    <div class="vehicle-type-form">
      <h2>{{ editingVehicleType ? 'Edit Vehicle Type' : 'Create Vehicle Type' }}</h2>
      <form @submit.prevent="editingVehicleType ? updateVehicleType() : createVehicleType()">
        <input v-model="currentVehicleType.name" placeholder="Vehicle Type Name" required />
        <input type="checkbox" v-model="currentVehicleType.status" id="status" />
        <label for="status">Active</label>
        <button type="submit">
          {{ editingVehicleType ? 'Update Vehicle Type' : 'Create Vehicle Type' }}
        </button>
        <button v-if="editingVehicleType" @click="resetForm">Cancel</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.vehicle-types {
  margin: 20px;
}

.vehicle-types ul {
  list-style: none;
  padding: 0;
}

.vehicle-types li {
  margin-bottom: 10px;
}

.vehicle-type-form {
  margin-top: 20px;
}
</style>
