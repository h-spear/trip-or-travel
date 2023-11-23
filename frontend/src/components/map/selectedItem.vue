<script setup>
import { onMounted, onUpdated, watch, ref } from 'vue';
const props = defineProps({
  selectedItem: Object
});


const emits = defineEmits(['unselectItem'])



const unselectItem = () => {
  emits('unselectItem', props.selectedItem)
}


onMounted(() => {
  console.log('onMounted', props.selectedItem);
});

</script>

<template>
  <div class="card" style="">
    <img :src="selectedItem.imageUrl" class="card-img-top" style="height: 50px; width: 50px;" alt="..." />
    <div class="card-body">
      <h5 class="card-title">{{ selectedItem.title }}</h5>
      <p class="card-text">
        <div>{{ selectedItem.addr1 }}</div>
        <div>{{ selectedItem.addr2 }}</div>
      </p>
      <button class="btn btn-primary" @click="unselectItem" >취소
        </button>
      <button class="btn btn-danger">찜</button>
    </div>
  </div>
</template>

<style scoped></style>
