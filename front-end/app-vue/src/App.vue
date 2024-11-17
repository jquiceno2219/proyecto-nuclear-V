<script setup lang="ts">
</script>

<template>
  <header>
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/administration/users">Users</router-link> |
      <router-link to="/administration/roles">Roles</router-link> |
      <router-link to="/administration/parking-facilities">Parking Facilities</router-link> |
      <router-link to="/administration/parking-spots">Parking Spots</router-link> |
      <router-link to="/map">Map</router-link>
    </nav>
  </header>
  <main>
    <router-view></router-view>
  </main>
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

.logo {
  display: block;
  margin: 0 auto 2rem;
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

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
