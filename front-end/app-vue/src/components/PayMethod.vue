<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import type { PayMethod } from "@/models/PayMethod";
import payMethodService from "@/services/PayMethodService";

const payMethods = ref<PayMethod[]>([]);
const newPayMethod = ref<PayMethod>({
  id: 0,
  name: '',
  status: true,
});
const editingPayMethod = ref<PayMethod | null>(null);

onMounted(async () => {
  await loadPayMethods();
});

const loadPayMethods = async () => {
  try {
    payMethods.value = await payMethodService.getPayMethods();
  } catch (error) {
    console.error('Error loading pay methods:', error);
  }
};

const createPayMethod = async () => {
  try {
    await payMethodService.createPayMethod(newPayMethod.value);
    await loadPayMethods();
    resetForm();
  } catch (error) {
    console.error('Error creating pay method:', error);
    alert('Error creating pay method: ' + (error as Error).message);
  }
};

const updatePayMethod = async () => {
  if (editingPayMethod.value) {
    try {
      await payMethodService.updatePayMethod(editingPayMethod.value.id, editingPayMethod.value);
      await loadPayMethods();
      resetForm();
    } catch (error) {
      console.error('Error updating pay method:', error);
      alert('Error updating pay method: ' + (error as Error).message);
    }
  }
};

const togglePayMethodStatus = async (payMethod: PayMethod) => {
  try {
    const updatedPayMethod = await payMethodService.togglePayMethodStatus(payMethod.id);
    const index = payMethods.value.findIndex((pm: { id: any; }) => pm.id === updatedPayMethod.id);
    if (index !== -1) {
      payMethods.value[index] = updatedPayMethod;
    }
  } catch (error) {
    console.error('Error toggling pay method status:', error);
    alert('Error toggling pay method status: ' + (error as Error).message);
  }
};

const editPayMethod = (payMethod: PayMethod) => {
  editingPayMethod.value = { ...payMethod };
};

const resetForm = () => {
  newPayMethod.value = { id: 0, name: '', status: true };
  editingPayMethod.value = null;
};

const currentPayMethod = computed(() => editingPayMethod.value || newPayMethod.value);
</script>

<template>
  <div class="pay-methods">
    <h2>Pay Methods</h2>
    <ul>
      <li v-for="payMethod in payMethods" :key="payMethod.id">
        <span>- {{ payMethod.name }} ({{ payMethod.status ? 'Active' : 'Inactive' }}) </span>
        <button @click="editPayMethod(payMethod)">Edit</button>
        <button @click="togglePayMethodStatus(payMethod)">
          {{ payMethod.status ? 'Deactivate' : 'Activate' }}
        </button>
      </li>
    </ul>
    <div class="pay-method-form">
      <h2>{{ editingPayMethod ? 'Edit Pay Method' : 'Create Pay Method' }}</h2>
      <form @submit.prevent="editingPayMethod ? updatePayMethod() : createPayMethod()">
        <input v-model="currentPayMethod.name" placeholder="Pay Method Name" required />
        <input type="checkbox" v-model="currentPayMethod.status" id="status" />
        <label for="status">Active</label>
        <button type="submit">
          {{ editingPayMethod ? 'Update Pay Method' : 'Create Pay Method' }}
        </button>
        <button v-if="editingPayMethod" @click="resetForm">Cancel</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.pay-methods {
  margin: 20px;
}

.pay-methods ul {
  list-style: none;
  padding: 0;
}

.pay-methods li {
  margin-bottom: 10px;
}

.pay-method-form {
  margin-top: 20px;
}
</style>
