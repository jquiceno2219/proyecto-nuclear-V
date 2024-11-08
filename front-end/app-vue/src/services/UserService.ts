import axios from 'axios';
import type { User } from '@/models/User';


const API_URL = 'http://localhost:8080/api';

const axiosInstance = axios.create({
    baseURL: API_URL,
    auth: {
        username: 'joe',
        password: '123'
    }
});

export default {
    async getUser(): Promise<User[]> {
        try {
            const response = await axios.get<User[]>(`${API_URL}/users/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createUser(user: User): Promise<string> {
        const response = await axios.post(`${API_URL}/users/new`, user);
        return response.data;
    },

    async updateUser(id: number, user: User): Promise<string> {
        const response = await axios.put(`${API_URL}/users/update/${id}`, user);
        return response.data;
    },

    async toggleUserStatus(id: number): Promise<User> {
        const response = await axios.patch(`${API_URL}/users/status/${id}`);
        return response.data;
    }
};