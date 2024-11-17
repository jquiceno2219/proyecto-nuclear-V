<template>
  <div id="Map" ref="mapContainer" style="width: 800px; height: 400px;"></div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, defineEmits } from 'vue';
import L from 'leaflet';

const mapContainer = ref(null);
const emit = defineEmits();
let mapInstance;

onMounted(() => {
  // Inicializar el mapa
  mapInstance = L.map(mapContainer.value).setView([4.537, -75.678], 13);

  // Agregar capa de OpenStreetMap
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors',
  }).addTo(mapInstance);

  // Escuchar clics en el mapa y emitir las coordenadas
  mapInstance.on('click', (e) => {
    const { lat, lng } = e.latlng;
    emit('map-click', { coordX: lat.toString(), coordY: lng.toString() });

        // Agregar marcador en la ubicación del clic
        L.marker([lat, lng])
      .addTo(mapInstance)
      .bindPopup(`Latitud: ${lat}, Longitud: ${lng}`)
      .openPopup();
  });
});

onUnmounted(() => {
  // Destruir el mapa al desmontar el componente
  if (mapInstance) {
    mapInstance.remove();
  }
});
</script>

<style>
/* Asegúrate de importar el CSS de Leaflet */
@import 'leaflet/dist/leaflet.css';

</style>
