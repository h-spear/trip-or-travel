<script setup>
import { ref } from 'vue';
defineProps({ selectOptions: Array });
const emit = defineEmits(['onKeySelect']);
const key = ref('all');
const onSelect = () => {
  emit('onKeySelect', key.value);
};
</script>
<template>
  <select class="form-select" v-model="key" @change="onSelect">
    <option v-for="(k, index) in selectOptions" :key="index" :value="k.value" selected>
      {{ k.text }}
    </option>
  </select>
</template>
<style scoped>
select {
  background: transparent;
  color: #fff;
  width: 45%;
  padding: 10px;
}

option {
  background-color: rgba(133, 4, 4, 0.555);
  color: black;
}
</style>
