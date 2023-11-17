<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted, reactive, onUpdated } from 'vue'
import { detailBoard, removeBoard } from '../../api/board'
import CommentList from './comment/CommentList.vue'
import { loginStore } from '@/stores/LoginStore.js'
const loginstore = loginStore()
const { userId } = loginstore

const route = useRoute()
const router = useRouter()
const postId = Number(route.params.postId)

const post = ref({
  postId: 0,
  title: 0,
  registrationDate: 0,
  views: 0,
  content: 0,
  commentCount: 0,
  likes: 0
})

const getPost = () => {
  detailBoard(
    postId,
    ({ data }) => {
      console.log('get detail data : ', data.data)
      post.value = data.data
      console.log('detail', post.value)
    },
    (error) => {
      console.log('get post error : ', error)
    }
  )
}

getPost()

function moveList() {
  router.push({
    name: 'board-list'
  })
}

function moveModify() {
  router.push({
    name: 'board-modify',
    state: {
      post: { ...post.value }
    }
  })
}

function onDeleteArticle() {
  removeBoard(
    postId,
    (data) => {
      console.log('delete complete', data)
      moveList()
      // 댓글도 전부 지우는 작업이 함께 들어가야만 한다.
      // deleteCommentAll 필요
    },
    (error) => {
      console.log('error : ', error)
    }
  )
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ post.postId }}. {{ post.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                id="profileImg"
                class="avatar me-2 float-md-start bg-light p-2"
                :src="post.writerProfileImageUrl"
              />
              <p>
                <span class="fw-bold">{{ post.writerNickname }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ post.registrationDate }} 조회 : {{ post.views }}
                </span>
              </p>
            </div>
          </div>
          <!-- <div class="col-md-4 align-self-center text-end">댓글 : {{ post.commentCount }}</div> -->
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ post.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글 목록
            </button>
            <template v-if="post.writerId == userId">
              <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
                글수정
              </button>
              <button
                type="button"
                class="btn btn-outline-danger mb-3 ms-1"
                @click="onDeleteArticle"
              >
                글삭제
              </button>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="m-2 bg-white">중간선</div>
  <CommentList :postId="postId"></CommentList>
</template>

<style scoped></style>
