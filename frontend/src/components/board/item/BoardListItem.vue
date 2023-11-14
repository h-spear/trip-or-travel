<script setup>
import {ref} from 'vue'
import router from "@/router";
const props = defineProps({ article: Object })

function moveDetail(){
  router.push({
    name:'board-detail',
    params:{
      articleNo:props.article.articleNo
    }
  })
}
</script>

<template>
  <tr class="text-center" @click="moveDetail">
    <th scope="row">{{ article.articleNo }}</th>
    <td class="text-start click" >
        {{ article.subject }}
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime }}</td>
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
