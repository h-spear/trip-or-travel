<script setup>
import { onMounted, onUpdated } from 'vue';
const props = defineProps({
  selectedItem: Object
});
onUpdated(() => {
  console.log('updated', props.selectedItem);
  props.selectedItem.forEach((data) => {
    console.log('each', data);
  });
});
watch(
  () => {
    props.selectedItem;
  },
  () => {
    console.log(props.selectedItem);
  }
);
</script>

<template>
  <div>asdf {{ selectedItem }}</div>
</template>

<style scoped></style>
