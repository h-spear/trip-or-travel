<script setup>
import { ref, inject } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { detailBook, updateBook, removeBook } from '@/api/books'

// router로 이동시 전달한 데이터를 받기 위해
const route = useRoute()
// router로 화면 이동하기 위해
const router = useRouter()

// :to={name:'', qeury:{}}로 전달한 데이터를 받기 위해
const isbn = ref(route.query.isbn)

// dom이 mounted된 상태에서 아직 서버에서 데이터가 안 온 경우
// book의 데이터를 화면에 표시할 수 없어서 에러가 발생하므로 초기 값을 설정해준다.
const book = ref({
  isbn: '',
  title: '',
  author: '',
  price: '',
  describ: ''
})
console.log('bookdetail.setup....')
detailBook(
  isbn.value,
  ({ data }) => {
    console.log('bookdetail.setup...', data)
    book.value = data
  },
  (error) => {
    console.log('error', error)
  }
)

//초기에는 Detail화면 표시 하기 위해 readonly='readonly' 로 표시
//수정일 경우 readonly='' 표시하기 위해 isReadonly=false로
const isReadonly = ref(true)

function removeHandler() {
  console.log('BookDetail.remove.......................')
  removeBook(
    isbn.value,
    ({ data }) => {
      console.log('bookdetail.remvoe', data)
      alert('delete')
      moveHandler()
    },
    (error) => {
      console.log(error)
      alert('fail to delete')
    }
  )
}

function moveHandler() {
  router.push({ name: 'book-list' })
}

function updateHandler() {
  //books에 있는 book객체를 props로 전달 받았고 props도 반응형이라서
  //v-model로 props를 데이타를 변경하면 books에 바로 반영된다.
  //그래서 화면 전환만 하면된다

  if (!isReadonly.value) {
    // update 화면에서 데엍 수정 후 update ㅇ청
    updateBook(
      book.value,
      ({ data }) => {
        console.log('bookdetail.update', data)
        alert('수정됨')
      },
      (error) => {
        console.log('error, update failed', error)
      }
    )
  }
  isReadonly.value = !isReadonly.value
}
</script>

<template>
  <div>
    <table class="table table-bordered">
      <tbody>
        <tr>
          <th>책 일련번호</th>
          <td><input type="text" v-model.lazy="book.isbn" readonly="readonly" /></td>
        </tr>
        <tr>
          <th>제목</th>
          <td><input type="text" v-model.lazy="book.title" :readonly="isReadonly" /></td>
        </tr>
        <tr>
          <th>저자</th>
          <td><input type="text" v-model.lazy="book.author" :readonly="isReadonly" /></td>
        </tr>
        <tr>
          <th>가격</th>
          <td><input type="text" v-model.lazy="book.price" :readonly="isReadonly" /></td>
        </tr>
        <tr>
          <th colspan="2">책 정보</th>
        </tr>
        <tr>
          <td colspan="2">
            <textarea
              cols="45"
              rows="10"
              v-model.lazy="book.describ"
              :readonly="isReadonly"
            ></textarea>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
            <button class="btn btn-outline-primary m-1" @click="updateHandler">수정</button>
            <button class="btn btn-outline-primary m-1" @click="moveHandler">목록</button>
            <button class="btn btn-outline-primary m-1" @click="removeHandler">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
input:read-only {
  background-color: lightgray;
}
</style>
