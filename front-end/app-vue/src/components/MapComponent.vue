<template>
  <div id="map"></div>
</template>

<script setup lang="ts">
import leaflet from "leaflet";
import { onMounted } from "vue";

let map: leaflet.Map;

onMounted(() => {
  // Inicializa el mapa
  map = leaflet
      .map("map")
      .setView([51.505, -0.09], 13);

  // Añade la capa de tiles
  leaflet
      .tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution:
            '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      })
      .addTo(map);
  // Manejar el evento de clic en el mapa
  map.on('click', function(e) {
    // Obtener las coordenadas donde se hizo clic
    const { lat, lng } = e.latlng;

    // Crear un marcador en las coordenadas del clic
    const marker = leaflet.marker([lat, lng]).addTo(map);
    marker.bindPopup('Haz clic para eliminar este marcador.').openPopup();

    // Manejar el clic en el marcador para eliminarlo
    marker.on('click', function() {
      map.removeLayer(marker); // Eliminar el marcador del mapa
    });
  });
});
</script>

<style scoped>
#map {
  width: 800%;
  height: 100%;
}
</style>