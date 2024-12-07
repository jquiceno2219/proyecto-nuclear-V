<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import payMethodService from "@/services/PayMethodService";
import type {PayMethod} from "@/models/PayMethod";

/**
 * Este componente Vue gestiona los métodos de pago, permitiendo crear, editar y eliminar registros de estos métodos.
 * Al montarse, carga la lista de métodos de pago existentes desde un servicio externo.
 * Proporciona un formulario para crear o editar métodos de pago, permitiendo al usuario ingresar el nombre y el estado del método.
 * La interfaz muestra una lista de métodos de pago activos y permite realizar acciones como editar o eliminar cada método.
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de métodos de pago.
 */

const payMethods = ref<PayMethod[]>([]);
const newPayMethod = ref<PayMethod>({ id: 0, name: '', status: true });
const editingPayMethods = ref<PayMethod | null>(null); // Para almacenar que se está editando

onMounted(async () => {
  await loadPayMethods();
});

// Cargar payMethods desde el servicio
const loadPayMethods = async () => {
  try {
    payMethods.value = await payMethodService.getPayMethod();
  } catch (error) {
    console.error('Error al cargar payMethods:', error);
  }
};

// Crear un nuevo
const createPayMethods = async () => {
  try {
    await payMethodService.createPayMethod(newPayMethod.value);
    resetForm(); // Reiniciar el formulario
    await loadPayMethods(); // Recargar payMethods
  } catch (error) {
    console.error('Error al crear:', error);
  }
};

// Iniciar la edición
const editPayMethods = (payMethod: PayMethod) => {
  editingPayMethods.value = { ...payMethod }; // Clonar para editarlo
};

// Actualizar
const updatePayMethods = async () => {
  if (editingPayMethods.value) {
    try {
      await payMethodService.updatePayMethod(editingPayMethods.value.id, editingPayMethods.value);
      resetForm(); // Reiniciar el formulario
      await loadPayMethods(); // Recargar payMethods
    } catch (error) {
      console.error('Error al actualizar:', error);
    }
  }
};

// Alternar estado
const togglePayMethodsStatus = async (payMethod: PayMethod) => {
  try {
    const updatedPayMethod = await payMethodService.togglePayMethodStatus(payMethod.id);
    const index = payMethods.value.findIndex((p: PayMethod) => p.id === updatedPayMethod.id);
    if (index !== -1) {
      payMethods.value[index] = updatedPayMethod; // Actualizar en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newPayMethod.value = { id: 0, name: '', status: true };
  editingPayMethods.value = null; // Reiniciar en edición
};

// Computed property para filtrar payMethods activos
const activePayMethods = computed(() => {
  return payMethods.value.filter((payMethod: PayMethod) => payMethod.status);
});

// Computed properties para manejar el formulario
const currentPayMethods = computed(() => {
  return editingPayMethods.value || newPayMethod.value;
});
</script>

<template>
  <div class="container">
    <div class="payMethods-list">
      <h2>Lista de Métodos de Pago</h2>
      <ul v-if="activePayMethods.length">
        <li v-for="payMethod in activePayMethods" :key="payMethod.id">
          <strong>Nombre:</strong> {{ payMethod.name }}<br>
          <button @click="togglePayMethodsStatus(payMethod)">Eliminar</button>
          <button @click="editPayMethods(payMethod)">Editar</button>
          <hr />
        </li>
      </ul>
      <p v-else>No hay métodos de pago activos disponibles.</p>
    </div>

    <div class="payMethods-form">
      <h2>{{ editingPayMethods ? 'Editar Método de Pago' : 'Crear Método de Pago' }}</h2>
      <input v-model="currentPayMethods.name" placeholder="Nombre del Método de Pago" />
      <input type="checkbox" v-model="currentPayMethods.status" />
      <label for="status">Activo</label>
      <button @click="editingPayMethods ? updatePayMethods() : createPayMethods()">
        {{ editingPayMethods ? 'Actualizar Método de Pago' : 'Crear Método de Pago' }}
      </button>
      <button @click="resetForm" v-if="editingPayMethods">Cancelar</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  width: 120%;
}

.payMethods-list, .payMethods-form {
  flex: 1;
  margin: 10px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.payMethods-list h2, .payMethods-form h2 {
  margin-bottom: 20px;
  color: #ffffff;
  font-size: 1.5em;
}

.payMethods-list ul {
  list-style-type: none;
  padding: 0;
}

.payMethods-list li {
  margin-bottom: 10px;
}

.payMethods-form input {
  margin-bottom: 10px;
  width: 80%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.payMethods-form button {
  margin-top: 10px;
}
</style>
