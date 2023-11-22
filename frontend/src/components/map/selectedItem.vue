<script setup>
import { onMounted, onUpdated, watch, ref } from 'vue';
const props = defineProps({
  selectedItem: Object
});
const id = ref(props.selectedItem[0])
const value = ref(props.selectedItem[1])


const emits = defineEmits(['closeItem'])



const closeItem = () => {
  emits('closeItem', id.value)
}


onMounted(() => {
  console.log('onMounted', props.selectedItem);
});

</script>

<template>
  <div class="card" style="">
    <img :src="selectedItem[1].imageUrl" class="card-img-top" style="height: 50px; width: 50px;" alt="..." />
    <div class="card-body">
      <h5 class="card-title">{{ selectedItem[1].title }}</h5>
      <p class="card-text">
        <div>{{ selectedItem[1].addr1 }}</div>
        <div>{{ selectedItem[1].addr2 }}</div>
      </p>
      <button class="btn btn-primary" @click="closeItem" >취소
        </button>
      <button class="btn btn-danger">찜</button>
    </div>
  </div>
</template>

<style scoped></style>
