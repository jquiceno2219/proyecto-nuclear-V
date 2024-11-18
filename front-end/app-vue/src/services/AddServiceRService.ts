import axios from "axios";
import type {AddService} from "@/models/AddService";

/**
 * Servicio para interactuar con el API de servicios adicionales.
 * - `getAddService`: Obtiene la lista de servicios adicionales.
 * - `createAddService`: Crea un nuevo servicio adicional.
 * - `updateAddService`: Actualiza un servicio adicional existente por ID.
 * - `toggleAddServiceStatus`: Cambia el estado de un servicio adicional por ID.
 */


const API_URL = 'http://localhost:8080/api/add-services';

export default {
    async getAddService(): Promise<AddService[]> {
        try {
            const response = await axios.get<AddService[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createAddService(addService: AddService): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, addService);
        return response.data;
    },

    async updateAddService(id: number, addService: AddService): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, addService);
        return response.data;
    },

    async toggleAddServiceStatus(id: number): Promise<AddService> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};
