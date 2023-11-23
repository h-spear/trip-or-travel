<script setup>
import { useRoute } from 'vue-router';
import { ref, onMounted, watch, computed, onUpdated } from 'vue';
import { localAxios } from '../utils/http-commons';

const local = localAxios();
const route = useRoute();

const boardId = ref(route.query.boardId);
const title = ref('');
const description = ref('');

onUpdated(() => {
  getBoardInfo();
});
onMounted(() => {
  getBoardInfo();
});

const getBoardInfo = () => {
  boardId.value = route.query.boardId;
  local
    .get(`board/${boardId.value}`)
    .then((data) => {
      data = data.data;
      if (data.status === 'success') {
        title.value = data.data.name;
        description.value = data.data.description;
      }
    })
    .catch((error) => {
      console.log('error : ', error);
    });
};
</script>

<template>
  <section>
    <div class="board-wrapper">
      <a-page-header
        style="width: 100%"
        :title="title"
        :sub-title="description"
        @back="() => $router.go(-1)"
      />
      <hr style="width: 100%; margin: 0px 0 30px 0" />
      <router-view></router-view>
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
.board-wrapper {
  background: #ffffff;
  border-radius: 20px;
  /* border: 1px solid rgba(255, 255, 255, 0.5); */
  /* backdrop-filter: blur(15px); */
  -webkit-box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 30px 40px;
}

::v-deep .ant-page-header-heading {
  display: flex;
  height: 80px;
  align-items: center;
}
::v-deep .ant-page-header-heading-title {
  font-size: 40px;
  height: 50px;
  line-height: 50px;
}

::v-deep .ant-page-header-heading-sub-title {
  font-size: 14px;
  height: 50px;
  line-height: 80px;
}
</style>
