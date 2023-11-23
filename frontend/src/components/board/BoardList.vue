<script setup>
import { useRoute } from 'vue-router';
import { ref, onMounted, onUpdated, watch, computed } from 'vue';
import BoardListItem from '@/components/board/item/BoardListItem.vue';
import router from '@/router';
import { listBoard } from '@/api/board.js';

const route = useRoute();
const posts = ref([]);

const boardId = ref(route.query.boardId);
const size = ref(10);
const page = ref(1);
const total = ref(0);
const title = ref('');
const writerNickname = ref('');
const startDate = ref('');
const endDate = ref('');
const order = ref('');

watch([size, page], () => {
  getPosts();
});

watch(boardId, () => {
  getPosts();
});

onUpdated(() => {
  boardId.value = route.query.boardId;
}),
  onMounted(() => {
    getPosts();
  });

const getPosts = () => {
  const searchParams = {
    boardId: boardId.value,
    size: size.value,
    page: page.value - 1,
    title: title.value,
    writerNickname: writerNickname.value,
    startDate: startDate.value,
    endDate: endDate.value,
    order: order.value
  };
  console.log('searchParams=', searchParams);
  listBoard(
    searchParams,
    ({ data }) => {
      if (data.status === 'success') {
        // const pageable = data.data.pageable;
        // console.log('content', data.data);
        // console.log('pageable=', pageable);
        // console.log('total=', data.data.totalElements);
        total.value = data.data.totalElements;
        posts.value = data.data.content;
      }
    },
    (error) => {
      console.log('error : ', error);
    }
  );
};
</script>

<template>
  <div class="wrapper">
    <table class="table table-hover">
      <thead>
        <tr class="text-center" style="font-weight: 700">
          <th scope="col" style="width: 10%">번호</th>
          <th scope="col" style="width: 50%">제목</th>
          <th scope="col" style="width: 10%">작성자</th>
          <th scope="col" style="width: 10%">추천</th>
          <th scope="col" style="width: 10%">조회</th>
          <th scope="col" style="width: 10%">작성일</th>
        </tr>
      </thead>
      <tbody>
        <BoardListItem v-for="post in posts" :key="post.postId" :post="post"> </BoardListItem>
      </tbody>
    </table>
    <div class="pagination-box">
      <div style="width: 80px"></div>
      <a-pagination
        size="large"
        v-model:current="page"
        v-model:pageSize="size"
        :total="total"
        class="pagination"
      />
      <a-select
        ref="select"
        v-model:value="size"
        style="width: 80px"
        size="large"
        class="pagination-select"
      >
        <a-select-option value="5">5</a-select-option>
        <a-select-option value="10">10</a-select-option>
        <a-select-option value="20">20</a-select-option>
        <a-select-option value="50">50</a-select-option>
        <a-select-option value="100">100</a-select-option>
      </a-select>
    </div>
  </div>
</template>

<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

.pagination-box {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  position: relative;
}

.pagination {
  display: flex;
  justify-content: center;
  transition: 0.5s;
}
</style>
