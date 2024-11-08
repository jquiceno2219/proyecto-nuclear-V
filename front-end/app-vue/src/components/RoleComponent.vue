<script setup lang="ts">
import {onMounted, ref} from "vue";
import type {Role} from "@/models/Role";
import roleService from "@/services/RoleService";

const roles = ref<Role[]>([])

onMounted(async () => {
  try {
    roles.value = await roleService.getRole()
  } catch (error) {
    console.error('Error al cargar roles:', error)
  }
})
</script>

<template>
  <div class="role-list">
    <h2>Roles List</h2>
    <ul v-if="roles.length">
      <li v-for="role in roles" :key="role.id">
        <strong>Name:</strong> {{ role.name }}<br>
        <strong>Status:</strong> {{ role.status }}<br>
        <hr />
      </li>
    </ul>
  </div>
  <div class="role-form">
    <h2>Role Form</h2>

  </div>
</template>

<style scoped>

</style>