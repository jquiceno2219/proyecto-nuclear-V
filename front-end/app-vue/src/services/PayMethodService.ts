import axios from "axios";
import type {PayMethod} from "@/models/PayMethod";

/**
 * Servicio para interactuar con el API de métodos de pago.
 * - `getPayMethod`: Obtiene la lista de métodos de pago.
 * - `createPayMethod`: Crea un nuevo método de pago.
 * - `updatePayMethod`: Actualiza un método de pago existente por ID.
 * - `togglePayMethodStatus`: Cambia el estado de un método de pago por ID.
 */

const API_URL = 'http://localhost:8080/api/pay-methods';

export default {

    async getPayMethod(): Promise<PayMethod[]> {
        try {
            const response = await axios.get<PayMethod[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },


    async createPayMethod(payMethod: PayMethod): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, payMethod);
        return response.data;
    },

    async updatePayMethod(id: number, payMethod: PayMethod): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, payMethod);
        return response.data;
    },

    async togglePayMethodStatus(id: number): Promise<PayMethod> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};

