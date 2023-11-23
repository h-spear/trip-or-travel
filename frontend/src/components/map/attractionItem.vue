<script setup>
import { ref } from 'vue';
const props = defineProps({
  position: Object
});

const clicked = ref(false);
const emits = defineEmits(['selectItem', 'unselectItem', 'searchAround']);

const searchAround = () => {
  console.log('searchAround', props.position);
  emits('searchAround', props.position.latitude, props.position.longitude, 1);
};

const selectItem = () => {
  clicked.value = true;
  emits('selectItem', props.position);
};
const unselectItem = () => {
  clicked.value = false;
  emits('unselectItem', props.position);
};

const notPrepare = () => {
  alert('준비중입니다!');
};
</script>

<template>
  <div class="attraction-card" style="">
    <div class="image-box">
      <img
        src="@/assets/image/no-picture.png"
        v-if="position.imageUrl == ''"
        style="height: 100%; width: 100%; object-fit: cover"
        alt="..."
      />
      <img
        :src="position.imageUrl"
        v-if="position.imageUrl != ''"
        style="height: 100%; width: 100%; object-fit: cover"
        alt="..."
      />
    </div>
    <h5 class="attraction-card-title">{{ position.title }}</h5>
    <div class="attraction-card-btnBox">
      <a-button class="my-btn" v-if="!clicked" type="primary" @click="selectItem">등록</a-button>
      <a-button class="my-btn" v-else type="primary" @click="unselectItem" danger>삭제</a-button>
      <a-button class="my-btn a-button-custom" type="primary" @click="searchAround"
        >주변 볼거리</a-button
      >
      <a-button class="my-btn a-button-custom2" type="primary" @click="notPrepare">찜</a-button>
    </div>
  </div>
</template>

<style scoped>
.image-box {
  padding: 7px;
  width: 100%;
  height: 180px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.attraction-card {
  background: white;
  border: 1px solid black;
  border-radius: 10px;
  -webkit-box-shadow: 2px 2px 7px 2px rgba(0, 0, 0, 0.54);
  box-shadow: 2px 2px 7px 2px rgba(0, 0, 0, 0.54);
  margin: 15px 0;
  position: relative;
}

.attraction-card-title {
  text-align: center;
  font-size: 16px;
  font-weight: 700;
  padding: 0 8px;
  margin-top: 6px;
}
.a-button-custom {
  background-color: #198754;
}
.a-button-custom:hover {
  background-color: #146c43;
}
.a-button-custom2 {
  background-color: #a02cc4;
}
.a-button-custom2:hover {
  background-color: #6d1987;
}

.attraction-card-btnBox {
  margin: 10px 0;
  display: flex;
  justify-content: center;
  align-content: center;
}

.my-btn {
  margin: 0 3px;
}
</style>
