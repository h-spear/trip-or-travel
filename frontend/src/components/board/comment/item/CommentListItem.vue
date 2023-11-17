<script setup>
import CommentWrite from './CommentWrite.vue'
import { ref, computed } from 'vue'
import router from '@/router'
import ReplyListItem from './ReplyListItem.vue'

const props = defineProps({
  comment: Object,
  commentId: Number
})

const clickUpdate = ref(true)
const clickCocoment = ref(false)
const comment = ref(props.comment)
const children = ref(props.comment.children)
const content = ref(props.comment.comment)

import { loginStore } from '@/stores/LoginStore.js'
const loginstore = loginStore()
const { userId } = loginstore

const emits = defineEmits(['updatingComment', 'deletingComment', 'registingComment'])

function writeCocoment(comment) {
  const commentForRegister = {
    comment: comment.comment,
    parentCommentId: props.commentId
  }
  console.log('list item: cocoment', commentForRegister)
  emits('registingComment', commentForRegister)
  content.value = ''
}

function updateComment(data) {
  // if문에 들어오면 댓글, 아니면 답글
  let replyForUpdate = {}
  if (data.commentId === undefined){
    clickUpdate.value = !clickUpdate.value
    if (clickUpdate.value) {
      replyForUpdate = {
          commentId: comment.value.commentId,
          comment: content.value
        }
      emits('updatingComment', replyForUpdate)
    }
  } else{
    replyForUpdate = {
      commentId: data.commentId,
      comment: data.comment
    }
    emits('updatingComment', replyForUpdate)
  }
}

function deleteComment(data) {
  // if문에 들어오면 댓글, 아니면 답글
  let replyForDelete = {}
  if (data.commentId === undefined){
      replyForDelete = {
          commentId: comment.value.commentId,
      }
      emits('deletingComment', replyForDelete)
  } else{
    replyForDelete = {
      commentId: data.commentId,
    }
    emits('deletingComment', replyForDelete)
  }
}
</script>

<template>
  <div class="container list-group-item">
    <div class="row">
      <div class="col">
        <h4>
          <img id="profileImg" :src="comment.commenterProfileImageUrl" />{{
            comment.commenterNickname
          }}
        </h4>
      </div>
      <div class="col">
        {{ comment.registerTime }}
      </div>
      <textarea v-model="content" :readonly="clickUpdate" class="" cols="30" rows="5"></textarea>
    </div>
    <div class="row">
      <div v-if="userId == comment.commenterId">
        <button class="col" @click="updateComment">
          <div v-if="clickUpdate">수정</div>
          <div v-else>확인</div>
        </button>
        <button class="col" @click="deleteComment">삭제</button>
      </div>
      <div></div>
      <button class="col" @click="clickCocoment = !clickCocoment">
        <div v-if="!clickCocoment">답글</div>
        <div v-else>취소</div>
      </button>
    </div>
    <CommentWrite v-if="clickCocoment" @registing-comment="writeCocoment"></CommentWrite>
    <!-- 답글 부분 -->
    <ReplyListItem
      v-for="child in children"
      :key="child.commentId"
      :child="child"
      @updating-reply="updateComment"
      @deleting-reply="deleteComment"
    />
  </div>
</template>

<style scoped></style>
