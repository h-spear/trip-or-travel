<script setup>
import { ref } from 'vue';
import { localAxios } from '../utils/http-commons';
import { Modal } from 'ant-design-vue';
import { h } from 'vue';

const contentTypes = ref({
  12: '관광지',
  14: '문화시설',
  15: '축제공연행사',
  28: '레포츠',
  32: '숙박',
  38: '쇼핑',
  39: '음식점'
});
const typeColor = ref({
  12: 'red',
  14: 'orange',
  15: 'yellow',
  28: 'green',
  32: 'blue',
  38: 'purple',
  39: 'pink'
});
const local = localAxios();
const attractionsByType = ref(null);
const info = (contentId) => {
  local
    .get(`attraction/${contentId}`)
    .then(({ data }) => {
      data = data.data;
      console.log(data);
      Modal.info({
        title: `${data.title}`,
        content: h('div', {}, [
          h('p', `${data.addr1} ${data.addr2}`),
          h('p', `${data.overview != null ? data.overview : '설명이 준비되지 않았습니다.'}`)
        ]),
        onOk() {
          console.log('ok');
        }
      });
    })
    .catch((error) => {
      console.log('error : ', error);
    });
};
local
  .get(`attraction/top?top=5`)
  .then(({ data }) => {
    attractionsByType.value = data.data;
  })
  .catch((error) => {
    console.log('error : ', error);
  });
</script>

<template>
  <section>
    <div class="home-wrapper">
      <img src="@/assets/image/paint.png" alt="paint" style="width: 100%" />
      <h1
        style="
          font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
          width: 100%;
          text-align: center;
          font-size: 60px;
        "
      >
        Trip or Travel
      </h1>
      <p style="width: 100%; text-align: center">여행지 정보와 여행 계획 서비스를 제공합니다.</p>

      <div
        v-for="(attractions, tId) of attractionsByType"
        class="type-wrapper"
        :style="{ 'border-top-color': typeColor[tId] }"
        :key="tId"
      >
        <h3 class="home-title">{{ contentTypes[tId] }} Hot Place</h3>
        <div class="card-container">
          <div
            v-for="attraction in attractions"
            class="home-card"
            :key="attraction.id"
            @click="() => info(attraction.id)"
          >
            <div class="image-box">
              <img
                src="@/assets/image/no-picture.png"
                v-if="attraction.imageUrl == ''"
                style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
                alt="..."
              />
              <img
                :src="attraction.imageUrl"
                v-if="attraction.imageUrl != ''"
                style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
                alt="..."
              />
            </div>
            <p
              style="
                font-weight: bold;
                text-align: center;
                width: 100%;
                padding: 0 10px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              "
            >
              {{ attraction.title }}
            </p>
            <div style="margin-left: 30px">
              <p style="margin-top: 10px">추천 : {{ attraction.likeCount }}</p>
              <p>방문 : {{ attraction.readCount }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
section {
  display: flex;
  margin: 0;
  position: relative;
  width: 100vw;
  min-width: 800px;
  max-width: 1400px;
  height: 100%;
  padding: 100px 50px 30px 50px;
}
.home-wrapper {
  background: #ffffff;
  border-radius: 20px;
  -webkit-box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  min-width: 800px;
  max-width: 1400px;
  padding: 20px 30px;
  align-items: center;
  height: 100%;
  width: 100%;
}

.home-title {
  font-size: 30px;
  font-weight: bold;
  margin: 20px 0 0 0;
}

.type-wrapper {
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  align-items: center;
  border-top: 2px solid black;
  margin: 20px 0;
  padding: 20px 0;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
  padding: 20px 0;
  position: relative;
}

.home-card {
  margin: 30px 20px;
  width: 180px;
  height: 250px;
  -webkit-box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.33);
  cursor: pointer;
  box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.33);
}
.home-card p {
  margin: 0;
}

.image-box {
  padding: 10px;
  width: 100%;
  height: 150px;
}
</style>
