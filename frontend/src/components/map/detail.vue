<script setup>
import { ref, onMounted } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';


const date = ref();

const selectedItems = ref([])

onMounted(() => {
  console.log('history.state', history.state.selectedItems);
  selectedItems.value = history.state.selectedItems


  const startDate = new Date();
  // startDate.get
  const endDate = new Date(new Date().setDate(startDate.getDate() + 7));
  date.value = [startDate, endDate];
});

const test = () => {
  console.log('date', date.value)
  const data = date.value[0]
  console.log('start  date', date.value[0])
  const startDate = `${data.getFullYear()}.${1 + data.getUTCMonth()}.${data.getDate()}T`
  
  console.log('dd', data.getTime()
  )
  console.log('start', startDate)
}

</script>

<template>
<VueDatePicker v-model="date" range />

  <div class="bg-success" v-for="item in selectedItems" :key="item.id">
    <h5 >{{ item.title }}</h5>
      <p>
        <div>{{ item.addr1 }}</div>
        <div>{{ item.addr2 }}</div>
      </p>
  </div>
  <button @click="test">dd</button>
</template>

<style scoped></style>
