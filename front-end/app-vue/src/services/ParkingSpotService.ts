import type {ParkingSpot} from "@/models/ParkingSpot";
import axios from "axios";

/**
 * Servicio para interactuar con el API de espacios de estacionamiento.
 * - `getSpot`: Obtiene la lista de espacios de estacionamiento.
 * - `createSpot`: Crea un nuevo espacio de estacionamiento.
 * - `updateSpot`: Actualiza un espacio de estacionamiento existente por ID.
 * - `toggleSpotStatus`: Cambia el estado de un espacio de estacionamiento por ID.
 */


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

    async createSpot(spot: ParkingSpot): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, spot);
        return response.data;
    },

    async updateSpot(id: number, spot: ParkingSpot): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, spot);
        return response.data;
    },

    async toggleSpotStatus(id: number): Promise<ParkingSpot> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};