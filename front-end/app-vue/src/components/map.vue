<template>

  <div id="map"></div>
</template>
  
<script setup lang="ts">
  import leaflet from "leaflet";

  import { onMounted } from "vue";
//  import { useGeolocation } from "@vueuse/core";
  
  //import { userMarker, nearbyMarkers } from "@/stores/mapStore";
  
//  const { coords } = useGeolocation();
  
  let map: leaflet.Map;
  let userGeoMarker: leaflet.Marker;
  
  onMounted(() => {
    map = leaflet
      .map("map")
      .setView([51.505, -0.09], 13);
  
    leaflet
      .tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      })
      .addTo(map);
    })

</script>

<style scoped>
#map {
  width: 500px;
  height: 500px;
}
</style>