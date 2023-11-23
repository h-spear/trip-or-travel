<script setup>
import { ref } from 'vue';
import Info from '@/components/member/Info.vue';
import Plans from '@/components/member/Plans.vue';
// 내 정보와 여행리스트를 조회할 수 있는 사이트
// 정보는 그냥 회원가입 페이지와 비슷해도 될 것 같다
// 리스트는 일단 리스트를 쫙 보여주고 디테일로 하나하나 클릭할 수 있게 한다.
// 각각 수정도 가능하게 만든다.
const view = ref(true);
</script>

<template>
  <div class="container">
    <div class="row">
      <button>내 정보</button>
      <button>내 여행 리스트</button>
    </div>
    <div class="row">
      <button>내 정보</button>
      <button>네비바 때메 안보여서 복붙때림</button>
    </div>
    <div class="row">
      <button class="btn btn-success" @click="view = true">내 정보</button>
      <button class="btn btn-success border border-white" @click="view = false">
        내 여행 리스트
      </button>
    </div>
    <Info v-if="view" />
    <Plans v-else />
  </div>
</template>

<style scoped></style>
