<script setup>
import { ref, onMounted } from 'vue';

const selectedItems = ref([])

onMounted(() => {
  console.log('history.state', history.state.selectedItems);
  selectedItems.value =history.state.selectedItems
});
</script>

<template>
  <div class="bg-success" v-for="item in selectedItems" :key="item.id">
    <h5 >{{ item.title }}</h5>
      <p>
        <div>{{ item.addr1 }}</div>
        <div>{{ item.addr2 }}</div>
      </p>
  </div>
</template>

<style scoped></style>
