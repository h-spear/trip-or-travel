<script setup>
import { ref, onUpdated } from 'vue';
import { uploadImage } from '@/api/image.js';
import { VueDraggableNext } from 'vue-draggable-next';

const image = ref(null);

const tmp = ref([]);
tmp.value.push({
  t1: 1,
  t2: 2
});
tmp.value.push({
  t1: 3,
  t2: 4
});

const test = () => {
  console.log(tmp.value);
};

function getFileName(data) {
  const fileReader = new FileReader();
  fileReader.readAsDataURL(data[0]);
  fileReader.onload = () => {
    console.log('value', fileReader);
    uploadImage(
      fileReader.result,
      ({ data }) => {
        console.log('success', data);
        image.value = data.data.url;
      },
      (error) => {
        console.log('error ', error);
      }
    );
  };
}
</script>

<template>
  <div class="container">
    <div class="row ">asdf</div>
    <div class="row">asdf</div>
    <div class="row">asdf</div>
    <div class="row">asdf</div>
    <div class="row wrapper">asdf</div>
  </div>
  <div class="mt-4 p-5 bg-primary text-white rounded">sdfs</div>
  <VueDraggableNext :list="tmp" class="bg-success">
    <div v-for="item in tmp" :key="item.t1">
      {{ item.t2 }}
    </div>
  </VueDraggableNext>
  <button class="btn btn-success" @click="test">테스트</button>
  <!-- <div class="mt-4 p-5 bg-primary text-white rounded">
    <h1>이곳은 테스트페이지입니다</h1>
    <img :src="image" alt="testing" />
    <div>
      <input type="file" id="upload-image" hidden @change="getFileName($event.target.files)" />
      <label for="upload-image">
        <img src="@/assets/ssafy_logo.png" />
      </label>
    </div>
  </div> -->
</template>

<style scoped></style>
