<script setup>
import { ref, onMounted } from 'vue';
import { getPlans } from '@/api/plan.js';
import { useRouter } from 'vue-router';
const router = useRouter();
// import router from '@/router';

const plans = ref();

onMounted(() => {
  console.log('we are good');
  getPlans(
    ({ data }) => {
      console.log('success', data.data);
      plans.value = data.data;
    },
    ({ error }) => {
      console.log('fail', error);
    }
  );
});

const moveDetail = (id) => {
  router.push({
    name: 'my-plans-detail',
    params: { id: id }
    // props: true
  });
  console.log('id', id);
};
</script>

<template>
  <section>
    <div class="trip-wrapper">
      <div
        v-if="plans == null || plans.length == 0"
        style="height: 300px; display: flex; justify-content: center; align-items: center"
      >
        <h1>저장된 여행 계획이 없습니다.</h1>
      </div>
      <template v-for="plan in plans" :key="plan.id">
        <div class="plan-item" @click="() => moveDetail(plan.planId)">
          <h2>
            <b>{{ plan.title }}</b>
          </h2>
          <p class="plan-item-content">
            여행기간 : {{ plan.startDateTime }} ~ {{ plan.endDateTime }}
          </p>
          <p class="plan-item-content">작성일 : {{ plan.registrationDate }}</p>
          <p class="plan-item-content memo">메모 : {{ plan.description }}</p>
        </div>
      </template>
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

.plan-item {
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  margin: 20px 0 40px 0;
  padding: 15px 50px;
  display: flex;
  justify-content: center;
  align-content: center;
  width: 100%;
  height: 200px;
  flex-direction: column;
  cursor: pointer;
}

.plan-item-content {
  margin: 0;
  width: 100%;
  overflow-wrap: break-word;
}
.plan-item-content.memo {
  margin: 0;
  width: 100%;
  overflow-wrap: break-word;
}
</style>
