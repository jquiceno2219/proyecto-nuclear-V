import axios from "axios";
import type {ReservationStatus} from "@/models/ReservationStatus";

/**
 * Servicio para interactuar con el API de estados de reservas.
 * - `getReservationStatus`: Obtiene la lista de estados de reservas.
 * - `createReservationStatus`: Crea un nuevo estado de reserva.
 * - `updateReservationStatus`: Actualiza un estado de reserva existente por ID.
 * - `toggleReservationStatusStatus`: Cambia el estado de una reserva por ID.
 */


const API_URL = 'http://localhost:8080/api/reservations-status';

export default {
    async getReservationStatus(): Promise<ReservationStatus[]> {
        try {
            const response = await axios.get<ReservationStatus[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createReservationStatus(reservationStatus: ReservationStatus): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, reservationStatus);
        return response.data;
    },

    async updateReservationStatus(id: number, reservationStatus: ReservationStatus): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, reservationStatus);
        return response.data;
    },

    async toggleReservationStatusStatus(id: number): Promise<ReservationStatus> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};
