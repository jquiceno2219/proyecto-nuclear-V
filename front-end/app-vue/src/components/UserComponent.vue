<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import type {User} from "@/models/User";
import userService from "@/services/UserService";
import UserRoleService from "@/services/UserRoleService";
import type {UserRole} from "@/models/UserRole";
import type {ParkingFacility} from "@/models/ParkingFacility";
import ParkingFacilityService from "@/services/ParkingFacilityService";

const users = ref<User[]>([]);
const newUser = ref<User>({
  parkingFacility: { id: 0, name: "" },
  email: "",
  id: 0,
  idNumber: "",
  name: "",
  password: "",
  phoneNumber: "",
  userRole: { id: 0, name: "" },
  status: true,
  userName: ""
});
const editingUser = ref<User | null>(null);

const parkings = ref<ParkingFacility[]>([]);
const roles = ref<UserRole[]>([]);

onMounted(async () => {
  await loadUsers();
  await loadRoles();
  await loadParkings();
});

const loadUsers = async () => {
  try {
    users.value = await userService.getUser();
  } catch (error) {
    console.error('Error al cargar usuarios:', error);
  }
};

const loadRoles = async () => {
  try {
    roles.value = await UserRoleService.getRole();
  } catch (error) {
    console.error('Error al cargar roles:', error);
  }
};

const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
  } catch (error) {
    console.error('Error al cargar car parks:', error);
  }
};

const createUser = async () => {
  try {
    if (!validateUserForm()) {
      alert('Please complete all required fields');
      return;
    }

    const userToSend = {
      id: 0,
      userName: newUser.value.userName,
      password: newUser.value.password,
      name: newUser.value.name,
      idNumber: newUser.value.idNumber,
      phoneNumber: newUser.value.phoneNumber,
      email: newUser.value.email,
      status: newUser.value.status,
      parkingFacility: { id: newUser.value.parkingFacility.id , name: newUser.value.parkingFacility.name},
      userRole: { id: newUser.value.userRole.id , name: newUser.value.userRole.name}
    };

    await userService.createUser(userToSend);
    await loadUsers();
    resetForm();
  } catch (error) {
    console.error('Error creating user:', error);
    alert('Error creating user: ' + (error as Error).message);
  }
};

const validateUserForm = () => {
  const user = currentUser.value;
  return user.userName &&
      user.password &&
      user.name &&
      user.idNumber &&
      user.email &&
      user.parkingFacility?.id &&
      user.userRole?.id;
};

const editUser = (user: User) => {
  editingUser.value = { ...user };
};

const updateUser = async () => {
  if (editingUser.value) {
    try {
      if (!validateUserForm()) {
        alert('Por favor complete todos los campos requeridos');
        return;
      }
      const userToSend = {
        id: editingUser.value.id,
        userName: editingUser.value.userName,
        password: editingUser.value.password,
        name: editingUser.value.name,
        idNumber: editingUser.value.idNumber,
        phoneNumber: editingUser.value.phoneNumber,
        email: editingUser.value.email,
        status: editingUser.value.status,
        parkingFacility: { id: editingUser.value.parkingFacility.id , name: editingUser.value.parkingFacility.name},
        userRole: { id: editingUser.value.userRole.id , name: editingUser.value.userRole.name}
      };

      await userService.updateUser(editingUser.value.id, userToSend);
      await loadUsers();
      resetForm();
    } catch (error) {
      console.error('Error al actualizar usuario:', error);
      alert('Error al actualizar usuario: ' + (error as Error).message);
    }
  }
};

const toggleUserStatus = async (user: User) => {
  try {
    const updatedUser = await userService.toggleUserStatus(user.id);
    const index = users.value.findIndex(r => r.id === updatedUser.id);
    if (index !== -1) {
      users.value[index] = updatedUser;
    }
  } catch (error) {
    console.error('Error al alternar estado de user:', error);
    alert('Error al cambiar estado del usuario: ' + (error as Error).message);
  }
};

const resetForm = () => {
  newUser.value = {
    parkingFacility: { id: 0, name: "" },
    email: "",
    id: 0,
    idNumber: "",
    name: "",
    password: "",
    phoneNumber: "",
    userRole: { id: 0, name: "" },
    status: true,
    userName: ""
  };
  editingUser.value = null;
};

const activeUsers = computed(() => {
  return users.value.filter(user => user.status);
});

const currentUser = computed(() => {
  return editingUser.value || newUser.value;
});
</script>

<template>
  <div class="container">
    <div class="user-list">
      <h2>Users List</h2>
      <table v-if="activeUsers.length">
        <thead>
        <tr>
          <th>Username</th>
          <th>Name</th>
          <th>ID Number</th>
          <th>Phone Number</th>
          <th>Email</th>
          <th>Status</th>
          <th>Parking Facility</th>
          <th>User Role</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in activeUsers" :key="user.id">
          <td>{{ user.userName }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.idNumber }}</td>
          <td>{{ user.phoneNumber }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.status ? 'Active' : 'Inactive' }}</td>
          <td>{{ parkings.find(p => p.id === user.parkingFacility.id)?.name || 'N/A' }}</td>
          <td>{{ roles.find(r => r.id === user.userRole.id)?.name || 'N/A' }}</td>
          <td>
            <button class="action-button" @click="toggleUserStatus(user)">
              {{ user.status ? 'Delete' : 'Recuperate' }}
            </button>
            <button class="action-button" @click="editUser  (user)">Edit</button>
          </td>
        </tr>
        </tbody>
      </table>
      <p v-else>No active users available.</p>
    </div>
    <div class="user-form">
      <h2>{{ editingUser   ? 'Edit User' : 'Create User' }}</h2>
      <form @submit.prevent="editingUser   ? updateUser  () : createUser  ()">
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .userName" placeholder="Username" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .password" type="password" placeholder="Password" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .name" placeholder="Name" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .idNumber" placeholder="ID Number" required />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .phoneNumber" type="tel" placeholder="Phone Number" />
        </div>
        <div class="form-group">
          <input class="form-input" v-model="currentUser  .email" type="email" placeholder="Email" required />
        </div>
        <div class="form-group">
          <select class="form-select" v-model="currentUser  .parkingFacility.id" required>
            <option disabled value="">Select Parking Facility</option>
            <option v-for="parkingFacility in parkings" :key="parkingFacility.id" :value="parkingFacility.id">
              {{ parkingFacility.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <select class="form-select" v-model="currentUser  .userRole.id" required>
            <option disabled value="">Select User Role</option>
            <option v-for="userRole in roles" :key="userRole.id" :value="userRole.id">
              {{ userRole.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <input type="checkbox" id="status" v-model="currentUser  .status" />
          <label for="status">Active</label>
        </div>
        <button class="submit-button" type="submit">
          {{ editingUser   ? 'Update User' : 'Create User' }}
        </button>
        <button class="cancel-button" type="button" @click="resetForm" v-if="editingUser  ">
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
  width: 120%;
}

.user-list, .user-form {
  flex: 1;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.user-list h2, .user-form h2 {
  margin-bottom: 10px;
}

table {
  width: 0%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: rgb(255, 255, 255);
}

.form-group {
  margin-bottom: 15px;
}

.form-input, .form-select {
  width: 80%;
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
