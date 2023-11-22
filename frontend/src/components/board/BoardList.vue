<script setup>
import { ref, onMounted, inject } from 'vue';
import VSelect from '@/components/common/VSelect.vue';
import BoardListItem from '@/components/board/item/BoardListItem.vue';
import PageNavigation from '@/components/common/PageNavigation.vue';
import router from '@/router';
import { listBoard } from '@/api/board.js';

const selectOption = ref([
  { text: '검색조건', value: '' },
  { text: '글번호', value: 'article_no' },
  { text: '제목', value: 'subject' },
  { text: '작성자아이디', value: 'user_id' }
]);
const posts = ref([]);

const currentPage = ref(2);
const totalPage = ref(3);
const param = ref({
  pgno: currentPage.value,
  spp: import.meta.env.VITE_ARTICLE_LIST_SIZE,
  key: '',
  word: ''
});

onMounted(() => {
  getPosts();
});

const changeKey = (val) => {
  console.log('BoarList에서 선택한 조건 : ' + val);
  param.value.key = val;
};

const getPosts = () => {
  const searchParams = {
    boardId: 1,
    size: 2
  };

  listBoard(
    searchParams,
    ({ data }) => {
      console.log(data);
      console.log('get article list : ', data.data);
      posts.value = data.data.content;
      currentPage.value = 1;
      totalPage.value = 5;
    },
    (error) => {
      console.log('error : ', error);
    }
  );
};

const moveWrite = () => {
  router.push('write');
};
</script>

<template>
  <div class="wrapper">
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">글번호</th>
          <th scope="col">좋아요</th>
          <th scope="col">제목(댓글)</th>
          <th scope="col">작성자</th>
          <th scope="col">조회수</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>
      <tbody>
        <BoardListItem v-for="post in posts" :key="post.postId" :post="post"></BoardListItem>
      </tbody>
    </table>
    <a-pagination v-model:currentPage="currentPage" :total="12" />
  </div>
</template>

<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
}
</style>
