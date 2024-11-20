//LoginView
<script lang="ts">
import {defineComponent, ref} from 'vue';
import AuthService from '@/services/AuthService';
import router from "@/router";

/**
 * Componente de vista de login.
 * Permite a los usuarios ingresar su nombre de usuario y contraseña.
 * Utiliza AuthService para manejar la autenticación del usuario.
 * Si las credenciales son correctas, el usuario es redirigido a la página principal.
 * En caso de error, se muestra un mensaje indicando que las credenciales son incorrectas.
 */

export default defineComponent({
  setup() {
    const username = ref('');
    const password = ref('');
    const error = ref('');

    const handleLogin = async () => {
      try {
        await AuthService.login(username.value, password.value);
        router.push('/');
      } catch (err) {
        error.value = 'Credenciales incorrectas';
      }
    };

    return {
      username,
      password,
      handleLogin,
      error,
    };
  },
});
</script>

<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <input v-model="username" type="text" placeholder="Username" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
    </form>
    <div v-if="error">{{ error }}</div>
  </div>
</template>

<style scoped>

</style>