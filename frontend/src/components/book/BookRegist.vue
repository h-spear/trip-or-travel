<script setup>
import { ref, inject } from 'vue'
import router from '@/router'

//v-model과 연결한 반응형 변수 선언
const isbn = ref('')
const title = ref('')
const author = ref('')
const price = ref('')
const describ = ref('')

/*
Dom과 연결할 반응형 변수 선언, setup에서는 DOM tree가 구성되지 않았기 때문에 
mounted 이후에 접근이 가능하다. 
*/
const isbnDiv = ref(null)
const titleDiv = ref(null)
const authoDivr = ref(null)

const books = inject('books')
console.log('BookRegist....books:', books.value)

function moveHandler() {
  router.push({ name: 'book-list' })
}

function createHandler() {
  //사용자 입력 값을 체크하기
  let err = false
  let msg = ''

  // if (!isbn.value) {
  //   msg = '책 일련 번호를 입력해 주세요'
  //   err = false;
  //   isbnDiv.value.focus()
  // } else if (err && !title.value) { }
  !isbn.value && ((msg = '책 일련 번호를 입력해 주세요'), (err = true), isbnDiv.value.focus())
  !err && !title.value && ((msg = '제목을 입력해 주세요'), (err = true), titleDiv.value.focus())
  !err && !author.value && ((msg = '저자를 입력해 주세요'), (err = true), authorDiv.value.focus())

  if (err) {
    alert(msg)
  } else {
    const book = {
      isbn: isbn.value,
      title: title.value,
      author: author.value,
      price: price.value,
      describ: describ.value
    }
    books.value.push(book)
    console.log('bookregist.......', books.value)

    title.value = ''
    isbn.value = ''
    author.value = ''
    price.value = ''
    describ.value = ''
    alert('등록')
    moveHandler()
  }
}
</script>

<template>
  <div class="me-4">
    <h1 class="underline text-center">도서 등록</h1>
    <table class="table table-boardered">
      <thead>
        <tr>
          <td>책 일련 번호</td>
          <td><input type="text" ref="isbnDiv" v-model="isbn" /></td>
        </tr>
        <tr>
          <td>제목</td>
          <td><input type="text" ref="titleDiv" v-model="title" /></td>
        </tr>
        <tr>
          <td>저자</td>
          <td><input type="text" ref="authorDiv" v-model="author" /></td>
        </tr>
        <tr>
          <td>가격</td>
          <td><input type="text" v-model="price" /></td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td colspan="2">책 정보</td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
            <textarea v-model="describ" cols="45" rows="10" />
          </td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
            <button class="btn btn-primary m-1" @click="createHandler">등록</button>
            <button class="btn btn-primary m-1" @click="moveHandler">목록</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
