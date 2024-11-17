import axios from 'axios';
import type { VehicleTypeDto } from '@/models/VehicleTypeDto.ts';

const API_URL = 'http://localhost:8080/api/vehicles-types';

export default {
  /**
   * Obtiene la lista de tipos de vehículos desde la API.
   * @returns Una promesa con la lista de tipos de vehículos.
   */
  async listVehicleTypes(): Promise<VehicleTypeDto[]> {
    try {
      const response = await axios.get<VehicleTypeDto[]>(`${API_URL}/list`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener los tipos de vehículos:', error);
      throw error;
    }
  },

  /**
   * Crea un nuevo tipo de vehículo.
   * @param vehicleType - Los datos del tipo de vehículo a crear.
   * @returns Un mensaje de éxito de la API.
   */
  async createVehicleType(vehicleType: VehicleTypeDto): Promise<string> {
    try {
      const response = await axios.post(`${API_URL}/new`, vehicleType);
      return response.data;
    } catch (error) {
      console.error('Error al crear el tipo de vehículo:', error);
      throw error;
    }
  },

  /**
   * Actualiza un tipo de vehículo existente.
   * @param id - El ID del tipo de vehículo a actualizar.
   * @param vehicleType - Los nuevos datos del tipo de vehículo.
   * @returns Un mensaje de éxito de la API.
   */
  async updateVehicleType(id: number, vehicleType: VehicleTypeDto): Promise<string> {
    try {
      const response = await axios.put(`${API_URL}/update/${id}`, vehicleType);
      return response.data;
    } catch (error) {
      console.error('Error al actualizar el tipo de vehículo:', error);
      throw error;
    }
  },

  /**
   * Cambia el estado de un tipo de vehículo (activo/inactivo).
   * @param id - El ID del tipo de vehículo a modificar.
   * @returns El tipo de vehículo actualizado.
   */
  async toggleVehicleTypeStatus(id: number): Promise<VehicleTypeDto> {
    try {
      const response = await axios.patch(`${API_URL}/status/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error al cambiar el estado del tipo de vehículo:', error);
      throw error;
    }
  }
};
