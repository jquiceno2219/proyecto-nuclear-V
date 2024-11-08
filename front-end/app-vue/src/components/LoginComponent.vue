
<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const username = ref('');
const password = ref('');
const errorMessage = ref('');
const router = useRouter();

async function login() {
  try {
    // Realiza la llamada a la API de una ruta protegida usando autenticación básica
    const response = await axios.get('http://localhost:8080/api/users/list', {
      auth: {
        username: username.value,
        password: password.value
      }
    });

    // Si la solicitud es exitosa, puedes almacenar el nombre de usuario y la contraseña
    localStorage.setItem('username', username.value);
    localStorage.setItem('password', password.value);

    router.push('/');
  } catch (error) {
    errorMessage.value = 'Credenciales incorrectas';
  }
}
</script>

<template>
  <div class="login">
    <h2>Iniciar Sesión</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">Usuario:</label>
        <input type="text" v-model="username" required />
      </div>
      <div>
        <label for="password">Contraseña:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit">Iniciar Sesión</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
.error {
  color: #0037ff;
}
</style>