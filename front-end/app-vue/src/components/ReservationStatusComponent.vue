<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {ReservationStatus} from "@/models/ReservationStatus";
import reservationStatusService from "@/services/ReservationStatusService";

/**
 * Este componente Vue gestiona los estados de reserva, permitiendo crear, editar y eliminar registros de estos estados.
 * Al montarse, carga la lista de estados de reserva existentes desde un servicio externo.
 * Proporciona un formulario para crear o editar estados de reserva, permitiendo al usuario ingresar el nombre y el estado del mismo.
 * La interfaz muestra una lista de estados de reserva activos y permite realizar acciones como editar o eliminar cada estado.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de estados de reserva.
 */

const reservations = ref<ReservationStatus[]>([]);
const newReservation = ref<ReservationStatus>({ id: 0, name: '', status: true });
const editingReservation = ref<ReservationStatus | null>(null); // Para almacenar que se está editando

onMounted(async () => {
  await loadReservations();
});

// Cargar reservations desde el servicio
const loadReservations = async () => {
  try {
    reservations.value = await reservationStatusService.getReservationStatus();
  } catch (error) {
    console.error('Error al cargar reservations:', error);
  }
};

// Crear un nuevo
const createReservation = async () => {
  try {
    await reservationStatusService.createReservationStatus(newReservation.value);
    resetForm(); // Reiniciar el formulario
    await loadReservations(); // Recargar reservations
  } catch (error) {
    console.error('Error al crear:', error);
  }
};

// Iniciar la edición
const editReservation = (reservation: ReservationStatus) => {
  editingReservation.value = { ...reservation }; // Clonar para editarlo
};

// Actualizar
const updateReservation = async () => {
  if (editingReservation.value) {
    try {
      await reservationStatusService.updateReservationStatus(editingReservation.value.id, editingReservation.value);
      resetForm(); // Reiniciar el formulario
      await loadReservations(); // Recargar reservations
    } catch (error) {
      console.error('Error al actualizar:', error);
    }
  }
};

// Alternar estado
const toggleReservationStatus = async (reservation: ReservationStatus) => {
  try {
    const updatedReservation = await reservationStatusService.toggleReservationStatusStatus(reservation.id);
    const index = reservations.value.findIndex(r => r.id === updatedReservation.id);
    if (index !== -1) {
      reservations.value[index] = updatedReservation; // Actualizar en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newReservation.value = { id: 0, name: '', status: true };
  editingReservation.value = null; // Reiniciar en edición
};

// Computed property para filtrar reservations activos
const activeReservations = computed(() => {
  return reservations.value.filter(reservation => reservation.status);
});

// Computed properties para manejar el formulario
const currentReservation = computed(() => {
  return editingReservation.value || newReservation.value;
});
</script>

<template>
  <div class="container">
    <div class="reservations-list">
      <h2>Reservations List</h2>
      <ul v-if="activeReservations.length">
        <li v-for="reservation in activeReservations" :key="reservation.id" class="reservation-item">
          <strong>Name:</strong> {{ reservation.name }}<br>
          <div class="reservation-actions">
            <button class="action-button" @click="toggleReservationStatus(reservation)">Delete</button>
            <button class="action-button" @click="editReservation(reservation)">Edit</button>
          </div>
          <hr />
        </li>
      </ul>
      <p v-else>No active reservations available.</p>
    </div>

    <div class="reservations-form">
      <h2>{{ editingReservation ? 'Edit Reservation' : 'Create Reservation' }}</h2>
      <form @submit.prevent="editingReservation ? updateReservation() : createReservation()">
        <div class="form-group">
          <input class="form-input" v-model="currentReservation.name" placeholder="Reservation Name" required />
        </div>
        <div class="form-group">
          <input type="checkbox" id="status" v-model="currentReservation.status" />
          <label for="status">Active</label>
        </div>
        <button class="submit-button" type="submit">
          {{ editingReservation ? 'Update Reservation' : 'Create Reservation' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingReservation">Cancel</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  width: 150%;
}

.reservations-list, .reservations-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.reservations-list h2, .reservations-form h2 {
  margin-bottom: 10px;
}

.reservations-list ul {
  list-style-type: none;
  padding: 0;
}

.reservations-list li {
  margin-bottom: 10px;
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