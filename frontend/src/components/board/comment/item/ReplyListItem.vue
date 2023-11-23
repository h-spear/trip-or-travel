<script setup>
import CommentWrite from './CommentWrite.vue';
import { ref, computed } from 'vue';
import router from '@/router';
const props = defineProps({
  child: Object,
  commentId: Number
});

const clickUpdate = ref(true);
const modal = ref(true);
const comment = ref(props.child);
const content = ref(props.child.comment);

import { loginStore } from '@/stores/LoginStore.js';
const loginstore = loginStore();
const { userId } = loginstore;

const emits = defineEmits(['updatingReply', 'deletingReply']);

function updateReply() {
  console.log(1);
  clickUpdate.value = !clickUpdate.value;
  if (clickUpdate.value) {
    const replyForUpdate = {
      commentId: comment.value.commentId,
      comment: content.value
    };
    emits('updatingReply', replyForUpdate);
  }
}

function deleteReply() {
  // 모달로 삭제할 것인지 물어보면 좋을 듯
  console.log('delete');
  const replyForDelete = {
    commentId: comment.value.commentId
  };
  emits('deletingReply', replyForDelete);
}
</script>

<template>
  <a-comment>
    <template #actions v-if="userId == comment.commenterId">
      <div
        v-if="userId == comment.commenterId"
        @click="updateReply"
        style="font-size: 12px; cursor: pointer"
      >
        <span v-if="clickUpdate">수정</span>
        <span v-else>확인</span>
      </div>
      <div v-if="userId == comment.commenterId" style="margin-left: 10px">
        <span class="col" @click="deleteReply" style="font-size: 12px; cursor: pointer">삭제</span>
      </div>
    </template>
    <template #author>
      <a style="font-size: 16px">{{ comment.commenterNickname }}</a>
      /
      {{
        comment.registrationDate
          .replace('T', ' ')
          .substring(0, comment.registrationDate.indexOf('.'))
      }}
    </template>
    <template #avatar>
      <a-avatar :src="comment.commenterProfileImageUrl" alt="ProfileImage" />
    </template>
    <template #content>
      <p v-show="clickUpdate">{{ comment.comment }}</p>
      <a-textarea v-show="!clickUpdate" v-model:value="content" placeholder="" :rows="5" />
    </template>
  </a-comment>
</template>

<style scoped></style>
