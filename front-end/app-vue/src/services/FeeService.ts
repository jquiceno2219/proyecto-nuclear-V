import axios from "axios";
import type {Fee} from "@/models/Fee";

/**
 * Servicio para interactuar con el API de tarifas.
 * - `getFee`: Obtiene la lista de tarifas.
 * - `createFee`: Crea una nueva tarifa.
 * - `updateFee`: Actualiza una tarifa existente por ID.
 * - `toggleFeeStatus`: Cambia el estado de una tarifa por ID.
 */


const API_URL = 'http://localhost:8080/api/fee';

export default {
    async getFee(): Promise<Fee[]> {
        try {
            const response = await axios.get<Fee[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createFee(fee: Fee): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, fee);
        return response.data;
    },

    async updateFee(id: number, fee: Fee): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, fee);
        return response.data;
    },

    async toggleFeeStatus(id: number): Promise<Fee> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};
