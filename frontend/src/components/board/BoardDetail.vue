<script setup>
import {useRoute, useRouter} from 'vue-router'
import { ref, onMounted, reactive, onUpdated } from 'vue'
import { detailBoard, removeBoard } from '../../api/board'
import CommentList from "./comment/CommentList.vue";

const route = useRoute()
const router = useRouter()

const article = ref({
  articleNo: 0,
  subject: 0,
  registerDate: 0,
  hit: 0,
  content: 0,
});

onMounted(() => {
  getArticle()
})

const getArticle = () => {
  detailBoard(
    route.params.articleNo,
    ({ data }) => {
      console.log('get detail data : ', data)
      article.value = data
    },
    (error) => {
      console.log('error : ', error)
    }
  )
}

function moveList(){
  router.push({
    name:'board-list'
  })
}


function moveModify(){
  router.push({
    name: 'board-modify', 
    state:{
      article:{...article.value}
    }
  })
}

function onDeleteArticle() {
  removeBoard(
    article.value.articleNo,
    (data)=>{
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
          <h2 class="text-secondary px-5">{{ article.articleNo }}. {{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.userId }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerDate }}조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글 목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="m-2 bg-white">중간선</div>
  <CommentList :articleNo="article.articleNo"></CommentList>
</template>

<style scoped>
</style>
