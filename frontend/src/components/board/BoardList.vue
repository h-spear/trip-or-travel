<script setup>
import { ref, onMounted, inject } from 'vue'
import VSelect from '@/components/common/VSelect.vue'
import BoardListItem from '@/components/board/item/BoardListItem.vue'
import PageNavigation from '@/components/common/PageNavigation.vue'
import router from '@/router'
import { listBoard } from '@/api/board.js'

const selectOption = ref([
  { text: '검색조건', value: '' },
  { text: '글번호', value: 'article_no' },
  { text: '제목', value: 'subject' },
  { text: '작성자아이디', value: 'user_id' }
])
const articles = ref([])

const currentPage = ref(7)
const totalPage = ref(35)
const param = ref({
  pgno: currentPage.value,
  spp: import.meta.env.VITE_ARTICLE_LIST_SIZE,
  key: '',
  word: ''
})

onMounted(() => {
  getArticleList()
})

const changeKey = (val) => {
  console.log('BoarList에서 선택한 조건 : ' + val)
  param.value.key = val
}

const getArticleList = () => {
  listBoard(
    ({ data }) => {
      console.log('get article list : ', data)
      articles.value = data
      currentPage.value = 1
      totalPage.value = 5
    },
    (error) => {
      console.log('error : ', error)
    }
  )
}

const onPageChange = (val) => {
  console.log(val + '번 페이지로 이동 준비 끝!!!')
  param.value.pgno = val
  getArticleList()
}

const moveWrite = () => {
  router.push('write')
  // router.push({
  //   name: "write",
  //   params: {
  //     board: board,
  //   },
  // });
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in articles"
              :key="article.articleNo"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></PageNavigation>
    </div>
  </div>
</template>

<style scoped></style>
