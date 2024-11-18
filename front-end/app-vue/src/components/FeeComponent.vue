<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import type { Fee } from "@/models/Fee";
import feeService from "@/services/FeeService";
import type { ParkingFacility } from "@/models/ParkingFacility";
import ParkingFacilityService from "@/services/ParkingFacilityService";
import type { VehicleType } from "@/models/VehicleType";
import vehicleTypeService from "@/services/VehicleTypeService";

/**
 * Este componente Vue gestiona las tarifas de estacionamiento, permitiendo crear, editar y eliminar tarifas asociadas a diferentes tipos de vehículos y facilidades de estacionamiento.
 * Al montarse, carga las tarifas existentes, los tipos de vehículos y las facilidades de estacionamiento desde servicios externos.
 * Proporciona un formulario para crear o editar tarifas, asegurando que todos los campos requeridos estén completos mediante validación.
 * La interfaz muestra una tabla con las tarifas activas y permite realizar acciones como editar el estado de la tarifa o modificar sus detalles.
 * Los estilos CSS garantizan una presentación clara y organizada para facilitar la interacción del usuario.
 */

const fees = ref<Fee[]>([]);
const newFee = ref<Fee>({
  id: 0,
  name: "",
  parkingFacility: { id: 0 },
  price: 0,
  status: true,
  vehicleType: { id: 0 }
});
const editingFee = ref<Fee | null>(null);

const parkings = ref<ParkingFacility[]>([]);
const vehicles = ref<VehicleType[]>([]);

onMounted(async () => {
  await loadFees();
  await loadVehicles();
  await loadParkings();
});

const loadFees = async () => {
  try {
    fees.value = await feeService.getFee();
  } catch (error) {
    console.error('Error al cargar fees:', error);
  }
};

const loadVehicles = async () => {
  try {
    vehicles.value = await vehicleTypeService.getVehicleType();
  } catch (error) {
    console.error('Error al cargar vehicleTypes:', error);
  }
};

const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar car parks:', error);
  }
};

const createFee = async () => {
  try {
    if (!validateFeeForm()) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }

    const feeToSend = {
      id: 0,
      name: newFee.value.name,
      price: newFee.value.price,
      status: newFee.value.status,
      parkingFacility: { id: newFee.value.parkingFacility.id },
      vehicleType: { id: newFee.value.vehicleType.id }
    };

    await feeService.createFee(feeToSend);
    await loadFees();
    resetForm();
  } catch (error) {
    console.error('Error creando fee:', error);
    alert('Error creando fee: ' + (error as Error).message);
  }
};

const validateFeeForm = () => {
  const fee = currentFee.value;
  return fee.name &&
      fee.price &&
      fee.status &&
      fee.parkingFacility?.id &&
      fee.vehicleType?.id;
};

const editFee = (fee: Fee) => {
  editingFee.value = { ...fee };
};

const updateFee = async () => {
  if (editingFee.value) {
    try {
      if (!validateFeeForm()) {
        alert('Por favor complete todos los campos requeridos');
        return;
      }
      const feeToSend = {
        id: editingFee.value.id,
        name: editingFee.value.name,
        price: editingFee.value.price,
        status: editingFee.value.status,
        parkingFacility: { id: editingFee.value.parkingFacility.id },
        vehicleType: { id: editingFee.value.vehicleType.id }
      };

      await feeService.updateFee(editingFee.value.id, feeToSend);
      await loadFees();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar fee:', error);
      alert('Error al actualizar fee: ' + (error as Error).message);
    }
  }
};

const toggleFeeStatus = async (fee: Fee) => {
  try {
    const updatedFee = await feeService.toggleFeeStatus(fee.id);
    const index = fees.value.findIndex(r => r.id === updatedFee.id);
    if (index !== -1) {
      fees.value[index] = updatedFee;
    }
  } catch (error) {
    console.error('Error al alternar estado de fee:', error);
    alert('Error al cambiar estado de la tarifa: ' + (error as Error).message);
  }
};

const resetForm = () => {
  newFee.value = {
    id: 0,
    name: "",
    parkingFacility: { id: 0 },
    price: 0,
    status: true,
    vehicleType: { id: 0 }
  };
  editingFee.value = null;
};

const activeFees = computed(() => {
  return fees.value.filter(fee => fee.status);
});

const currentFee = computed(() => {
  return editingFee.value || newFee.value;
});
</script>

<template>
  <div class="container">
  <div class="fee-list">
    <h2>Lista de Tarifas</h2>
    <table class="fee-table">
      <thead>
      <tr>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Estado</th>
        <th>Estacionamiento</th>
        <th>Tipo de Vehículo</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <tr v-if="activeFees.length" v-for="fee in activeFees" :key="fee.id">
        <td>{{ fee.name }}</td>
        <td>{{ fee.price }}</td>
        <td>{{ fee.status ? 'Activo' : 'Inactivo' }}</td>
        <td>{{ parkings.find(p => p.id === fee.parkingFacility.id)?.name || 'N/A' }}</td>
        <td>{{ vehicles.find(v => v.id === fee.vehicleType.id)?.name || 'N/A' }}</td>
        <td>
          <button @click="toggleFeeStatus(fee)">
            {{ fee.status ? 'Eliminar' : 'Recuperar' }}
          </button>
          <button @click="editFee(fee)">Editar</button>
        </td>
      </tr>
      <tr v-else>
        <td colspan="6">No hay tarifas activas disponibles.</td>
      </tr>
      </tbody>
    </table>
  </div>

  <div class="fee-form">
    <h2>{{ editingFee ? 'Editar Tarifa' : 'Crear Tarifa' }}</h2>
    <form @submit.prevent="editingFee ? updateFee() : createFee()">
      <div>
        <input v-model="currentFee.name" placeholder="Nombre" required />
      </div>
      <div>
        <input v-model="currentFee.price" placeholder="Precio" required />
      </div>
      <div>
        <select v-model="currentFee.parkingFacility.id" required>
          <option disabled value="">Seleccionar Estacionamiento</option>
          <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
            {{ parkingFacility.name }}
          </option>
        </select>
      </div>
      <div>
        <select v-model="currentFee.vehicleType.id" required>
          <option disabled value="">Seleccionar Tipo de Vehículo</option>
          <option v-for="vehicleType in vehicles" :key="vehicleType.id" :value="vehicleType.id">
            {{ vehicleType.name }}
          </option>
        </select>
      </div>
      <div>
        <input type="checkbox" id="status" v-model="currentFee.status" />
        <label for="status">Activo</label>
      </div>
      <button type="submit">
        {{ editingFee ? 'Actualizar Tarifa' : 'Crear Tarifa' }}
      </button>
      <button type="button" @click="resetForm" v-if="editingFee">
        Cancelar
      </button>
    </form>
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

.fee-list, .fee-form {
  flex: 1;
  margin: 10px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.fee-list h2, .fee-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 1.5em;
}

.fee-table {
  width: 100%;
  border-collapse: collapse;
}

.fee-table th, .fee-table td {
  border: 1px solid #dee2e6;
  padding: 10px;
  text-align: left;
}

.fee-table th {
  background-color: rgba(0, 0, 0, 0.99);
}

.fee-form div {
  margin-bottom: 20px;
}

.fee-form input, .fee-form select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  background-color: white;
  font-size: 1em;
}

.fee-form button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.fee-form button:hover {
  background-color: #218838;
}
</style>