<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {UserRole} from "@/models/UserRole";
import roleService from "@/services/UserRoleService";

const roles = ref<UserRole[]>([]);
const newRole = ref<UserRole>({ id: 0, name: '', status: true });
const editingRole = ref<UserRole | null>(null); // Para almacenar el rol que se está editando

onMounted(async () => {
  await loadRoles();
});

// Cargar roles desde el servicio
const loadRoles = async () => {
  try {
    roles.value = await roleService.getRole();
  } catch (error) {
    console.error('Error al cargar roles:', error);
  }
};

// Crear un nuevo rol
const createRole = async () => {
  try {
    await roleService.createRole(newRole.value);
    resetForm(); // Reiniciar el formulario
    await loadRoles(); // Recargar roles
  } catch (error) {
    console.error('Error al crear rol:', error);
  }
};

// Iniciar la edición de un rol
const editRole = (role: UserRole) => {
  editingRole.value = { ...role }; // Clonar el rol para editarlo
};

// Actualizar un rol
const updateRole = async () => {
  if (editingRole.value) {
    try {
      await roleService.updateRole(editingRole.value.id, editingRole.value);
      resetForm(); // Reiniciar el formulario
      await loadRoles(); // Recargar roles
    } catch (error) {
      console.error('Error al actualizar rol:', error);
    }
  }
};

// Alternar estado de un rol
const toggleRoleStatus = async (role: UserRole) => {
  try {
    const updatedRole = await roleService.toggleRoleStatus(role.id);
    const index = roles.value.findIndex(r => r.id === updatedRole.id);
    if (index !== -1) {
      roles.value[index] = updatedRole; // Actualizar el rol en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de rol:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newRole.value = { id: 0, name: '', status: true };
  editingRole.value = null; // Reiniciar el rol en edición
};

// Computed property para filtrar roles activos
const activeRoles = computed(() => {
  return roles.value.filter(role => role.status);
});

// Computed properties para manejar el formulario
const currentRole = computed(() => {
  return editingRole.value || newRole.value;
});
</script>

<template>
  <div class="role-list">
    <h2>Roles List</h2>
    <ul v-if="activeRoles.length">
      <li v-for="role in activeRoles" :key="role.id">
        <strong>Name:</strong> {{ role.name }}<br>
        <button @click="toggleRoleStatus(role)">Delete</button>
        <button @click="editRole(role)">Edit</button>
        <hr />
      </li>
    </ul>
    <p v-else>No active roles available.</p>
  </div>

  <div class="role-form">
    <h2>{{ editingRole ? 'Edit UserRole' : 'Create UserRole' }}</h2>
    <input v-model="currentRole.name" placeholder="UserRole Name" />
    <input type="checkbox" v-model="currentRole.status" />
    <label for="status">Active</label>
    <button @click="editingRole ? updateRole() : createRole()">
      {{ editingRole ? 'Update UserRole' : 'Create UserRole' }}
    </button>
    <button @click="resetForm" v-if="editingRole">Cancel</button>
  </div>
</template>

<style scoped>
.role-list, .role-form {
  margin: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.role-list h2, .role-form h2 {
  margin-bottom: 10px;
}

.role-list ul {
  list-style-type: none;
  padding: 0;
}

.role-list li {
  margin-bottom: 10px;
}
</style>