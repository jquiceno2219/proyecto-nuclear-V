
import axios from "axios";
import type {Daily} from "@/models/DailySchedule";

const API_URL = 'http://localhost:8080/api/days-schedules';

export default {
    async getDaily(): Promise<Daily[]> {
        try {
            const response = await axios.get<Daily[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createDaily(daily: Daily): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, daily);
        return response.data;
    },

    async updateDaily(id: number, daily: Daily): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, daily);
        return response.data;
    },

    async toggleDailyStatus(id: number): Promise<Daily> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};