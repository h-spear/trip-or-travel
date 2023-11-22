<script setup>
import { ref, onUpdated } from 'vue';
const props = defineProps({
  position: Object
});

const clicked = ref(false)
const emits = defineEmits(['selectOrNot'])

const select = () => {
  clicked.value = !clicked.value
  
  emits('selectOrNot', clicked.value, props.position)
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
      <button class="btn btn-primary" @click="select">
        <div v-if="!clicked">
          등록
          </div>
          <div v-else>
            취소
            </div>
        </button>
      <button class="btn btn-danger">찜</button>
      <button class="btn btn-success">주변 추천</button>
    </div>
  </div>
</template>

<style scoped></style>
