<script setup>
import { ref, reactive, onMounted } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import detailItem from '@/components/map/item/detailItem.vue';
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
  console.log(selectedItems.value.entries());
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

const test = () => {
  console.log('date', date.value);
  const data = date.value[0];
  const startDate = `${data.getFullYear()}-${1 + data.getUTCMonth()}-${data.getDate()}T${
    data.toTimeString().split(' ')[0]
  }`;
  console.log('start', startDate);

  console.log('dfd', JSON.parse(JSON.stringify(selectedItems.value)));
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
</script>

<template>
  <div>
    <form class="bg-white" action="" @submit.prevent="registerPlan">
      <label for="">제목</label>
      <input type="text" v-model.lazy="title" required />
      <VueDatePicker v-model="date" range required />
      <label for="">설명</label>
      <input type="text" v-model.lazy="description" required />
      <div>
        <detailItem
          class="bg-success m-2"
          v-for="[index, item] of selectedItems.entries()"
          :key="item.id"
          :item="item"
          :index="index"
          @confirm-each-plan="modifyEachPlan"
        ></detailItem>
      </div>

      <button type="submit">저장</button>
    </form>
  </div>
</template>

<style scoped></style>
