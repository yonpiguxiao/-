<template>
  <div class="home-container">
    <div class="navbar-container">
      <Navbar />
    </div>
    <div class="content-container">
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import Navbar from '@/components/Navbar.vue'
</script>

<style lang="scss" scoped>
.home-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.navbar-container {
  flex-shrink: 0;
  height: 60px;
  background: #409eff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.content-container {
  flex: 1;
  overflow: auto;
  padding: 20px;
  background-color: #f7f7f7;
}
</style>
