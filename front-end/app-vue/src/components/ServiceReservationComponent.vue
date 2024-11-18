<script setup lang="ts">
import type {ServiceReservation} from "@/models/ServiceReservation";
import {computed, onMounted, ref} from "vue";
import addServiceRService from "@/services/AddServiceRService";
import type {AddService} from "@/models/AddService";
import type {Reservation} from "@/models/Reservation";
import reservationService from "@/services/ReservationService";
import serviceReservationService from "@/services/ServiceReservationService";

/**
 * Este componente Vue gestiona las reservas de servicios adicionales, permitiendo a los usuarios crear, editar y visualizar estas reservas.
 * Al montarse, carga la lista de reservas de servicios, reservas generales y servicios adicionales desde servicios externos.
 * Proporciona un formulario para crear o editar reservas de servicios, donde los usuarios pueden seleccionar una reserva y un servicio adicional.
 * La interfaz muestra una lista de reservas de servicios activas, incluyendo detalles como las fechas de inicio y fin, el nombre y el precio del servicio adicional.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de reservas de servicios.
 */

const serviceReservations = ref<ServiceReservation[]>([]);
const newServiceReservation = ref<ServiceReservation>({ addService: { id: 0 }, id: 0, reservation: { id: 0 } });
const editingServiceReservation = ref<ServiceReservation | null>(null);

const reservations = ref<Reservation[]>([]);
const addServices = ref<AddService[]>([]);

onMounted(async () => {
  await loadServiceReservations();
  await loadReservations();
  await loadAddServices();
});

const loadServiceReservations = async () => {
  try {
    serviceReservations.value = await serviceReservationService.getServiceReservation();
  } catch (error) {
    console.error('Error al cargar las reservas de servicio:', error);
  }
};

const loadReservations = async () => {
  try {
    reservations.value = await reservationService.getReservation();
  } catch (error) {
    console.error('Error al cargar las reservas:', error);
  }
};

const loadAddServices = async () => {
  try {
    addServices.value = await addServiceRService.getAddService();
  } catch (error) {
    console.error('Error al cargar los servicios adicionales:', error);
  }
};

const createServiceReservation = async () => {
  try {
    if (!validateServiceReservationForm()) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }

    const serviceReservationToSend = {
      id: 0,
      reservation: { id: newServiceReservation.value.reservation.id },
      addService: { id: newServiceReservation.value.addService.id }
    };

    await serviceReservationService.createServiceReservation(serviceReservationToSend);
    await loadServiceReservations();
    resetForm();
  } catch (error) {
    console.error('Error al crear la reserva de servicio:', error);
    alert('Error al crear la reserva de servicio: ' + (error as Error).message);
  }
};

const validateServiceReservationForm = () => {
  const serviceReservation = currentServiceReservation.value;
  return serviceReservation.reservation?.id > 0 && serviceReservation.addService?.id > 0;
};

const editServiceReservation = (serviceReservation: ServiceReservation) => {
  editingServiceReservation.value = { ...serviceReservation };
};

const updateServiceReservation = async () => {
  if (editingServiceReservation.value) {
    try {
      if (!validateServiceReservationForm()) {
        alert('Por favor complete todos los campos requeridos');
        return;
      }
      const serviceReservationToSend = {
        id: editingServiceReservation.value.id,
        reservation: { id: editingServiceReservation.value.reservation.id },
        addService: { id: editingServiceReservation.value.addService.id }
      };
      await serviceReservationService.updateServiceReservation(editingServiceReservation.value.id, serviceReservationToSend);
      await loadServiceReservations();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar la reserva de servicio:', error);
      alert('Error al actualizar la reserva de servicio: ' + (error as Error).message);
    }
  }
};

const resetForm = () => {
  newServiceReservation.value = { addService: { id: 0 }, id: 0, reservation: { id: 0 } };
  editingServiceReservation.value = null;
};

const activeServiceReservations = computed(() => {
  return serviceReservations.value; // Retorna todas las reservas de servicio
});

const currentServiceReservation = computed(() => {
  return editingServiceReservation.value || newServiceReservation.value;
});

// Funciones para obtener detalles de la reserva y servicio adicional
const getReservationDetails = (reservationId: number) => {
  return reservations.value.find(r => r.id === reservationId) || { startDate: 'N/A', endDate: 'N/A', fee: 'N/A' };
};

const getAddServiceDetails = (addServiceId: number) => {
  return addServices.value.find(a => a.id === addServiceId) || { name: 'N/A', price: 'N/A' };
};

// Función para formatear fechas
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Los meses son 0-indexados
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
};

</script>

<template>
  <div class="container">
    <div class="serviceReservation-list">
      <h2>Lista de Reservas de Servicio</h2>
      <table v-if="activeServiceReservations.length">
        <thead>
        <tr>
          <th>Fecha de Inicio</th>
          <th>Fecha de Fin</th>
          <th>Nombre del Servicio Adicional</th>
          <th>Precio del Servicio Adicional</th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="serviceReservation in activeServiceReservations" :key="serviceReservation.id">
          <td>{{ formatDate(getReservationDetails(serviceReservation.reservation.id).startDate) }}</td>
          <td>{{ formatDate(getReservationDetails(serviceReservation.reservation.id).endDate) }}</td>
          <td>{{ getAddServiceDetails(serviceReservation.addService.id).name }}</td>
          <td>{{ getAddServiceDetails(serviceReservation.addService.id).price }}</td>
          <td>
            <button class="action-button" @click="editServiceReservation(serviceReservation)">Editar</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-else>No hay reservas de servicio activas disponibles.</p>
    </div>
    <div class="serviceReservation-form">
      <h2>{{ editingServiceReservation ? 'Editar Reserva de Servicio' : 'Crear Reserva de Servicio' }}</h2>
      <form @submit.prevent="editingServiceReservation ? updateServiceReservation() : createServiceReservation()">
        <div class="form-group">
          <select v-model="currentServiceReservation.reservation.id" required>
            <option disabled value="">Seleccionar Reserva</option>
            <option v-for="reservation in reservations" :key="reservation.id" :value="reservation.id">
              {{ formatDate(reservation.startDate) }} - {{ formatDate(reservation.endDate) }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <select v-model="currentServiceReservation.addService.id" required>
            <option disabled value="">Seleccionar Servicio Adicional</option>
            <option v-for="addService in addServices" :key="addService.id" :value="addService.id">
              {{ addService.name }} - ${{ addService.price }}
            </option>
          </select>
        </div>
        <button class="submit-button" type="submit">
          {{ editingServiceReservation ? 'Actualizar Reserva de Servicio' : 'Crear Reserva de Servicio' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingServiceReservation">
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
  margin: 10px;
  width: 100%;
}

.serviceReservation-list, .serviceReservation-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.serviceReservation-list h2, .serviceReservation-form h2 {
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
  background-color: #72e58c }

.cancel-button {
  background-color: #dc3545;
  color: white;
}

.cancel-button:hover {
  background-color: #c82333;
}
</style>