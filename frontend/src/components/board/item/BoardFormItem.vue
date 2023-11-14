<script setup>
import router from "@/router";
import { ref, watch } from "vue";
import {registBoard, updateBoard} from "@/api/board"

const props = defineProps({ type: String });
const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerDate: "",
});

if (props.type === "modify") {
  article.value = history.state.article
  isUseId.value = true;
}

function onSubmit() {
  // event.preventDefault();
  if (article.value.subject == '' || article.value.subject.length > 30){
    alert('제목을 확인해주세요')
  } else if(article.value.content == '' || article.value.content.length > 500){
    alert('내용을 확인해주세요')
  }else{
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}


function writeArticle() {
  if (!article.value.userId){
    article.value.userId = '기본자'
  } // 실제로 진행 시 없애야 할 코드. 유저는 따로 받아야 한다
  registBoard(
    article.value,
    ({ data }) => {
      console.log('regist articleNo : ', data)
      article.value.articleNo = data
      moveDetail()
    },
    (error) => {
      console.log('error : ', error)
    }
  )

}

function updateArticle() {
updateBoard(
  article.value,
  (data)=>{
    console.log('update complete', data)
    moveDetail()
  },
  (error)=>{
    console.log('error: ', error)
  }
)
}

function moveDetail(){
  console.log('move to :', article.value.articleNo)
  router.push({
    name:'board-detail',
    params:{
      articleNo: article.value.articleNo
    }
  })
}

function moveList() {
  router.push({name: 'board-list'});
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.userId"
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        placeholder="제목..."
      />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea
        class="form-control"
        v-model="article.content"
        rows="10"
      ></textarea>
    </div>
    <div class="col-auto text-center">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3"
        v-if="type === 'regist'"
      >
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>
        글수정
      </button>
      <button
        type="button"
        class="btn btn-outline-danger mb-3 ms-1"
        @click="moveList"
      >
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
