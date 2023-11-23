<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted, reactive, onUpdated } from 'vue';
import { detailBoard, removeBoard } from '../../api/board';
import CommentList from './comment/CommentList.vue';
import { loginStore } from '@/stores/LoginStore.js';
const loginstore = loginStore();
const { userId } = loginstore;

const route = useRoute();
const router = useRouter();
const boardId = Number(route.query.boardId);
const postId = Number(route.query.postId);
const modalOpen = ref(false);
const showModal = () => {
  modalOpen.value = true;
};

console.log(boardId, postId);

const post = ref({
  postId: 0,
  title: 0,
  registrationDate: '',
  views: 0,
  content: 0,
  commentCount: 0,
  likes: 0
});

const getPost = () => {
  detailBoard(
    postId,
    ({ data }) => {
      console.log('get detail data : ', data.data);
      post.value = data.data;
      console.log('detail', post.value);
    },
    (error) => {
      console.log('get post error : ', error);
    }
  );
};

getPost();

function moveList() {
  router.push({ name: 'board', query: { boardId } });
}

function moveModify() {
  router.push({
    name: 'board-modify',
    state: {
      post: { ...post.value }
    }
  });
}

const onDeleteArticle = () => {
  removeBoard(
    postId,
    (data) => {
      console.log('delete complete', data);
      moveList();
      // 댓글도 전부 지우는 작업이 함께 들어가야만 한다.
      // deleteCommentAll 필요
    },
    (error) => {
      console.log('error : ', error);
    }
  );
};

const handleDeleteOk = (e) => {
  onDeleteArticle();
  modalOpen.value = false;
};
</script>

<template>
  <div class="container">
    <div class="content">
      <div class="info-box">
        <h3>
          <b>{{ post.postId }}. {{ post.title }}</b>
        </h3>
        <span>
          작성일 :
          {{ post.registrationDate }}
          / 조회 : {{ post.views }}
        </span>
      </div>
      <div class="avatar-box">
        <a-avatar :size="48" :src="post.writerProfileImageUrl" alt="ProfileImage">
          <template #icon><UserOutlined /></template>
        </a-avatar>
        <span style="margin-left: 10px; font-size: 18px">{{ post.writerNickname }}</span>
      </div>
    </div>
    <p style="margin: 40px 0 70px 0">
      {{ post.content }}
    </p>
  </div>
  <div class="button-box">
    <a-button class="post-btn" type="primary" @click="moveList">목록</a-button>
    <template v-if="post.writerId == userId">
      <a-button
        class="post-btn"
        type="primary"
        @click="moveModify"
        style="background-color: rgb(0, 177, 0)"
        >수정</a-button
      >
      <a-button class="post-btn" type="primary" @click="showModal" danger>삭제</a-button>
    </template>
  </div>
  <CommentList :postId="postId"></CommentList>
  <a-modal
    v-model:open="modalOpen"
    title="게시글을 삭제하시겠습니까?"
    @ok="handleDeleteOk"
    okText="삭제"
    cancelText="취소"
    width="400px"
    style="margin-top: 200px"
  >
    <p style="margin-top: 20px; margin-bottom: 40px">게시글을 삭제하면 복구할 수 없습니다.</p>
  </a-modal>
</template>

<style scoped>
.button-box {
  width: 100%;
  margin-right: 100px;
  transform: translateY(40px);
  text-align: right;
  margin-bottom: 10px;
}
.container {
  padding: 0 20px;
}
.post-wrapper {
  padding: 0 10px;
}

.post-btn {
  margin-left: 10px;
}
.content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.info-box {
  font-size: 14px;
}
</style>
