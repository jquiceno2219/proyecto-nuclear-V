<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import type {Reservation} from "@/models/Reservation";
import reservationService from "@/services/ReservationService";
import type {PayMethod} from "@/models/PayMethod";
import type {ParkingSpot} from "@/models/ParkingSpot";
import type {Fee} from "@/models/Fee";
import type {ReservationStatus} from "@/models/ReservationStatus";
import payMethodService from "@/services/PayMethodService";
import parkingSpotService from "@/services/ParkingSpotService";
import feeService from "@/services/FeeService";
import reservationStatusService from "@/services/ReservationStatusService";

/**
 * Este componente Vue gestiona las reservas de estacionamiento, permitiendo crear, editar y visualizar registros de reservas.
 * Al montarse, carga la lista de reservas existentes, métodos de pago, estados de reserva, espacios de estacionamiento y tarifas desde servicios externos.
 * Proporciona un formulario para crear o editar reservas, asegurando que todos los campos requeridos estén completos mediante validación.
 * La interfaz muestra una tabla con las reservas existentes y permite realizar acciones como editar cada reserva.
 * Los estilos CSS garantizan una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de reservas.
 */

const reservations = ref<Reservation[]>([]);
const newReservation = ref<Reservation>({
  bookDate: new Date().toISOString().split('T')[0],
  endDate: new Date().toISOString().split('T')[0],
  fee: { id: 0 },
  id: 0,
  parkingSpot: { id: 0 },
  payDate: new Date().toISOString().split('T')[0],
  payMethod: { id: 0 },
  reservationStatus: { id: 0 },
  startDate: new Date().toISOString().split('T')[0]
});

const editingReservation = ref<Reservation | null>(null);
const payMethods = ref<PayMethod[]>([]);
const parkingSpots = ref<ParkingSpot[]>([]);
const fees = ref<Fee[]>([]);
const reservationStatus = ref<ReservationStatus[]>([]);

onMounted(async () => {
  await Promise.all([
    loadReservations(),
    loadPayMethods(),
    loadReservationStatus(),
    loadParkingSpots(),
    loadFees()
  ]);

  console.log('Reservations:', reservations.value);
  console.log('Status:', reservationStatus.value);
});

const formatDate = (date: number | string): string => {
  if (!date) return '';
  const d = new Date(date);
  return d.toLocaleDateString();
};


const loadReservations = async () => {
  try {
    const response = await reservationService.getReservation();
    console.log('Reservations Response:', response);
    reservations.value = response;
  } catch (error) {
    console.error('Error loading reservations:', error);
  }
};

const loadReservationStatus = async () => {
  try {
    reservationStatus.value = await reservationStatusService.getReservationStatus();
    console.log('Reservation Status Response:', reservationStatus.value);
  } catch (error) {
    console.error('Error loading status:', error);
  }
};

const loadPayMethods = async () => {
  try {
    payMethods.value = await payMethodService.getPayMethod();
  } catch (error) {
    console.error('Error loading payment methods:', error);
  }
};

const loadParkingSpots = async () => {
  try {
    parkingSpots.value = await parkingSpotService.getSpot();
  } catch (error) {
    console.error('Error loading parking spots:', error);
  }
};

const loadFees = async () => {
  try {
    fees.value = await feeService.getFee();
  } catch (error) {
    console.error('Error loading fees:', error);
  }
};

const createReservation = async () => {
  try {
    if (!validateReservationForm()) {
      alert('Please fill in all required fields');
      return;
    }

    const selectedStatus = reservationStatus.value.find(s => s.id === newReservation.value.reservationStatus.id);

    const reservationToSend = {
      ...newReservation.value,
      bookDate: newReservation.value.bookDate,
      payDate: newReservation.value.payDate,
      startDate: newReservation.value.startDate,
      endDate: newReservation.value.endDate,
      reservationStatus: {
        id: selectedStatus?.id || 0
      }
    };

    await reservationService.createReservation(reservationToSend);
    await loadReservations();
    resetForm();
  } catch (error) {
    console.error('Error creating reservation:', error);
    alert('Error creating reservation: ' + (error as Error).message);
  }
};

const validateReservationForm = () => {
  const reservation = currentReservation.value;
  return reservation.bookDate &&
      reservation.payDate &&
      reservation.startDate &&
      reservation.endDate &&
      reservation.reservationStatus.id &&
      reservation.payMethod?.id &&
      reservation.parkingSpot?.id &&
      reservation.fee?.id;
};

