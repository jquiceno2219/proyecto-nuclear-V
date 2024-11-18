<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {AddService} from "@/models/AddService";
import ParkingFacilityService from "@/services/ParkingFacilityService";
import addServiceRService from "@/services/AddServiceRService";
import type {ParkingFacility} from "@/models/ParkingFacility";

/**
 * Este componente Vue gestiona servicios adicionales para instalaciones de estacionamiento.
 * Utiliza la Composition API para manejar el estado y los ciclos de vida del componente.
 * Al montarse, carga servicios adicionales y estacionamientos mediante llamadas a servicios externos.
 * Permite crear, editar, eliminar y alternar el estado de los servicios adicionales a través de un formulario validado.
 * El diseño es responsivo y amigable, con estilos CSS que aseguran una presentación clara y estructurada.
 */

const addServices = ref<AddService[]>([]);
const newAddService = ref<AddService>({
  id: 0,
  name: "",
  parkingFacility: { id: 0 },
  price: 0,
  status: true
});
const editingAddService = ref<AddService | null>(null);

const parkings = ref<ParkingFacility[]>([]);

onMounted(async () => {
  await loadAddServices();
  await loadParkings();
});

const loadAddServices = async () => {
  try {
    addServices.value = await addServiceRService.getAddService();
  } catch (error) {
    console.error('Error al cargar addServices:', error);
  }
};

const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar estacionamientos:', error);
  }
};

const createAddService = async () => {
  try {
    if (!validateAddServiceForm()) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }

    const addServiceToSend = {
      id: 0,
      name: newAddService.value.name,
      price: newAddService.value.price,
      status: newAddService.value.status,
      parkingFacility: { id: newAddService.value.parkingFacility.id },
    };

    await addServiceRService.createAddService(addServiceToSend);
    await loadAddServices();
    resetForm();
  } catch (error) {
    console.error('Error creando addService:', error);
    alert('Error creando addService: ' + (error as Error).message);
  }
};

const validateAddServiceForm = () => {
  const service = currentAddService.value;
  return service.name && service.price && service.parkingFacility?.id;
};

const editAddService = (addService: AddService) => {
  editingAddService.value = { ...addService };
};

const updateAddService = async () => {
  if (editingAddService.value) {
    try {
      if (!validateAddServiceForm()) {
        alert('Por favor complete todos los campos requeridos');
        return;
      }
      const addServiceToSend = {
        id: editingAddService.value.id,
        name: editingAddService.value.name,
        price: editingAddService.value.price,
        status: editingAddService.value.status,
        parkingFacility: { id: editingAddService.value.parkingFacility.id },
      };

      await addServiceRService.updateAddService(editingAddService.value.id, addServiceToSend);
      await loadAddServices();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar addService:', error);
      alert('Error al actualizar addService: ' + (error as Error).message);
    }
  }
};

const toggleAddServiceStatus = async (addService: AddService) => {
  try {
    const updatedAddService = await addServiceRService.toggleAddServiceStatus(addService.id);
    const index = addServices.value.findIndex(r => r.id === updatedAddService.id);
    if (index !== -1) {
      addServices.value[index] = updatedAddService;
    }
  } catch (error) {
    console.error('Error al alternar estado de addService:', error);
    alert('Error al cambiar estado del servicio adicional: ' + (error as Error).message);
  }
};

const resetForm = () => {
  newAddService.value = {
    id: 0,
    name: "",
    parkingFacility: { id: 0 },
    price: 0,
    status: true
  };
  editingAddService.value = null;
};

const activeAddServices = computed(() => {
  return addServices.value.filter(addService => addService.status);
});

const currentAddService = computed(() => {
  return editingAddService.value || newAddService.value;
});
</script>

<template>
  <div class="container">
    <div class="addService-list">
      <h2>Lista de Servicios Adicionales</h2>
      <table class="addService-table">
        <thead>
        <tr>
          <th>Nombre</th>
          <th>Precio</th>
          <th>Estacionamiento </th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <tr v-if="activeAddServices.length" v-for="addService in activeAddServices" :key="addService.id">
          <td>{{ addService.name }}</td>
          <td>{{ addService.price }}</td>
          <td>{{ parkings.find(p => p.id === addService.parkingFacility.id)?.name || 'N/A' }}</td>
          <td>
            <button @click="toggleAddServiceStatus(addService)">
              {{ addService.status ? 'Eliminar' : 'Recuperar' }}
            </button>
            <button @click="editAddService(addService)">Editar</button>
          </td>
        </tr>
        <tr v-else>
          <td colspan="4">No hay servicios adicionales activos disponibles.</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="addService-form">
      <h2>{{ editingAddService ? 'Editar Servicio Adicional' : 'Crear Servicio Adicional' }}</h2>
      <form @submit.prevent="editingAddService ? updateAddService() : createAddService()">
        <div>
          <input v-model="currentAddService.name" placeholder="Nombre del Servicio Adicional" required />
        </div>
        <div>
          <input v-model="currentAddService.price" placeholder="Precio" required />
        </div>
        <div>
          <select v-model="currentAddService.parkingFacility.id" required>
            <option disabled value="">Seleccionar Estacionamiento</option>
            <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
              {{ parkingFacility.name }}
            </option>
          </select>
        </div>
        <div>
          <input type="checkbox" id="status" v-model="currentAddService.status" />
          <label for="status">Activo</label>
        </div>
        <button type="submit">
          {{ editingAddService ? 'Actualizar Servicio Adicional' : 'Crear Servicio Adicional' }}
        </button>
        <button type="button" @click="resetForm" v-if="editingAddService">
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

.addService-list, .addService-form {
  flex: 1;
  margin: 10px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.addService-list h2, .addService-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 1.5em;
}

.addService-table {
  width: 100%;
  border-collapse: collapse;
}

.addService-table th, .addService-table td {
  border: 1px solid #dee2e6;
  padding: 10px;
  text-align: left;
}

.addService-table th {
  background-color: rgba(0, 0, 0, 0.99);
}

.addService-form div {
  margin-bottom: 20px;
}

.addService-form input, .addService-form select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  background-color: white;
  font-size: 1em;
}

.addService-form button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.addService-form button:hover {
  background-color: #218838;
}
</style>