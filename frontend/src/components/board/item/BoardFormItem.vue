<script setup>
import router from '@/router';
import { ref, watch } from 'vue';
import { registBoard, updateBoard } from '@/api/board';
import { useRoute } from 'vue-router';

const props = defineProps({ type: String });
const isUseId = ref(false);

const route = useRoute();
const post = ref({
  boardId: Number(route.query.boardId),
  title: '',
  content: ''
});
console.log(route.query.boardId, '11111111S');

if (props.type === 'modify') {
  post.value = history.state.post;
  isUseId.value = true;
}

function onSubmit() {
  // event.preventDefault();
  console.log(post.value);
  if (post.value.title == '' || post.value.title.length > 30) {
    alert('제목을 확인해주세요');
  } else if (post.value.content == '' || post.value.content.length > 500) {
    alert('내용을 확인해주세요');
  } else {
    props.type === 'regist' ? writePost() : updatePost();
  }
}

function writePost() {
  console.log('write', post);
  registBoard(
    post.value,
    ({ data }) => {
      console.log('regist postId : ', data.data);
      post.value.postId = data.data;
      moveDetail();
    },
    (error) => {
      console.log('error : ', error);
    }
  );
}

function updatePost() {
  console.log('update', post);
  const updatePost = {
    title: post.value.title,
    content: post.value.content
  };
  updateBoard(
    post.value.postId,
    updatePost,
    ({ data }) => {
      console.log('update complete', data.data);
      moveDetail();
    },
    (error) => {
      console.log('error: ', error);
    }
  );
}

function moveDetail() {
  console.log('move to :', post.value.postId);
  router.push({
    name: 'board-detail',
    query: {
      boardId: post.value.boardId,
      postId: post.value.postId
    }
  });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div>
      <label for="title">제목</label>
      <a-input v-model:value="post.title" placeholder="제목을 입력하세요" />
    </div>
    <div style="margin-top: 30px">
      <label for="content">내용</label>
      <a-textarea v-model:value="post.content" rows="20" show-count :maxlength="1000" />
    </div>
    <div style="display: flex; width: 100%; justify-content: end; margin: 60px 0 10px 0">
      <a-button
        class="post-btn"
        type="primary"
        @click="updatePost"
        v-if="props.type == 'modify'"
        style="background-color: rgb(0, 177, 0)"
        >수정</a-button
      >
      <a-button class="post-btn" type="primary" @click="writePost" v-if="props.type == 'regist'"
        >작성</a-button
      >
      <a-button class="post-btn" type="primary" @click="moveDetail" danger>취소</a-button>
    </div>
  </form>
</template>

<style scoped>
label {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 6px;
}
.post-btn {
  margin: 0 5px;
}
</style>
