<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import type {UserRole} from "@/models/UserRole";
import roleService from "@/services/UserRoleService";

/**
 * Este componente Vue gestiona la creación, edición y visualización de roles de usuario en el sistema.
 * Al montarse, carga la lista de roles desde un servicio externo.
 * Proporciona un formulario para crear o editar roles, permitiendo a los administradores definir el nombre del rol y su estado (activo o inactivo).
 * La interfaz muestra una lista de roles activos, con opciones para editar o cambiar su estado (eliminar/recuperar).
 * Los estilos CSS aseguran una presentación clara y atractiva, mejorando la experiencia del usuario en la gestión de roles.
 */

const roles = ref<UserRole[]>([]);
const newRole = ref<UserRole>({ id: 0, name: '', status: true });
const editingRole = ref<UserRole | null>(null);

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
    if (!newRole.value.name) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }
    await roleService.createRole(newRole.value);
    resetForm();
    await loadRoles();
  } catch (error) {
    console.error('Error al crear rol:', error);
  }
};

// Iniciar la edición de un rol
const editRole = (role: UserRole) => {
  editingRole.value = { ...role };
};

// Actualizar un rol
const updateRole = async () => {
  if (editingRole.value) {
    try {
      await roleService.updateRole(editingRole.value.id, editingRole.value);
      resetForm();
      await loadRoles();
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
      roles.value[index] = updatedRole;
    }
  } catch (error) {
    console.error('Error al alternar estado de rol:', error);
  }
};

// Reiniciar el formulario
const resetForm = () => {
  newRole.value = { id: 0, name: '', status: true };
  editingRole.value = null;
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
  <div class="container">
    <div class="role-list">
      <h2>Roles List</h2>
      <table v-if="activeRoles.length">
        <thead>
        <tr>
          <th>Name</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="role in activeRoles" :key="role.id">
          <td>{{ role.name }}</td>
          <td>{{ role.status ? 'Active' : 'Inactive' }}</td>
          <td>
            <button class="action-button" @click="toggleRoleStatus(role)">
              {{ role.status ? 'Delete' : 'Recover' }}
            </button>
            <button class="action-button" @click="editRole(role)">Edit</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-else>No active roles available.</p>
    </div>

    <div class="role-form">
      <h2>{{ editingRole ? 'Edit UserRole' : 'Create UserRole' }}</h2>
      <form @submit.prevent="editingRole ? updateRole() : createRole()">
        <div class="form-group">
          <input class="form-input" v-model="currentRole.name" placeholder="User Role Name" required />
        </div>
        <div class="form-group">
          <input type="checkbox" id="status" v-model="currentRole.status" />
          <label for="status">Active</label>
        </div>
        <button class="submit-button" type="submit">
          {{ editingRole ? 'Update UserRole' : 'Create UserRole' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingRole">
          Cancel
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin: 10px;
  width : 150%;
}

.role-list, .role-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.role-list h2, .role-form h2 {
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: rgba(0, 0, 0, 0.99);
}

.form-group {
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

.action-button, .submit-button, .cancel-button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.action-button {
  background-color: #679ee8;
  color: white;
}

.action-button:hover {
  background-color: #569ae7;
}

.submit-button {
  background-color: #96f6ab;
  color: white;
}

.submit-button:hover {
  background-color: #72e58c;
}

.cancel-button {
  background-color: #dc3545;
  color: white;
}

.cancel-button:hover {
  background-color: #c82333;
}
</style>