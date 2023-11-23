<script setup>
import { ref, onMounted } from 'vue';
import { loginStore } from '@/stores/LoginStore.js';

const { userId } = loginStore();

const user = ref(null);
//api요청을 통해 유저 정보 받아오기

onMounted(() => {
  console.log('who I am', userId);
});
</script>

<template>
  <div class="container">
    <div class="row">asdfasdf</div>
  </div>
</template>

<style scoped></style>
