<script setup>
import { ref } from 'vue'
import { loginStore } from '@/stores/LoginStore.js'
const loginstore = loginStore()
const { userId } = loginstore

const content = ref('')

const emits = defineEmits(['registingComment'])
function writeComment() {
  const commentForRegister = {
    comment: content.value
  }
  emits('registingComment', commentForRegister)
  content.value = ''
}
</script>

<template>
  <div v-if="userId">
    <form @submit.prevent="writeComment">
      <label for="content" class="d-md-flex justify-content-md-start">댓글 달기</label>
      <textarea
        type="text"
        id="content"
        class="container"
        placeholder="댓글을 적어주세요"
        v-model="content"
      ></textarea>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <button type="submit" class="btn btn-success" value="">등록</button>
      </div>
    </form>
  </div>
  <div v-else>
    <div>로그인 후 이용해주세요!</div>
  </div>
</template>

<style scoped></style>
