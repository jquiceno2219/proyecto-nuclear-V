<script setup lang="ts">
import 'leaflet/dist/leaflet.css';
import NavbarSwitcher from "@/components/navbars/NavbarSwitcher.vue";
/**
 * Este componente contiene la barra de navegación principal para la administración del sistema.
 * Cada sección del menú (Usuarios, Estacionamiento, Horarios, Servicios) está agrupada en un menú desplegable.
 * El contenido principal se muestra a través de `<router-view>`, que se actualiza según la ruta activa.
 * Además, se asegura de que la barra de navegación permanezca fija en la parte superior de la página,
 * con un margen superior en el contenido para evitar que se sobreponga.
 */

</script>

<template>
  <navbar-switcher/>

</template>

<style scoped>
header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

nav {
  padding: 1rem;
  text-align: center;
}

nav a {
  margin: 0 0.5rem;
  text-decoration: none;
  color: #2c3e50;
}

nav a.router-link-active {
  color: #42b983;
}

.dropdown {
  display: inline-block;
  position: relative;
}

.dropbtn {
  background-color: #fff;
  color: #2c3e50;
  padding: 10px;
  font-size: 16px;
  border: none;
  cursor: pointer;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 200px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  text-align: left;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown-content a {
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  color: #2c3e50;
}

.dropdown-content a:hover {
  background-color: #f1f1f1;
}

.main-content {
  margin-top: 60px;
  padding: 20px;
  align-items: center;
}
</style>