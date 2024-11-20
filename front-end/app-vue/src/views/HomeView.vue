<script setup lang="ts">
import MapComponent from "@/components/MapComponent.vue";
/**
 * Componente principal de la vista de inicio.
 * Contiene un encabezado con un título de bienvenida y un contenedor para mostrar el mapa.
 * El mapa se incluye a través del componente MapComponent.
 * El diseño utiliza flexbox para alinear y centrar los elementos dentro del contenedor principal.
 * El contenedor del mapa tiene un tamaño máximo definido y un estilo que incluye bordes, bordes redondeados y sombra para mejorar la estética.
 */

</script>

<template>
  <main class="home-view">
    <header class="header">
      <h1 class="title">Welcome</h1>
    </header>
    <section class="map-container">
      <MapComponent />
    </section>
  </main>
</template>

<style scoped>
.home-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  background-color: #f9f9f9;
  min-height: 80vh;
  width: 150vh;
}

.header {
  margin-bottom: 20px;
}

.title {
  font-size:2rem;
  color: #333;
}

.map-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 800%;
  max-width: 1000px;
  height: 500px;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-top: 5px;
}


</style>