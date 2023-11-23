<script setup>
import CommentWrite from './item/CommentWrite.vue';
import CommentListItem from './item/CommentListItem.vue';
import { ref, onMounted, computed } from 'vue';
import { listComment, registComment } from '@/api/comment.js';
import { useRoute } from 'vue-router';
import { updateComment, removeComment } from '../../../api/comment';

import { getCurrentInstance } from 'vue';
const instance = getCurrentInstance();
const rerender = ref(0);

const route = useRoute();
const comments = ref([]);

const props = defineProps({ postId: Number });
const postId = props.postId;

const commentCount = computed(() => {
  let count = comments.value.length;
  for (let i = 0; i < comments.value.length; i++) {
    count += comments.value[i].children.length;
  }
  return count;
});

getCommentList(postId, comments);

const comment = ref({
  children: [],
  childrenCount: 0,
  comment: '',
  commentId: 0,
  commenterId: 0,
  commenterNickname: '',
  commenterProfileImageUrl: ''
});

function getCommentList(postId, comments) {
  listComment(
    postId,
    ({ data }) => {
      console.log('get comment list : ', data.data);
      comments.value = data.data;
      rerender.value++;
    },
    (error) => {
      console.log('error : ', error);
    }
  );
}

function writeComment(comment) {
  // 여기에 parentCommentId가 있으면 답글 방식
  console.log('list:write', comment);
  if (comment.content == '') {
    alert('내용을 적어주세요');
  } else {
    registComment(
      postId,
      comment,
      (data) => {
        console.log('registed ', data.data);
        //효율적인 업데이트를 위해 다시 디비 조회..
        getCommentList(postId, comments);
      },
      (error) => {
        console.log('error ', error);
      }
    );
  }
}

function onUpdateComment(data) {
  data = {
    ...data,
    postId: postId
  };
  console.log('in comment list', data);
  updateComment(
    data,
    ({ data }) => {
      console.log('updated comment : ', data);
      getCommentList(postId, comments);
    },
    (error) => {
      console.log('error : ', error);
    }
  );
}

function onDeleteComment(data) {
  console.log('comment list', data);
  data = {
    ...data,
    postId: postId
  };
  removeComment(
    data,
    ({ data }) => {
      console.log('deleted comment : ', data);
      getCommentList(postId, comments);
    },
    (error) => {
      console.log('error : ', error);
    }
  );
}
</script>

<!-- 
<div class="container">
  <div>{{ comments.length }}</div>
  <ol class="list-group list-group-numbered" :key="rerender">
    <CommentListItem
      class=""
      v-for="comment in comments"
      :key="comment.commentId"
      :comment="comment"
      :commentId="comment.commentId"
      @updating-comment="onUpdateComment"
      @deleting-comment="onDeleteComment"
      @registing-comment="writeComment"
    />
  </ol>
  <CommentWrite @registing-comment="writeComment"></CommentWrite>
</div> -->

<template>
  <div class="container">
    <p style="font-size: 16px">댓글 수 : {{ commentCount }}</p>
    <hr />
    <ol class="list-group list-group-numbered" :key="rerender">
      <CommentListItem
        class=""
        v-for="comment in comments"
        :key="comment.commentId"
        :comment="comment"
        :commentId="comment.commentId"
        @updating-comment="onUpdateComment"
        @deleting-comment="onDeleteComment"
        @registing-comment="writeComment"
      />
    </ol>
    <!-- <CommentWrite @registing-comment="writeComment"></CommentWrite> -->
  </div>
</template>

<style scoped></style>
