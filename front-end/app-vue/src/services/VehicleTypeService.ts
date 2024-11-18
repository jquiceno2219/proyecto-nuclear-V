import type {VehicleType} from "@/models/VehicleType";
import axios from "axios";

/**
 * Servicio para interactuar con el API de tipos de vehículos.
 * - `getVehicleType`: Obtiene la lista de tipos de vehículos.
 * - `createVehicleType`: Crea un nuevo tipo de vehículo.
 * - `updateVehicleType`: Actualiza un tipo de vehículo existente por ID.
 * - `toggleVehicleTypeStatus`: Cambia el estado de un tipo de vehículo por ID.
 */


const API_URL = 'http://localhost:8080/api/vehicle-types';

export default {
    async getVehicleType(): Promise<VehicleType[]> {
        try {
            const response = await axios.get<VehicleType[]>(`${API_URL}/list`);
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    },

    async createVehicleType(vehicleType: VehicleType): Promise<string> {
        const response = await axios.post(`${API_URL}/new`, vehicleType);
        return response.data;
    },

    async updateVehicleType(id: number, vehicleType: VehicleType): Promise<string> {
        const response = await axios.put(`${API_URL}/update/${id}`, vehicleType);
        return response.data;
    },

    async toggleVehicleTypeStatus(id: number): Promise<VehicleType> {
        const response = await axios.patch(`${API_URL}/status/${id}`);
        return response.data;
    }
};