<script setup>
import { ref } from 'vue';
import { loginStore } from '@/stores/LoginStore.js';
const loginstore = loginStore();
const { userId } = loginstore;

const content = ref('');

const emits = defineEmits(['registingComment']);
function writeComment() {
  const commentForRegister = {
    comment: content.value
  };
  emits('registingComment', commentForRegister);
  content.value = '';
}
</script>

<template>
  <hr />
  <div v-if="userId">
    <form>
      <a-textarea id="content" v-model:value="content" placeholder="내용을 입력하세요" :rows="5" />
      <!-- <button type="submit" class="btn btn-success" value="">등록</button> -->
      <div style="width: 100%; text-align: right; padding-right: 10px; padding-top: 10px">
        <a-button @click="writeComment" type="primary" style="margin-right: 10px">작성</a-button>
        <a-button @click="$emit('toggleComment')" type="primary" danger>취소</a-button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
