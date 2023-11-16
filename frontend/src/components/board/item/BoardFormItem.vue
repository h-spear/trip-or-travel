<script setup>
import router from "@/router";
import { ref, watch } from "vue";
import {registBoard, updateBoard} from "@/api/board"

const props = defineProps({ type: String });
const isUseId = ref(false);

const post = ref({
  boardId:3,
  title: "",
  content: "",
});

if (props.type === "modify") {
  post.value = history.state.post
  isUseId.value = true;
}

function onSubmit() {
  // event.preventDefault();
  console.log(post.value)
  if (post.value.title == '' || post.value.title.length > 30){
    alert('제목을 확인해주세요')
  } else if(post.value.content == '' || post.value.content.length > 500){
    alert('내용을 확인해주세요')
  }else{
    props.type === "regist" ? writePost() : updatePost();
  }
}


function writePost() {
  console.log(post)
  registBoard(
    post.value,
    ({ data }) => {
      console.log('regist postId : ', data)
      post.value.postId = data
      moveDetail()
    },
    (error) => {
      console.log('error : ', error)
    }
  )

}

function updatePost() {
updateBoard(
  post.value,
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
  console.log('move to :', post.value.postId)
  router.push({
    name:'board-detail',
    params:{
      postId: post.value.postId
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
      <label for="title" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="post.title"
        placeholder="제목..."
      />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea
        class="form-control"
        v-model="post.content"
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
