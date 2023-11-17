<script setup>
import CommentWrite from './CommentWrite.vue'
import { ref, computed } from 'vue'
import router from '@/router'
const props = defineProps({
  child: Object,
  commentId: Number
})

const clickUpdate = ref(true)
const modal = ref(true)
const comment = ref(props.child)
const content = ref(props.child.comment)

import { loginStore } from '@/stores/LoginStore.js'
const loginstore = loginStore()
const { userId } = loginstore




const emits = defineEmits(['updatingReply', 'deletingReply'])

function updateReply() {
  clickUpdate.value = !clickUpdate.value
  if (clickUpdate.value) {
    const replyForUpdate = {
      commentId: comment.value.commentId,
      comment: content.value
    }
    emits('updatingReply', replyForUpdate)
  }
}

function deleteReply() {
  // 모달로 삭제할 것인지 물어보면 좋을 듯
  console.log('delete')
  const replyForDelete = {
      commentId: comment.value.commentId,
    }
  emits('deletingReply', replyForDelete)
}
</script>

<template>
  <div class="list-group-item m-5">
    <div class="row">
      <div class="col">
        <h4>
          <img id="profileImg" :src="child.commenterProfileImageUrl" />{{ child.commenterNickname }}
        </h4>
      </div>
      <textarea :readonly="clickUpdate" class="" cols="30" rows="5" v-model="content"></textarea>
    </div>
    <div class="row">
      <div v-if="userId == child.commenterId">
        <button class="col" @click="updateReply">
          <div v-if="clickUpdate">수정</div>
          <div v-else>확인</div>
        </button>
        <button class="col" @click="deleteReply">삭제</button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
