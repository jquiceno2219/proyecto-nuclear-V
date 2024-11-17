import axios from 'axios';
import type { FeeDto } from '@/models/FeeDto.ts';

const API_URL = 'http://localhost:8080/api/fee';

export default {
  async listFees(): Promise<FeeDto[]> {
    try {
      const response = await axios.get<FeeDto[]>(`${API_URL}/list`);
      return response.data;
    } catch (error) {
      console.error('Error fetching fees:', error);
      throw error;
    }
  },

  async createFee(fee: FeeDto): Promise<string> {
    try {
      const response = await axios.post(`${API_URL}/new`, fee);
      return response.data;
    } catch (error) {
      console.error('Error creating fee:', error);
      throw error;
    }
  },

  async updateFee(id: number, fee: FeeDto): Promise<string> {
    try {
      const response = await axios.put(`${API_URL}/update/${id}`, fee);
      return response.data;
    } catch (error) {
      console.error('Error updating fee:', error);
      throw error;
    }
  },

  async toggleFeeStatus(id: number): Promise<FeeDto> {
    try {
      const response = await axios.patch(`${API_URL}/status/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error toggling fee status:', error);
      throw error;
    }
  }
};
