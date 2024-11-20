import axios from "axios";
import type {Reservation} from "@/models/Reservation";

/**
 * Servicio para interactuar con el API de reservas.
 * - `getReservation`: Obtiene la lista de reservas.
 * - `createReservation`: Crea una nueva reserva.
 * - `updateReservation`: Actualiza una reserva existente por ID.
 */


const API_URL = 'http://localhost:8080/api/reservations';

export default {
    async getReservation(): Promise<Reservation[]> {
        try {
            const response = await axios.get<Reservation[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createReservation(reservation: Reservation): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, reservation);
        return response.data;
    },

    async updateReservation(id: number, reservation: Reservation): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, reservation);
        return response.data;
    },

};