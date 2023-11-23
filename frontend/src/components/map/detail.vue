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
    console.log('ind, val', index, value);
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
  return `${date.getFullYear()}-${1 + date.getUTCMonth()}-${date.getDate()}T${
    date.toTimeString().split(' ')[0]
  }`;
};

const registerPlan = () => {
  const plans = {
    title: title.value,
    description: description.value,
    startDateTime: bindToLocalDateTime(date.value[0]),
    endDateTime: bindToLocalDateTime(date.value[1]),
    planItems: planItems.value
  };
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

const goBack = () => {
  history.go(-1);
};
</script>

<template>
  <div class="container">
    <div class="row">sdfsf</div>
    <div class="row">sdfsf</div>
    <div class="row">sdfsf</div>
    <div class="row">sdfsf</div>
    <button @click="goBack()">뒤로 가기</button>
    <div>
      <form class="bg-white" action="" @submit.prevent="registerPlan">
        <label for="">제목</label>
        <input type="text" v-model.lazy="title" required />
        <VueDatePicker
          v-model="date"
          range
          required
          :inline="{ input: true }"
          text-input
          auto-apply
        />
        <label for="">설명</label>
        <input type="text" v-model.lazy="description" required />
        <div>
          <DetailItem
            class="bg-success m-2"
            v-for="[index, item] of selectedItems.entries()"
            :key="item.id"
            :item="item"
            :index="index"
            @confirm-each-plan="modifyEachPlan"
          ></DetailItem>
        </div>

        <button type="submit">저장</button>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
