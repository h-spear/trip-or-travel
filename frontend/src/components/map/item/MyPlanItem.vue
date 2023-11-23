<script setup>
import VueDatePicker from '@vuepic/vue-datepicker';
import { ref, watch, onMounted } from 'vue';

const props = defineProps({
  item: Object,
  index: Number
});
const emits = defineEmits(['confirmEachPlan']);

const memo = ref('');
const date = ref([new Date(), new Date()]);

watch(
  () => {
    return [memo.value, date.value];
  },
  (newVal, oldVal) => {
    console.log('newVal , oldVal', newVal, oldVal);
    const data = {
      attractionId: props.item.id,
      memo: newVal[0],
      startDateTime: bindToLocalDateTime(newVal[1][0]),
      endDateTime: bindToLocalDateTime(newVal[1][1]),
      order: props.index
    };
    //완성된 형태로 제공
    emits('confirmEachPlan', data);
  },
  {
    deep: true
  }
);

const bindToLocalDateTime = (date) => {
  let myMonth = 1 + date.getMonth();
  let myDate = date.getDate();
  myMonth = myMonth < 10 ? '0' + myMonth : myMonth;
  myDate = myDate < 10 ? '0' + myDate : myDate;
  return `${date.getFullYear()}-${myMonth}-${myDate}T${date.toTimeString().split(' ')[0]}`;
};
</script>

<template>
  <div class="item-card">
    <div class="image-box">
      <img
        src="@/assets/image/no-picture.png"
        v-if="item.imageUrl == ''"
        style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
        alt="..."
      />
      <img
        :src="item.imageUrl"
        v-if="item.imageUrl != ''"
        style="height: 100%; width: 100%; border-radius: 10px; object-fit: cover"
        alt="..."
      />
    </div>
    <div class="info-box">
      <div style="display: flex; justify-content: space-between; align-items: center">
        <div>
          <h5 class="item-card-title" s>
            {{ item.title }}<span style="font-size: 20px"> ({{ item.contentType }})</span>
          </h5>
          <p>{{ item.addr1 }} {{ item.addr2 }}</p>
        </div>
        <VueDatePicker required text-input auto-apply v-model="date" style="width: 350px" range />
      </div>
      <a-textarea
        :rows="4"
        placeholder="메모를 입력하세요"
        :maxlength="1000"
        v-model:value.lazy="memo"
      />
    </div>
  </div>
</template>

<style scoped>
.item-card {
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  margin: 20px 0 40px 0;
  padding: 15px;
  display: flex;
  justify-content: center;
  align-content: center;
}

.item-card-title {
  font-weight: 700;
  font-size: 24px;
  width: 300px;
}

.image-box {
  width: 200px;
  height: 200px;
}

.info-box {
  width: 80%;
  padding: 20px 20px;
}
</style>
