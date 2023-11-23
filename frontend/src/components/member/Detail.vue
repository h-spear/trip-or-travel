<script setup>
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import { getPlanDetail } from '@/api/plan.js';
const route = useRoute();

const plan = ref({
  title: '',
  startDateTime: '',
  endDateTime: '',
  description: '',
  planItems: []
});

onMounted(() => {
  console.log(route.params);
  getPlanDetail(
    route.params.id,
    ({ data }) => {
      plan.value = data.data;
      console.log('success', data);
    },
    ({ error }) => {
      console.log('failed', error);
    }
  );
});
</script>

<template>
  <section>
    <div class="trip-wrapper">
      <a-page-header style="width: 100%" :title="plan.title" @back="() => $router.go(-1)" />
      <hr style="margin-bottom: 30px" />
      <div>
        <p class="item-card">{{ plan.description }}</p>
      </div>

      <div style="margin-top: 30px">
        <label
          class="input-label"
          style="width: 100%; font-size: 24px; font-weight: 700; text-align: center"
          >여행 세부 계획</label
        >
        <h5 style="font-weight: 400; text-align: center; margin-top: 10px">
          {{ plan.startDateTime }} ~ {{ plan.endDateTime }}
        </h5>
      </div>
      <div v-for="item in plan.planItems" :key="item.id">
        <div class="item-card">
          <div class="image-box">
            <img
              src="@/assets/image/no-picture.png"
              v-if="item.attractionImageUrl == ''"
              style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
              alt="..."
            />
            <img
              :src="item.attractionImageUrl"
              v-if="item.attractionImageUrl != ''"
              style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
              alt="..."
            />
          </div>
          <div class="info-box">
            <div style="display: flex; justify-content: space-between; align-items: center">
              <div>
                <h5 class="item-card-title">
                  {{ item.attractionTitle
                  }}<span style="font-size: 20px"> ({{ item.attractionContentType }})</span>
                </h5>
                <p>{{ item.attractionAddr1 }} {{ item.attractionAddr2 }}</p>
                <p v-if="item.startDateTime != null || item.endDateTime != null">
                  <span v-if="item.startDateTime != null">{{ item.startDateTime }}</span> ~

                  <span v-if="item.endDateTime != null">{{ item.endDateTime }}</span>
                </p>
                <p>
                  메모 : <b>{{ item.memo }}</b>
                </p>
                <p>위도 : {{ item.latitude }}</p>
                <p>경도 : {{ item.longitude }}</p>
              </div>
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
.trip-wrapper {
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

.item-card {
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  margin: 20px 0 40px 0;
  padding: 15px;
  display: flex;
  justify-content: center;
  align-content: center;
  min-height: 150px;
}

.image-box {
  width: 150px;
  height: 150px;
}

.info-box {
  width: 800px;
  margin-left: 20px;
}

.info-box p {
  margin: 4px 0;
}
::v-deep .ant-page-header {
  display: flex;
  align-items: center;
  justify-content: center;
}

::v-deep .ant-page-header-heading {
  display: flex;
  align-items: center;
}
::v-deep .ant-page-header-heading-title {
  font-size: 40px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}

::v-deep .ant-page-header-heading-sub-title {
  font-size: 14px;
  height: 50px;
  line-height: 80px;
}
</style>
