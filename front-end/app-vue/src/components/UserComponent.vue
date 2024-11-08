/UserComponent

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type {User} from "@/models/User";
import userService from "@/services/UserService";

const users = ref<User[]>([])

onMounted(async () => {
  try {
    users.value = await userService.getUser()
  } catch (error) {
    console.error('Error al cargar usuarios:', error)
  }
})
</script>

<template>
  <div class="user-list">
    <h2>Users List</h2>
    <ul v-if="users.length">
      <li v-for="user in users" :key="user.id">
        <strong>Username:</strong> {{ user.userName }}<br>
        <strong>Password:</strong> {{ user.password }}<br>
        <strong>Name:</strong> {{ user.name }}<br>
        <strong>ID Number:</strong> {{ user.idNumber }}<br>
        <strong>Phone Number:</strong> {{ user.phoneNumber }}<br>
        <strong>Email:</strong> {{ user.email }}<br>
        <strong>Status:</strong> {{ user.status }}<br>
        <strong>Car Park:</strong> {{ user.carPark }}<br>
        <strong>Role:</strong> {{ user.role }}<br>
        <hr />
      </li>
    </ul>
  </div>
  <div class="user-form">
    <h2>User Form</h2>

  </div>
</template>

<style scoped>

</style>