const editReservation = (reservation: Reservation) => {
  editingReservation.value = {
    ...reservation,
    bookDate: reservation.bookDate,
    payDate: reservation.payDate,
    startDate: reservation.startDate,
    endDate: reservation.endDate
  };
};

const updateReservation = async () => {
  if (editingReservation.value) {
    try {
      if (!validateReservationForm()) {
        alert('Please fill in all required fields');
        return;
      }

      const selectedStatus = reservationStatus.value.find(s => s.id === editingReservation.value?.reservationStatus.id);

      const reservationToSend = {
        ...editingReservation.value,
        bookDate: editingReservation.value.bookDate,
        payDate: editingReservation.value.payDate,
        startDate: editingReservation.value.startDate,
        endDate: editingReservation.value.endDate,
        reservationStatus: {
          id: selectedStatus?.id || 0
        }
      };

      await reservationService.updateReservation(editingReservation.value.id, reservationToSend);
      await loadReservations();
      resetForm();
    } catch (error) {
      console.error('Error updating reservation:', error);
      alert('Error updating reservation: ' + (error as Error).message);
    }
  }
};

const resetForm = () => {
  newReservation.value = {
    bookDate: new Date().toISOString().split('T')[0],
    endDate: new Date().toISOString().split('T')[0],
    fee: { id: 0 },
    id: 0,
    parkingSpot: { id: 0 },
    payDate: new Date().toISOString().split('T')[0],
    payMethod: { id: 0 },
    reservationStatus: { id: 0 },
    startDate: new Date().toISOString().split('T')[0]
  };
  editingReservation.value = null;
};

const router = useRouter();

const currentReservation = computed(() => {
  return editingReservation.value || newReservation.value;
});

// Format date for input fields
const formatDateForInput = (date: Date | string | number): string => {
  const d = new Date(date);
  return d.toISOString().split('T')[0];
};

// Check if current user is PUBLIC_USER
const isPublicUser = computed(() => {
  const user = JSON.parse(sessionStorage.getItem('user') || '{}');
  return user.userRole?.name === 'PUBLIC_USER';
});

// Check if reservation can be paid
const canPayReservation = (reservation: Reservation) => {
  const statusName = reservationStatus.value.find(s => s.id === reservation.reservationStatus?.id)?.name;
  return statusName === 'FINISHED' || statusName === 'CANCELLED';
};

// Calculate payment amount based on reservation status and duration
const calculatePaymentAmount = (reservation: Reservation) => {
  const fee = fees.value.find(f => f.id === reservation.fee?.id);
  if (!fee) return 0;

  const statusName = reservationStatus.value.find((s: ReservationStatus) => s.id === reservation.reservationStatus?.id)?.name;

  if (statusName === 'FINISHED') {
    // Calculate total time in hours
    const startTime = new Date(reservation.startDate).getTime();
    const endTime = new Date(reservation.endDate).getTime();
    const hours = Math.ceil((endTime - startTime) / (1000 * 60 * 60));
    return hours * fee.price;
  } else if (statusName === 'CANCELLED') {
    // Cancellation fee (e.g., 20% of the base fee)
    return fee.price * 0.2;
  }

  return 0;
};

// Navigate to payment view
const goToPayment = (reservation: Reservation) => {
  const amount = calculatePaymentAmount(reservation);
  router.push({
    name: 'Payment',
    params: {
      reservationId: reservation.id.toString(),
      amount: amount.toString()
    }
  });
};
</script>

<template>
  <div class="container">
    <div class="content-wrapper">
      <!-- Reservations Table -->
      <div class="reservations-table">
        <h2>Reservations List</h2>
        <div class="table-container" v-if="reservations.length">
          <table>
            <thead>
            <tr>
              <th>Booking Date</th>
              <th>Payment Date</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Status</th>
              <th>Payment Method</th>
              <th>Parking Spot</th>
              <th>Fee</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="reservation in reservations" :key="reservation.id">
              <td>{{ formatDate(reservation.bookDate) }}</td>
              <td>{{ formatDate(reservation.payDate) }}</td>
              <td>{{ formatDate(reservation.startDate) }}</td>
              <td>{{ formatDate(reservation.endDate) }}</td>
