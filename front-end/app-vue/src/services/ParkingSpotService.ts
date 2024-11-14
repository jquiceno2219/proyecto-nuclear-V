import type {ParkingSpot} from "@/models/ParkingSpot";
import axios from "axios";


const API_URL = 'http://localhost:8080/api/parking-spots';


export default {
    async getSpot(): Promise<ParkingSpot[]> {
        try {
            const response = await axios.get<ParkingSpot[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createSpot(Spot: ParkingSpot): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, Spot);
        return response.data;
    },

    async updateSpot(id: number, Spot: ParkingSpot): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, Spot);
        return response.data;
    },

    async toggleSpotStatus(id: number): Promise<ParkingSpot> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};