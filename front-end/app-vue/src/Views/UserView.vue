<!-- src/views/UserView.vue -->
<script setup lang="ts">
import UserComponent from "@/components/UserComponent.vue";
</script>

<template>
  <div class="user-view">
    <h1>User Administration</h1>
    <main>
      <user-component/>
    </main>
  </div>
</template>

<style scoped>
.user-view {
  /* Add any specific styles for the user view */
}
</style>