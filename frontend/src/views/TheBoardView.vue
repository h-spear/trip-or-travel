<script setup>
import { ref, inject, provide, watch } from 'vue'

const boards = ref([])
let boardSt = localStorage.getItem('boards')
if (boardSt) {
  boards.value = JSON.parse(boardSt).boards
}
provide('boards', boards)
</script>

<template>
  <div class="container text-center mt-3">
    <div class="alert alert-info" role="alrt">게시판입니다</div>
    <!-- router view 설정 -->
    <!-- <router-link to="/list" class="nav-link">게시판</router-link> -->
    <div></div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
router-view {
  background-color: #54fff9;
}
.container {
  background-color: aliceblue;
  opacity: 90%;
}
</style>
