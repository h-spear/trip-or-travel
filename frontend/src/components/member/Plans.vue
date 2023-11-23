<script setup>
import { ref, onMounted } from 'vue';
import { getPlans } from '@/api/plan.js';
import { useRouter } from 'vue-router';
const router = useRouter();
// import router from '@/router';

const plans = ref();

onMounted(() => {
  console.log('we are good');
  getPlans(
    ({ data }) => {
      console.log('success', data.data);
      plans.value = data.data;
    },
    ({ error }) => {
      console.log('fail', error);
    }
  );
});

const moveDetail = (id) => {
  router.push({
    name: 'my-plans-detail',
    params: { id: id }
    // props: true
  });
  console.log('id', id);
};
</script>

<template>
  <div v-for="plan in plans" :key="plan.id">
    <div class="container border bg-success" @click="moveDetail(plan.planId)">
      <div>제목 : {{ plan.title }}</div>
      <div>기간 : {{ plan.startDateTime }} ~ {{ plan.endDateTime }}</div>
      <div>등록 시간 : {{ plan.registrationTime }}</div>
    </div>
  </div>
</template>

<style scoped></style>
