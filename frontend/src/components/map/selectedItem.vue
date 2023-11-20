<script setup>
import { onMounted, onUpdated, watch } from 'vue';
const props = defineProps({
  selectedItem: Object
});
onMounted(() => {
  console.log('onMounted', props.selectedItem);
});
// watch(
//   () => {
//     props.selectedItem;
//   },
//   () => {
//     console.log(props.selectedItem);
//   }
// );
</script>

<template>
  <div>{{ selectedItem }}</div>
</template>

<style scoped></style>
