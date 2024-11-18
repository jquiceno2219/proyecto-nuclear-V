import axios from 'axios';
import type {User} from '@/models/User';

/**
 * Servicio para interactuar con el API de usuarios.
 * - `getUser`: Obtiene la lista de usuarios.
 * - `createUser`: Crea un nuevo usuario.
 * - `updateUser`: Actualiza un usuario existente por ID.
 * - `toggleUserStatus`: Cambia el estado de un usuario por ID.
 */


const API_URL = 'http://localhost:8080/api/users';

export default {
    async getUser(): Promise<User[]> {
        try {
            const response = await axios.get<User[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createUser(user: User): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, user);
        return response.data;
    },

    async updateUser(id: number, user: User): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, user);
        return response.data;
    },

    async toggleUserStatus(id: number): Promise<User> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};