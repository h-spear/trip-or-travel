<script setup>
import { ref } from 'vue';
import router from '@/router';

const props = defineProps({ post: Object });

async function moveDetail() {
  // 라우터와 피니아가 헷갈린다. 나중에 리팩토링 필수
  // postId.value = props.post.postId
  // await FuncgetCommentList()
  router.push({
    name: 'board-detail',
    params: {
      postId: props.post.postId
    }
  });
}

const convertDate = (dateTime) => {
  const splited = dateTime.split('T');
  const date = splited[0];
  const time = splited[1];
  const dateArr = date.split('-').map((x) => x * 1);
  const todayDateTime = new Date();
  const todayArr = [
    todayDateTime.getFullYear(),
    todayDateTime.getMonth() + 1,
    todayDateTime.getDate()
  ];
  for (let i = 0; i < 3; ++i) {
    if (todayArr[i] !== dateArr[i]) {
      return `${dateArr[0] % 100}/${dateArr[1]}/${dateArr[2]}`;
    }
  }
  return time;
};
</script>

<template>
  <tr class="text-center" @click="moveDetail">
    <th scope="row">{{ post.postId }}</th>
    <td class="text-start">
      {{ post.title }}<b> [{{ post.commentCount }}]</b>
    </td>
    <td>{{ post.writerNickname }}</td>
    <td>{{ post.likes }}</td>
    <td>{{ post.views }}</td>
    <td>{{ convertDate(post.registrationDate) }}</td>
  </tr>
</template>

<style scoped>
tr {
  cursor: pointer;
}
a {
  text-decoration: none;
}
</style>
