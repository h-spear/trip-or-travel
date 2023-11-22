<script setup></script>

<template>
  <section>
    <div class="board-wrapper">
      <div class="page-header">asdas</div>
      <router-view></router-view>
    </div>
  </section>
</template>

<style scoped>
section {
  display: flex;
  margin: 0;
  position: relative;
  min-width: 800px;
  width: 100vw;
  height: 100vh;
  max-width: 1200px;
  padding: 130px 50px 30px 50px;
}

.board-wrapper {
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  backdrop-filter: blur(15px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>
