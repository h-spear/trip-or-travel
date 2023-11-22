<script setup></script>

<template>
  <section>
    <div class="board-wrapper">
      <a-page-header
        style="width: 100%"
        title="Title"
        sub-title="This is a subtitle"
        @back="() => null"
      />
      <hr style="width: 100%; border-color: black; margin: 10px 0 30px 0" />
      <router-view></router-view>
    </div>
  </section>
</template>

<style scoped>
section {
  display: flex;
  margin: 0;
  position: relative;
  width: 100vw;
  min-width: 800px;
  max-width: 1400px;
  height: 100%;
  padding: 130px 50px 30px 50px;
}
.board-wrapper {
  background: #ffffff;
  border-radius: 20px;
  /* border: 1px solid rgba(255, 255, 255, 0.5); */
  /* backdrop-filter: blur(15px); */
  -webkit-box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 30px 40px;
}
</style>
