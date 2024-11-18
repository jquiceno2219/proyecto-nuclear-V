<script setup lang="ts">
import leaflet from "leaflet";
import {onMounted, ref} from "vue";
import ParkingFacilityService from "@/services/ParkingFacilityService";
import type {ParkingFacility} from "@/models/ParkingFacility";

/**
 * Este componente Vue utiliza Leaflet para mostrar un mapa interactivo que permite a los usuarios ver y seleccionar ubicaciones de instalaciones de estacionamiento.
 * Al montarse, inicializa el mapa y carga las instalaciones de estacionamiento desde un servicio externo, mostrando marcadores en el mapa.
 * Los usuarios pueden hacer clic en el mapa para obtener las coordenadas de la ubicación seleccionada, que se muestran en la interfaz y se emiten como un evento.
 * Además, se pueden agregar marcadores en las coordenadas seleccionadas, que los usuarios pueden eliminar haciendo clic en ellos.
 * Los estilos CSS aseguran que el mapa tenga un tamaño adecuado para la visualización.
 */

let map: leaflet.Map;
const latRef = ref('Latitud: ');
const lngRef = ref('Longitud: ');
const parkings = ref<ParkingFacility[]>([]);
const emit = defineEmits(['map-click']); // Emitir evento

onMounted(async () => {
  // Inicializa el mapa
  map = leaflet.map("map").setView([51.505, -0.09], 13);

  // Añade la capa de tiles
  leaflet.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  }).addTo(map);

  // Cargar parkings y mostrarlos en el mapa
  await loadParkings();

  // Manejar el evento de clic en el mapa
  map.on('click', function(e) {
    const { lat, lng } = e.latlng;

    // Actualizar las coordenadas en la interfaz
    latRef.value = `Latitud: ${lat.toFixed(5)}`;
    lngRef.value = `Longitud: ${lng.toFixed(5)}`;

    // Emitir el evento con las coordenadas
    emit('map-click', { coordX: lat.toFixed(5), coordY: lng.toFixed(5) });

    // Crear un marcador en las coordenadas del clic
    const marker = leaflet.marker([lat, lng]).addTo(map);
    marker.bindPopup(`Coordenadas: ${lat.toFixed(5)}, ${lng.toFixed(5)}`).openPopup();

    // Manejar el clic en el marcador para eliminarlo
    marker.on('click', function() {
      map.removeLayer(marker);
    });
  });
});

// Cargar parkings desde el servicio y mostrarlos en el mapa
const loadParkings = async () => {
  try {
    parkings.value = await ParkingFacilityService.getParking();
    parkings.value.forEach(parking => {
      const marker = leaflet.marker([parking.coordX, parking.coordY]).addTo(map);
      marker.bindPopup(`Nombre: ${parking.name}<br>Dirección: ${parking.address}`).openPopup();
    });
  } catch (error) {
    console.error('Error al cargar parkings:', error);
  }
};
</script>

<template>
  <div>
    <div id="map"></div>
    <div id="coordinates" style="margin-top: 10px; font-size: 16px;">
      Coordenadas: <span id="lat">{{ latRef }}</span> <span id="lng">{{ lngRef }}</span>
    </div>
  </div>
</template>

<style scoped>
#map {
  width: 1000px;
  height: 500px;
}
</style>