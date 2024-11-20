<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {VehicleType} from "@/models/VehicleType";
import vehicleTypeService from "@/services/VehicleTypeService";

/**
 * Este componente Vue gestiona la creación, edición y visualización de tipos de vehículos en el sistema.
 * Al montarse, carga la lista de tipos de vehículos desde un servicio externo.
 * Proporciona un formulario para crear o editar tipos de vehículos, permitiendo a los administradores definir el nombre del tipo y su estado (activo o inactivo).
 * La interfaz muestra una lista de tipos de vehículos activos, con opciones para editar o eliminar.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de tipos de vehículos.
 */

const vehicles = ref<VehicleType[]>([]);
const newVehicle = ref<VehicleType>({ id: 0, name: "", status: true });
const editingVehicle = ref<VehicleType | null>(null);

onMounted(async () => {
  await loadVehicles();
});

const loadVehicles = async () => {
  try {
    vehicles.value = await vehicleTypeService.getVehicleType();
  } catch (error) {
    console.error('Error al cargar vehicles:', error);
  }
};

const createVehicle = async () => {
  try {
    await vehicleTypeService.createVehicleType(newVehicle.value);
    resetForm(); // Reiniciar el formulario
    await loadVehicles(); // Recargar
  } catch (error) {
    console.error('Error al crear vehicle:', error);
  }
};

const editVehicle = (vehicle: VehicleType) => {
  editingVehicle.value = { ...vehicle }; // Clonar para editarlo
};

const updateVehicle = async () => {
  if (editingVehicle.value) {
    try {
      await vehicleTypeService.updateVehicleType(editingVehicle.value.id, editingVehicle.value);
      resetForm(); // Reiniciar el formulario
      await loadVehicles(); // Recargar
    } catch (error) {
      console.error('Error al actualizar vehicles:', error);
    }
  }
};

const toggleVehicleStatus = async (vehicle: VehicleType) => {
  try {
    const updatedVehicle = await vehicleTypeService.toggleVehicleTypeStatus(vehicle.id);
    const index = vehicles.value.findIndex(r => r.id === updatedVehicle.id);
    if (index !== -1) {
      vehicles.value[index] = updatedVehicle; // Actualizar en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de rol:', error);
  }
};

const resetForm = () => {
  newVehicle.value = { id: 0, name: "", status: true };
  editingVehicle.value = null; // Reiniciar en edición
};

const activeVehicles = computed(() => {
  return vehicles.value.filter(vehicle => vehicle.status);
});

const currentVehicle = computed(() => {
  return editingVehicle.value || newVehicle.value;
});
</script>

<template>
  <div class="container">
    <div class="vehicle-list">
      <h2>Lista de Vehículos</h2>
      <ul v-if="activeVehicles.length">
        <li v-for="vehicle in activeVehicles" :key="vehicle.id">
          <strong>Nombre:</strong> {{ vehicle.name }}<br>
          <button @click="toggleVehicleStatus(vehicle)">Eliminar</button>
          <button @click="editVehicle(vehicle)">Editar</button>
          <hr />
        </li>
      </ul>
      <p v-else>No hay vehículos activos disponibles.</p>
    </div>

    <div class="vehicle-form">
      <h2>{{ editingVehicle ? 'Editar Tipo de Vehículo' : 'Crear Tipo de Vehículo' }}</h2>
      <input v-model="currentVehicle.name" placeholder="Nombre del Tipo de Vehículo" />
      <input type="checkbox" v-model="currentVehicle.status" />
      <label for="status">Activo</label>
      <button @click="editingVehicle ? updateVehicle() : createVehicle()">
        {{ editingVehicle ? 'Actualizar Tipo de Vehículo' : 'Crear Tipo de Vehículo' }}
      </button>
      <button @click="resetForm" v-if="editingVehicle">Cancelar</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  width: 120%;
}

.vehicle-list, .vehicle-form {
  flex: 1;
  margin: 10px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.vehicle-list h2, .vehicle-form h2 {
  margin-bottom: 20px;
  color: #ffffff;
  font-size: 1.5em;
}

.vehicle-list ul {
  list-style-type: none;
  padding: 0;
}

.vehicle-list li {
  margin-bottom: 10px;
}

.vehicle-form input {
  margin-bottom: 10px;
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.vehicle-form button {
  margin-top: 10px;
}
</style>