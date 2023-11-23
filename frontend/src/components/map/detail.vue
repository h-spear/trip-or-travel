<script setup>
import { ref, reactive, onMounted } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import DetailItem from '@/components/map/item/DetailItem.vue';
import { useRouter } from 'vue-router';
const router = useRouter();
import { registPlan } from '@/api/plan.js';

const date = ref();

const title = ref('');
const description = ref('');

const selectedItems = ref([]);
const planItems = ref([]);

onMounted(() => {
  selectedItems.value = history.state.selectedItems;
  for (const [index, value] of selectedItems.value.entries()) {
    planItems.value.push({
      attractionId: value.id,
      memo: '',
      startDateTime: '',
      endDateTime: '',
      order: index
    });
  }
});

const bindToLocalDateTime = (date) => {
  let myMonth = 1 + date.getMonth();
  let myDate = date.getDate();
  myMonth = myMonth < 10 ? '0' + myMonth : myMonth;
  myDate = myDate < 10 ? '0' + myDate : myDate;
  return `${date.getFullYear()}-${myMonth}-${myDate}T${date.toTimeString().split(' ')[0]}`;
};

const registerPlan = () => {
  const items = [];
  for (const [index, value] of selectedItems.value.entries()) {
    console.log('ind, val', index, value);
    items.push({
      attractionId: value.id,
      memo: '',
      startDateTime: '',
      endDateTime: '',
      order: index
    });
  }
  const plans = {
    title: title.value,
    description: description.value,
    startDateTime: bindToLocalDateTime(date.value[0]),
    endDateTime: bindToLocalDateTime(date.value[1]),
    planItems: items
  };
  console.log('a', items);
  console.log(plans);
  registPlan(
    plans,
    ({ data }) => {
      console.log('success', data);
      alert('성공적으로 등록되었습니다.');
      router.push({
        name: 'main'
      });
    },
    ({ error }) => {
      console.log('fail', error);
    }
  );
};

const modifyEachPlan = (item) => {
  planItems.value[item.order] = item;
  console.log('going', planItems.value);
};
</script>

<template>
  <section>
    <div class="trip-wrapper">
      <h1 style="font-weight: 700; text-align: center; margin: 10px 0 30px 0">여행 계획 작성</h1>
      <hr style="margin-bottom: 30px" />
      <!-- <button @click="() => history.go(-1)">뒤로 가기</button> -->
      <div>
        <form action="" @submit.prevent="registerPlan">
          <div style="display: flex; align-items: center; justify-content: space-between">
            <div class="input-box" style="width: 48%">
              <label class="input-label">이름</label>
              <a-input
                v-model:value.lazy="title"
                placeholder="여행 계획의 이름을 입력하세요."
                size="large"
              />
            </div>
            <div class="input-box" style="width: 48%">
              <label class="input-label">기간</label>
              <VueDatePicker v-model="date" range required text-input auto-apply />
            </div>
          </div>
          <div style="margin-top: 10px">
            <label class="input-label" style="margin: 10px 0">메모</label>
            <a-textarea
              :rows="7"
              placeholder="메모를 입력하세요"
              :maxlength="100"
              v-model:value.lazy="description"
            />
          </div>
          <div style="margin-top: 30px">
            <label class="input-label" style="width: 100%">여행 세부 계획</label>
            <DetailItem
              v-for="[index, item] of selectedItems.entries()"
              :key="item.id"
              :item="item"
              :index="index"
              @confirm-each-plan="modifyEachPlan"
            ></DetailItem>
          </div>

          <a-button
            type="primary"
            style="
              width: 100%;
              font-size: 18px;
              margin-top: 20px;
              background-color: rgb(24, 24, 24);
            "
            size="large"
            @click="registerPlan"
            >여행 계획 저장</a-button
          >
        </form>
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
  padding: 30px 50px;
  align-items: center;
  height: 100%;
  width: 100%;
}

.input-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-label {
  width: 70px;
  font-weight: 700;
  font-size: 20px;
}
</style>
