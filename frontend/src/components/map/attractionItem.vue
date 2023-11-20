<script setup>
import { ref, onUpdated } from 'vue';
const props = defineProps({
  position: Object
});

onUpdated(() => {
  //   item.value = props.position;
  console.log('updated', props.position);
});
</script>

<template>
  <div class="card" style="">
    <img :src="position.imageUrl" class="card-img-top" alt="..." />
    <div class="card-body">
      <h5 class="card-title">{{ position.title }}</h5>
      <p class="card-text">
        <div>{{ position.addr1 }}</div>
        <div>{{ position.addr2 }}</div>
      </p>
      <button class="btn btn-primary">등록</button>
      <button class="btn btn-danger">찜</button>
    </div>
  </div>
</template>

<style scoped></style>
