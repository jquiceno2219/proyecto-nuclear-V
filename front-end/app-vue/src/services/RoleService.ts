import type {Role} from "@/models/Role";
import axios from "axios";

const API_URL = 'http://localhost:8080/api';
export default {
    async getRole(): Promise<Role[]> {
        try {
            const response = await axios.get<Role[]>(`${API_URL}/roles/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createRole(role: Role): Promise<string> {
        const response = await axios.post(`${API_URL}/roles/new`, role);
        return response.data;
    },

    async updateRole(id: number, role: Role): Promise<string> {
        const response = await axios.put(`${API_URL}/roles/update/${id}`, role);
        return response.data;
    },

    async toggleRoleStatus(id: number): Promise<Role> {
        const response = await axios.patch(`${API_URL}/roles/status/${id}`);
        return response.data;
    }
};