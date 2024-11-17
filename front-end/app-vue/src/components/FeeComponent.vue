// AUN NO ESTá listo

<template>
    <div class="fee-management">
      <h2>Fee Management</h2>
      <ul>
        <li v-for="fee in fees" :key="fee.id">
          <span>{{ fee.name }} ({{ fee.status ? 'Active' : 'Inactive' }})</span>
          <button @click="editFee(fee)">Edit</button>
          <button @click="toggleFeeStatus(fee)">
            {{ fee.status ? 'Deactivate' : 'Activate' }}
          </button>
        </li>
      </ul>
      <div class="fee-form">
        <h2>{{ editingFee ? 'Edit Fee' : 'Create Fee' }}</h2>
        <form @submit.prevent="editingFee ? updateFee() : createFee()">
          <input v-model="currentFee.name" placeholder="Fee Name" required />
          <input v-model="currentFee.price" type="number" placeholder="Fee Price" required />
          <input type="checkbox" v-model="currentFee.status" id="status" />
          <label for="status">Active</label>
          <button type="submit">{{ editingFee ? 'Update Fee' : 'Create Fee' }}</button>
          <button v-if="editingFee" @click="resetForm">Cancel</button>
        </form>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { ref, onMounted, computed } from 'vue';
  import feeService from '../services/FeeService';
  import type { FeeDto } from '@/models/FeeDto';
  
  export default {
    name: 'FeeManagement',
    setup() {
      const fees = ref<FeeDto[]>([]);
      const newFee = ref<FeeDto>({
        id: 0,
        name: '',
        price: 0,
        status: true,
        parkingFacility: { id: 0 },
        VehicleTypeDto: { id: 0 }
      });
      const editingFee = ref<FeeDto | null>(null);
  
      onMounted(async () => {
        await loadFees();
      });
  
      const loadFees = async () => {
        try {
          fees.value = await feeService.listFees();
        } catch (error) {
          console.error('Error loading fees:', error);
        }
      };
  
      const createFee = async () => {
        try {
          await feeService.createFee(newFee.value);
          await loadFees();
          resetForm();
        } catch (error) {
          console.error('Error creating fee:', error);
          alert('Error creating fee: ' + (error as Error).message);
        }
      };
  
      const updateFee = async () => {
        if (editingFee.value) {
          try {
            await feeService.updateFee(editingFee.value.id, editingFee.value);
            await loadFees();
            resetForm();
          } catch (error) {
            console.error('Error updating fee:', error);
            alert('Error updating fee: ' + (error as Error).message);
          }
        }
      };
  
      const toggleFeeStatus = async (fee: FeeDto) => {
        try {
          const updatedFee = await feeService.toggleFeeStatus(fee.id);
          const index = fees.value.findIndex((f) => f.id === updatedFee.id);
          if (index !== -1) {
            fees.value[index] = updatedFee;
          }
        } catch (error) {
          console.error('Error toggling fee status:', error);
          alert('Error toggling fee status: ' + (error as Error).message);
        }
      };
  
      const editFee = (fee: FeeDto) => {
        editingFee.value = { ...fee };
      };
  
      const resetForm = () => {
        newFee.value = {
          id: 0,
          name: '',
          price: 0,
          status: true,
          parkingFacility: { id: 0 },
          VehicleTypeDto: { id: 0 }
        };
        editingFee.value = null;
      };
  
      const currentFee = computed(() => editingFee.value || newFee.value);
  
      return {
        fees,
        newFee,
        editingFee,
        loadFees,
        createFee,
        updateFee,
        toggleFeeStatus,
        editFee,
        resetForm,
        currentFee
      };
    }
  };
  </script>
  
  <style>
  .fee-management {
    font-family: Arial, sans-serif;
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .fee-management h2 {
    margin-bottom: 16px;
  }
  
  .fee-management ul {
    list-style: none;
    padding: 0;
    margin-bottom: 20px;
  }
  
  .fee-management li {
    margin-bottom: 10px;
  }
  
  .fee-management li span {
    margin-right: 10px;
  }
  
  .fee-form {
    margin-top: 20px;
  }
  
  .fee-form form {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .fee-form input[type="text"],
  .fee-form input[type="number"] {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .fee-form button {
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }
  
  .fee-form button:hover {
    background-color: #0056b3;
  }
  
  .fee-form button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  </style>
  