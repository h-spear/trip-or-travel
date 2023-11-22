<script setup>
import VueDatePicker from '@vuepic/vue-datepicker';
import { ref, watch, onMounted } from 'vue';

const props = defineProps({
  item: Object,
  index: Number
});
const emits = defineEmits(['confirmEachPlan']);

const memo = ref('');
const date = ref([new Date(),new Date()]);

watch(() => {
  return [memo.value, date.value]
}, (newVal, oldVal) => {
  console.log('newVal , oldVal', newVal, oldVal)
  const data = {
    attractionId: props.item.id,
      memo: newVal[0],
      startDateTime: bindToLocalDateTime(newVal[1][0]),
      endDateTime: bindToLocalDateTime(newVal[1][1]),
      order: props.index
  }
  //완성된 형태로 제공
  emits('confirmEachPlan', data)
},
  {
    deep: true,
  }
)

const bindToLocalDateTime = (date) => {
  return `${date.getFullYear()}-${1 + date.getMonth()}-${date.getDate()}T${
    date.toTimeString().split(' ')[0]
  }`;
};
</script>

<template>
  <div>
    <h5>{{ item.title }}</h5>
    <p>
      <div>{{ item.addr1 }}</div> 
      <div>{{ item.addr2 }}</div>
    </p>
    <VueDatePicker v-model="date" range />
    <input type="text" v-model.lazy="memo" >
  </div>
</template>

<style scoped></style>
