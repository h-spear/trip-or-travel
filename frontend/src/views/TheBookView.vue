<script setup>
import { ref, provide, watch } from 'vue';

//목록을 표시할 books를 반응형으로 선언 
const books = ref([])
let bookSt = localStorage.getItem('books');
if (bookSt) {
  books.value = JSON.parse(bookSt).books
} 
provide('books', books)
watch(books, updateBooks, { deep: true })
function updateBooks() { 
  console.log('watch...............updateBooks : ', books.value)
  localStorage.setItem('books', JSON.stringify({books: books.value}))
}
</script>
<template>
    <div class="text-center m-4">
      <div class="alert alert-info" role="alert">도서 정보</div>
      <router-view></router-view>
    </div>
</template>
<style scoped>
</style>
