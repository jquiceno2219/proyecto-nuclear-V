import type {ParkingFacility} from "@/models/ParkingFacility";
import axios from "axios";

const API_URL = 'http://localhost:8080/api/parking-facilities';

export default {
    async getParking(): Promise<ParkingFacility[]> {
        try {
            const response = await axios.get<ParkingFacility[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createParking(parkingFacility: ParkingFacility): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, parkingFacility);
        return response.data;
    },

    async updateParking(id: number, parkingFacility: ParkingFacility): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, parkingFacility);
        return response.data;
    },

    async toggleParkingStatus(id: number): Promise<ParkingFacility> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};