import axios from 'axios';
import type { PayMethod } from "@/models/PayMethod";

const API_URL = 'http://localhost:8080/api/pay-methods';

export default {
    /**
     * Obtiene la lista de métodos de pago desde la API.
     * @returns Una promesa con la lista de métodos de pago.
     */
    async getPayMethods(): Promise<PayMethod[]> {
        try {
            const response = await axios.get<PayMethod[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error al obtener los métodos de pago:', error);
            throw error;
        }
    },

    /**
     * Crea un nuevo método de pago.
     * @param payMethod - Los datos del método de pago a crear.
     * @returns Un mensaje de éxito de la API.
     */
    async createPayMethod(payMethod: PayMethod): Promise<string> {
        try {
            const response = await axios.post(`${API_URL}/new`, payMethod);
            return response.data;
        } catch (error) {
            console.error('Error al crear el método de pago:', error);
            throw error;
        }
    },

    /**
     * Actualiza un método de pago existente.
     * @param id - El identificador del método de pago.
     * @param payMethod - Los datos del método de pago a actualizar.
     * @returns Un mensaje de éxito de la API.
     */
    async updatePayMethod(id: number, payMethod: PayMethod): Promise<string> {
        try {
            const response = await axios.put(`${API_URL}/update/${id}`, payMethod);
            return response.data;
        } catch (error) {
            console.error('Error al actualizar el método de pago:', error);
            throw error;
        }
    },

    /**
     * Cambia el estado de un método de pago (activo o inactivo).
     * @param id - El identificador del método de pago.
     * @returns El método de pago actualizado con el nuevo estado.
     */
    async togglePayMethodStatus(id: number): Promise<PayMethod> {
        try {
            const response = await axios.patch(`${API_URL}/status/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error al cambiar el estado del método de pago:', error);
            throw error;
        }
    }
};
