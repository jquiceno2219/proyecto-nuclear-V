import axios from "axios";
import type {DayWeek} from "@/models/DayOfWeek";


const API_URL = 'http://localhost:8080/api/days-week';

export default {
    async getDayOfWeek(): Promise<DayWeek[]> {
        try {
            const response = await axios.get<DayWeek[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createDayOfWeek(dayWeek: DayWeek): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, dayWeek);
        return response.data;
    },

    async updateDayOfWeek(id: number, dayWeek: DayWeek): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, dayWeek);
        return response.data;
    },

    async toggleDayOfWeekStatus(id: number): Promise<DayWeek> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};