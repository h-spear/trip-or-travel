<script setup>
import CommentWrite from './item/CommentWrite.vue'
import CommentListItem from './item/CommentListItem.vue'
import { ref, onMounted } from 'vue'
import { listComment, registComment } from '@/api/comment.js'
import { useRoute } from 'vue-router'
import { updateComment, removeComment } from '../../../api/comment'
import { loginStore } from '@/stores/LoginStore.js'
import { commentStore } from '@/stores/CommentStore.js'
import { storeToRefs } from 'pinia'
const loginstore = loginStore()
const { userId, userProfile, userNickname } = storeToRefs(loginstore)
const commentstore = commentStore()
const { postId, commentList } = storeToRefs(commentstore)

const route = useRoute()
const comments = ref([])

onMounted(() => {
  getCommentList()
})

const comment = ref({
  children: [],
  childrenCount: 0,
  comment: '',
  commentId: 0,
  commenterId: 0,
  commenterNickname: '',
  commenterProfileImageUrl: ''
})

comment.value.userId = userId
// 토큰이나 provide로 뿌려야 할 듯
comment.value.postId = route.params.postId

function writeComment(data) {
  console.log(data.content)
  comment.value = data
  if (comment.value.comment == '') {
    alert('내용을 적어주세요')
  } else {
    registComment(
      comment.value,
      (data) => {
        console.log('registed ', data.data)
        //효율적인 업데이트를 위해 다시 디비 조회..
        getCommentList()
      },
      (error) => {
        console.log('error ', error)
      }
    )
  }
}

function onUpdateComment(data) {
  updateComment(
    data,
    ({ data }) => {
      console.log('updated comment : ', data)
      getCommentList()
    },
    (error) => {
      console.log('error : ', error)
    }
  )
}

function onDeleteComment(data) {
  removeComment(
    data,
    ({ data }) => {
      console.log('deleted comment : ', data)
      getCommentList()
    },
    (error) => {
      console.log('error : ', error)
    }
  )
}
</script>

<template>
  <div class="container">
    <ol class="list-group list-group-numbered">
      <CommentListItem
        class=""
        v-for="comment in comments"
        :key="comment.commentId"
        :comment="comment"
        @updating-comment="onUpdateComment"
        @deleting-comment="onDeleteComment"
      >
      </CommentListItem>
    </ol>

    <CommentWrite :comments="comments" @registing-comment="writeComment"></CommentWrite>
  </div>
</template>

<style scoped></style>
