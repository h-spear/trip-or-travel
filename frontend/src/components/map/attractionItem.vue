<script setup>
import { onUpdated } from 'vue';
const props = defineProps({
  position: Object
});

onUpdated(() => {
  console.log('updated', props.position);
});
</script>

<template>
  <div>sdf</div>
</template>

<style scoped></style>
