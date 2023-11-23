<script setup>
import { ref, onMounted } from 'vue';
import { getPlans } from '@/api/plan.js';

const plans = ref();

onMounted(() => {
  console.log('we are good');
  getPlans(
    ({ data }) => {
      console.log('success', data);
    },
    ({ error }) => {
      console.log('fail', error);
    }
  );
});
</script>

<template>
  <div>lst</div>
</template>

<style scoped></style>
