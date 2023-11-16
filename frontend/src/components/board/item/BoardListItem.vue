<script setup>
import { ref } from 'vue'
import router from '@/router'
const props = defineProps({ post: Object })
console.log(props.post)

function moveDetail() {
  console.log(props.post.postId)
  router.push({
    name: 'board-detail',
    params: {
      postId: props.post.postId
    }
  })
}
</script>

<template>
  <tr class="text-center" @click="moveDetail">
    <th scope="row">{{ post.postId }}</th>
    <td>{{ post.likes }}</td>
    <td class="text-start click">
      {{ post.title }}<b>({{ post.commentCount }})</b>
    </td>
    <td><img :post="post.writerProfileImageUrl" alt="" />{{ post.writerNickname }}</td>
    <td>{{ post.views }}</td>
    <td>{{ post.registrationDate }}</td>
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
