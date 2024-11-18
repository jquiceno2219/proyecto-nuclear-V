import axios from "axios";
import type {ServiceReservation} from "@/models/ServiceReservation";

/**
 * Servicio para interactuar con el API de reservas de servicios.
 * - `getServiceReservation`: Obtiene la lista de reservas de servicios.
 * - `createServiceReservation`: Crea una nueva reserva de servicio.
 * - `updateServiceReservation`: Actualiza una reserva de servicio existente por ID.
 */


const API_URL = 'http://localhost:8080/api/service-reservations';

export default {
    async getServiceReservation(): Promise<ServiceReservation[]> {
        try {
            const response = await axios.get<ServiceReservation[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createServiceReservation(serviceReservation: ServiceReservation): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, serviceReservation);
        return response.data;
    },

    async updateServiceReservation(id: number, serviceReservation: ServiceReservation): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, serviceReservation);
        return response.data;
    },

};