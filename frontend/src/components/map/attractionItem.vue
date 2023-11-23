<script setup>
import { ref, onUpdated } from 'vue';
const props = defineProps({
  position: Object
});

const clicked = ref(false)
const emits = defineEmits(['selectItem', 'unselectItem', 'searchAround'])


const searchAround = () => {
  console.log('searchAround', props.position)
  // 반경 얼마 추천할지도 여기에서 처리한다. 
  emits('searchAround', props.position.latitude, props.position.longitude, 1)
}

const selectItem = () => {
  clicked.value = true
  emits('selectItem', props.position)
}
const unselectItem = () => {
  clicked.value = false
  emits('unselectItem', props.position)
}
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
      <button v-if="!clicked" class="btn btn-primary" @click="selectItem">
        등록
        </button>
        <button v-else class="btn btn-primary" @click="unselectItem">
            취소
            </button>
      <button class="btn btn-danger">찜</button>
      <button class="btn btn-success" @click="searchAround">주변 추천</button>
    </div>
  </div>
</template>

<style scoped></style>