<td>{{ reservationStatus.find(s => s.id === reservation.reservationStatus?.id)?.name || 'N/A' }}</td>
              <td>{{ payMethods.find(p => p.id === reservation.payMethod?.id)?.name || 'N/A' }}</td>
              <td>{{ parkingSpots.find(p => p.id === reservation.parkingSpot?.id)?.spotNumber || 'N/A' }}</td>
              <td>{{ fees.find(f => f.id === reservation.fee?.id)?.price || 'N/A' }}</td>
              <td>
                <button class="edit-button" @click="editReservation(reservation)">Edit</button>
                <button
                  v-if="isPublicUser && canPayReservation(reservation)"
                  class="pay-button"
                  @click="goToPayment(reservation)"
                >
                  Pay
                </button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <p v-else class="no-data">No active reservations available.</p>
      </div>

      <!-- Reservation Form -->
      <div class="reservation-form">
        <h2>{{ editingReservation ? 'Edit Reservation' : 'Create Reservation' }}</h2>
        <form @submit.prevent="editingReservation ? updateReservation() : createReservation()">
          <div class="form-group">
            <label>Booking Date:</label>
            <input
              type="date"
              :value="formatDateForInput(currentReservation.bookDate)"
              @input="e => currentReservation.bookDate = (e.target as HTMLInputElement).value"
              required
            />
          </div>

          <div class="form-group">
            <label>Payment Date:</label>
            <input
              type="date"
              :value="formatDateForInput(currentReservation.payDate)"
              @input="e => currentReservation.payDate = (e.target as HTMLInputElement).value"
              required
            />
          </div>

          <div class="form-group">
            <label>Start Date:</label>
            <input
              type="date"
              :value="formatDateForInput(currentReservation.startDate)"
              @input="e => currentReservation.startDate = (e.target as HTMLInputElement).value"
              required
            />
          </div>

          <div class="form-group">
            <label>End Date:</label>
            <input
              type="date"
              :value="formatDateForInput(currentReservation.endDate)"
              @input="e => currentReservation.endDate = (e.target as HTMLInputElement).value"
              required
            />
          </div>

<div class="form-group">
  <label>Status:</label>
  <select class="form-select" v-model="currentReservation.reservationStatus.id" required>
    <option disabled value="0">Select Status</option>
    <option v-for="status in reservationStatus" :key="status.id" :value="status.id">
      {{ status.name }}
    </option>
  </select>
</div>

          <div class="form-group">
            <label>Payment Method:</label>
            <select v-model="currentReservation.payMethod.id" required>
              <option disabled value="0">Select Payment Method</option>
              <option v-for="payMethod in payMethods" :key="payMethod.id" :value="payMethod.id">
                {{ payMethod.name }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label>Parking Spot:</label>
            <select v-model="currentReservation.parkingSpot.id" required>
              <option disabled value="0">Select Parking Spot</option>
              <option v-for="parkingSpot in parkingSpots" :key="parkingSpot.id" :value="parkingSpot.id">
                {{ parkingSpot.spotNumber }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label>Fee:</label>
            <select v-model="currentReservation.fee.id" required>
              <option disabled value="0">Select Fee</option>
              <option v-for="fee in fees" :key="fee.id" :value="fee.id">
                {{ fee.name }} - {{ fee.price }}
              </option>
            </select>
          </div>

          <div class="button-group">
            <button type="submit" class="submit-button">
              {{ editingReservation ? 'Update Reservation' : 'Create Reservation' }}
            </button>
            <button type="button" class="cancel-button" @click="resetForm" v-if="editingReservation">
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  padding: 20px;
  width: 120%;
  margin: 0 auto;
}

.content-wrapper {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}

.reservations-table {
  background: rgba(242, 242, 242, 0);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: rgb(255, 255, 255);
  font-weight: 600;
}

.reservation-form {
  background: rgba(221, 221, 221, 0);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

input, select {
  width: 90%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
}

.submit-button {
  background-color: #4CAF50;
  color: white;
}

.submit-button:hover {
  background-color: #45a049;
}

.cancel-button {
  background-color: #f44336;
  color: white;
}

.cancel-button:hover {
  background-color: #da190b;
}

.edit-button {
  background-color: #2196F3;
  color: white;
}

.edit-button:hover {
  background-color: #0b7dda;
}

.pay-button {
  background-color: #4CAF50;
  color: white;
  margin-left: 8px;
}

.pay-button:hover {
  background-color: #45a049;
}

.no-data {
  text-align: center;
  padding: 20px;
  color: #666;
}

h2 {
  color: #ffffff;
  margin-bottom: 20px;
}
</style>
