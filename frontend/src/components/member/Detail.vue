<script setup>
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import { getPlanDetail } from '@/api/plan.js';
const route = useRoute();

onMounted(() => {
  console.log(route.params);
  getPlanDetail(
    route.params.id,
    ({ data }) => {
      console.log('success', data);
    },
    ({ error }) => {
      console.log('failed', error);
    }
  );
});
</script>

<template>
  <div>gogo</div>
</template>

<style scoped></style>
