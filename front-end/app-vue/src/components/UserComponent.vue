/UserComponent
<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import type {User} from "@/models/User";
import userService from "@/services/UserService";
import UserRoleService from "@/services/UserRoleService";
import type {UserRole} from "@/models/UserRole";
import type {ParkingFacility} from "@/models/ParkingFacility";
import ParkingFacilityService from "@/services/ParkingFacilityService";

const users = ref<User[]>([])
const newUser = ref<User>({
  parkingFacility: 0,
  email: "",
  id: 0,
  idNumber: "",
  name: "",
  password: "",
  phoneNumber: "",
  userRole: 0,
  status: false,
  userName: ""
});
const editingUser = ref<User|null>(null);

const parkings = ref<ParkingFacility[]>([]);
const roles = ref<UserRole[]>([]);

onMounted(async () => {
  await loadUsers()
  await loadRoles()
  await loadParkings()

});

const loadUsers = async () => {
  try {
    users.value = await userService.getUser()
  } catch (error) {
    console.error('Error al cargar usuarios:', error)
  }
};

const loadRoles = async () => {
  try {
    roles.value = await UserRoleService.getRole();
  } catch (error) {
    console.error('Error al cargar roles:', error);
  }
};

const loadParkings= async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar car parks:', error);
  }
};

const createUser = async () => {
  try {
  await userService.createUser(newUser.value);
  resetForm();
  await loadUsers();
  }catch (error){
    console.error('Error al crear user:', error);
  }
};

const editUser = (user: User) => {
  editingUser.value = {...user};

};

const updateUser = async () => {
  if (editingUser.value) {
    try {
      await userService.updateUser(editingUser.value.id, editingUser.value);
      resetForm(); // Reiniciar el formulario
      await loadUsers(); // Recargar roles
    } catch (error) {
      console.error('Error al actualizar rol:', error);
    }
  }
};

// Alternar estado de un rol
const toggleUserStatus = async (user: User) => {
  try {
    const updatedUser = await userService.toggleUserStatus(user.id);
    const index = users.value.findIndex(r => r.id === updatedUser.id);
    if (index !== -1) {
      users.value[index] = updatedUser; // Actualizar el rol en la lista
    }
  } catch (error) {
    console.error('Error al alternar estado de user:', error);
  }
};

const resetForm = () => {
  newUser.value = {
    parkingFacility: 0,
    email: "",
    id: 0,
    idNumber: "",
    name: "",
    password: "",
    phoneNumber: "",
    userRole: 0,
    status: false,
    userName: ""
  };
  editingUser.value = null;
};

// Computed property para filtrar
const activeUsers = computed(() => {
  return users.value.filter(user => user.status);
});

// Computed properties para manejar el formulario
const currentUser = computed(() => {
  return editingUser.value || newUser.value;
});

</script>

<template>
  <div class="user-list">
    <h2>Users List</h2>
    <ul v-if="activeUsers.length">
      <li v-for="user in activeUsers" :key="user.id">
        <strong>Username:</strong> {{ user.userName }}<br>
        <strong>Password:</strong> {{ user.password }}<br>
        <strong>Name:</strong> {{ user.name }}<br>
        <strong>ID Number:</strong> {{ user.idNumber }}<br>
        <strong>Phone Number:</strong> {{ user.phoneNumber }}<br>
        <strong>Email:</strong> {{ user.email }}<br>
        <strong>Status:</strong> {{ user.status }}<br>
        <strong>Park Facility:</strong> {{ user.parkingFacility}}<br>
        <strong>User Role:</strong> {{ user.userRole }}<br>
        <button @click="toggleUserStatus(user)">Delete</button>
        <button @click="editUser(user)">Edit</button>
        <hr />
      </li>
    </ul>
    <p v-else>No active users available.</p>
  </div>
  <div class="user-form">
    <h2>{{ editingUser ? 'Edit User' : 'Create User' }}</h2>
    <input v-model="currentUser.userName" placeholder="Username" />
    <input v-model="currentUser.password" placeholder="Password" />
    <input v-model="currentUser.name" placeholder="Name" />
    <input v-model="currentUser.idNumber" placeholder="ID Number" />
    <input v-model="currentUser.phoneNumber" placeholder="Phone Number" />
    <input v-model="currentUser.email" placeholder="Email" />
    <select v-model="currentUser.parkingFacility">
      <option disabled value="">Select Parking Facility</option>
      <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">{{ parkingFacility.name}}</option>
    </select>
    <select v-model="currentUser.userRole" >
      <option disabled value="">Select UserRole</option>
      <option v-for="userRole in roles" :key="userRole.id" :value="userRole.id">{{ userRole.name }}</option>
    </select>

    <input type="checkbox" v-model="currentUser.status" />
    <label for="status">Active</label>
    <button @click="editingUser ? updateUser() : createUser()">
      {{ editingUser ? 'Update UserRole' : 'Create UserRole' }}
    </button>
    <button @click="resetForm" v-if="editingUser">Cancel</button>
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