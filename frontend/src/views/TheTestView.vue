<script setup>
import { ref } from 'vue';
import { uploadImage } from '@/api/image.js';

const image = ref(null);

let tmp = new Map();
tmp.set(1, 12);
tmp.set(2, 22);
tmp.set(3, 32);
tmp.set(4, 42);

tmp.foreach((data) => {
  console.log(data);
});

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
  <div class="mt-4 p-5 bg-primary text-white rounded"></div>
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
