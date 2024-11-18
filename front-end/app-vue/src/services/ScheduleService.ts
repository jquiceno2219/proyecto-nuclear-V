import axios from "axios";
import type {Schedule} from "@/models/Schedule";

/**
 * Servicio para interactuar con el API de horarios.
 * - `getSchedule`: Obtiene la lista de horarios.
 * - `createSchedule`: Crea un nuevo horario.
 * - `updateSchedule`: Actualiza un horario existente por ID.
 * - `toggleScheduleStatus`: Cambia el estado de un horario por ID.
 */


const API_URL = 'http://localhost:8080/api/schedules';

export default {
    async getSchedule(): Promise<Schedule[]> {
        try {
            const response = await axios.get<Schedule[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createSchedule(schedule: Schedule): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, schedule);
        return response.data;
    },

    async updateSchedule(id: number, schedule: Schedule): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, schedule);
        return response.data;
    },

    async toggleScheduleStatus(id: number): Promise<Schedule> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};