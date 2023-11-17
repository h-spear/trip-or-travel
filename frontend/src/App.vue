<script setup>
import TheHeadingNavbar from '@/components/layout/TheHeadingNavbar.vue'
</script>

<template>
  <div>
    <TheHeadingNavbar class="mb-5"></TheHeadingNavbar>
    <div class="pt-5 m-5"></div>
    <!-- 네비바와 거리를 두기 위해 만든 태그 -->
    <router-view class=""></router-view>
  </div>
</template>

<style>
body {
  background-image: url(assets/jeju1.jpg);
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
