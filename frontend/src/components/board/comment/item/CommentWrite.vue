<script setup>
import { ref } from 'vue';
import { loginStore } from '@/stores/LoginStore.js';
const loginstore = loginStore();
const { userId } = loginstore;
const props = defineProps({
  isMain: Boolean
});
const content = ref('');

const emits = defineEmits(['registingComment']);
function writeComment() {
  const commentForRegister = {
    comment: content.value
  };
  console.log('register', commentForRegister);
  emits('registingComment', commentForRegister);
  content.value = '';
}
</script>

<template>
  <hr v-if="isMain" />
  <div v-if="userId">
    <form v-if="isMain">
      <a-textarea id="content" v-model:value="content" placeholder="내용을 입력하세요" :rows="5" />
      <div style="width: 100%; text-align: right; padding-right: 10px; padding-top: 10px">
        <a-button @click="writeComment" type="primary">작성</a-button>
      </div>
    </form>
    <form v-if="!isMain" style="display: flex">
      <div style="margin: 10px 20px 0px 5px; font-size: 20px">└</div>
      <div style="width: 100%">
        <a-textarea
          id="content"
          v-model:value="content"
          placeholder="내용을 입력하세요"
          :rows="5"
        />
        <div style="width: 100%; text-align: right; padding-right: 10px; padding-top: 10px">
          <a-button @click="writeComment" type="primary" style="margin-right: 10px">작성</a-button>
          <a-button @click="$emit('toggleComment')" type="primary" danger>취소</a-button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
