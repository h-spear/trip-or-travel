<script setup>
import CommentWrite from './CommentWrite.vue';
import { ref, computed } from 'vue';
import router from '@/router';
import ReplyListItem from './ReplyListItem.vue';

const props = defineProps({
  comment: Object,
  commentId: Number
});

const clickUpdate = ref(true);
const clickCocoment = ref(false);
const comment = ref(props.comment);
const children = ref(props.comment.children);
const content = ref(props.comment.comment);
const modalOpen = ref(false);
const showModal = () => {
  modalOpen.value = true;
};

import { loginStore } from '@/stores/LoginStore.js';
const loginstore = loginStore();
const { userId } = loginstore;

const emits = defineEmits(['updatingComment', 'deletingComment', 'registingComment']);

function writeCocoment(comment) {
  const commentForRegister = {
    comment: comment.comment,
    parentCommentId: props.commentId
  };
  console.log('list item: cocoment', commentForRegister);
  emits('registingComment', commentForRegister);
  content.value = '';
}

function updateComment(data) {
  // if문에 들어오면 댓글, 아니면 답글
  let replyForUpdate = {};
  if (data.commentId === undefined) {
    clickUpdate.value = !clickUpdate.value;
    if (clickUpdate.value) {
      replyForUpdate = {
        commentId: comment.value.commentId,
        comment: content.value
      };
      emits('updatingComment', replyForUpdate);
    }
  } else {
    replyForUpdate = {
      commentId: data.commentId,
      comment: data.comment
    };
    emits('updatingComment', replyForUpdate);
  }
}

function deleteComment(data) {
  // if문에 들어오면 댓글, 아니면 답글
  let replyForDelete = {};
  if (data.commentId === undefined) {
    replyForDelete = {
      commentId: comment.value.commentId
    };
    emits('deletingComment', replyForDelete);
  } else {
    replyForDelete = {
      commentId: data.commentId
    };
    emits('deletingComment', replyForDelete);
  }
}

const openReplyForm = () => {
  toggleComment();
  console.log(document.querySelector('.comment-write'));
  document
    .getElementById(comment.value.commentId)
    .scrollIntoView({ behavior: 'smooth', block: 'center' });
};

function toggleComment() {
  clickCocoment.value = !clickCocoment.value;
}

const handleDeleteOk = (data) => {
  deleteComment(data);
  modalOpen.value = false;
};
</script>
<template>
  <a-comment>
    <template #actions>
      <span key="comment-nested-reply-to" @click="openReplyForm">답글</span>
      <div
        v-if="userId == comment.commenterId"
        @click="updateComment"
        style="font-size: 12px; cursor: pointer"
      >
        <span v-if="clickUpdate">수정</span>
        <span v-else>확인</span>
      </div>
      <div v-if="userId == comment.commenterId" style="margin-left: 10px">
        <span class="col" @click="showModal" style="font-size: 12px; cursor: pointer">삭제</span>
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

    <ReplyListItem
      v-for="child in children"
      :key="child.commentId"
      :child="child"
      @updating-reply="updateComment"
      @deleting-reply="deleteComment"
    />
  </a-comment>

  <div :id="comment.commentId">
    <CommentWrite
      class="comment-write"
      v-if="clickCocoment"
      @registing-comment="writeCocoment"
      @toggle-comment="toggleComment"
    ></CommentWrite>
  </div>

  <a-modal
    v-model:open="modalOpen"
    title="댓글을 삭제하시겠습니까?"
    @ok="handleDeleteOk"
    okText="삭제"
    cancelText="취소"
    width="400px"
    style="margin-top: 200px"
  >
    <p style="margin-top: 20px; margin-bottom: 40px">댓글을 삭제하면 복구할 수 없습니다.</p>
  </a-modal>
</template>

<style scoped></style>
