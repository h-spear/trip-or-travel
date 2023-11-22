<script setup>
import { onMounted } from 'vue';
import TheHeadingNavbar from '@/components/layout/TheHeadingNavbar.vue';
import { AddressStore } from '@/stores/AddressStore.js';
const addressStore = AddressStore();
const { sidos, funcGetItems } = addressStore;
onMounted(() => {
  if (!sidos.value) {
    funcGetItems();
  }
});
</script>

<template>
  <div style="display: flex; justify-content: center; align-items: center">
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
  </div>
</template>

<style>
body {
  background-image: url(assets/bg.jpg);
  background-size: cover; /* 이미지를 화면에 꽉 채우도록 크기 조정 */
  background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
  background-attachment: fixed; /* 스크롤 시 배경 이미지 고정 */
}
a {
  cursor: pointer;
}

#profileImg {
  margin: 10px;
  width: 50px;
  height: 50px;
}
</style>
