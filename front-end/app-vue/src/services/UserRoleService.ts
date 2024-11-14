import type {UserRole} from "@/models/UserRole";
import axios from "axios";

const API_URL = 'http://localhost:8080/api/user-roles';


export default {
    async getRole(): Promise<UserRole[]> {
        try {
            const response = await axios.get<UserRole[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createRole(role: UserRole): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, role);
        return response.data;
    },

    async updateRole(id: number, role: UserRole): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, role);
        return response.data;
    },

    async toggleRoleStatus(id: number): Promise<UserRole> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};