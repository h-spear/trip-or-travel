<script setup>
import { ref } from 'vue';
const props = defineProps({
  selectOptions: Array,
  width: Number,
  defaultKey: String
});
const emit = defineEmits(['onKeySelect']);
const key = ref(props.defaultKey);
const onSelect = () => {
  console.log('vselect................... key:', key.value);
  emit('onKeySelect', key.value);
};
</script>
<template>
  <a-select
    ref="select"
    v-model:value="key"
    :options="sidos"
    @change="onSelect"
    :style="{ width: width + 'px' }"
  >
    <a-select-option :value="null">{{ defaultKey }}</a-select-option>
    <a-select-option v-for="(k, index) in selectOptions" :key="index" :value="k.value">{{
      k.text
    }}</a-select-option>
  </a-select>
</template>
<style scoped></style>
