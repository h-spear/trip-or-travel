<script setup>
import { ref } from 'vue'
import router from '@/router'
const props = defineProps({ comment: Object })
const clicked = ref(true)
const children = ref(props.comment.children)
const content = ref(props.comment.comment)

const emits = defineEmits(['updatingComment', 'deletingComment', 'writingComment'])

function updateComment() {
  props.comment.comment = content.value
  clicked.value = !clicked.value
  if (clicked.value) {
    emits('updatingComment', props.comment)
  }
}

function deleteComment() {
  // 모달로 삭제할 것인지 물어보면 좋을 듯
  emits('deletingComment', props.comment.commentId)
}

// children: [],
//   childrenCount: 0,
//   comment: '',
//   commentId: 0,
//   commenterId: 0,
//   commenterNickname: '',
//   commenterProfileImageUrl: ''
</script>

<template>
  <div class="container list-group-item">
    <div class="row">
      <div class="col">
        <h4><img :src="commenterProfileImageUrl" />{{ comment.commenterNickname }}</h4>
      </div>
      <div class="col">
        {{ comment.registerTime }}
      </div>
      <textarea v-model="content" :readonly="clicked" class="" cols="30" rows="5"></textarea>
    </div>
    <div class="row">
      <!-- 해당 유저와 로그인 유저가 일치하는지 체크 필요 -->
      <button class="col" @click="updateComment">
        <div v-if="clicked">수정</div>
        <div v-else>확인</div>
      </button>
      <button class="col" @click="deleteComment">삭제</button>
      <button class="col" @click="writingComment">답글</button>
    </div>
    <template v-for="child in children" :key="child.commentId">
      <div class="container list-group-item">
        <div class="row">
          <div class="col">
            <h4><img :src="commenterProfileImageUrl" />{{ child.commenterNickname }}</h4>
          </div>
          <textarea
            :readonly="clicked"
            class=""
            cols="30"
            rows="5"
            v-model="child.comment"
          ></textarea>
        </div>
        <div class="row">
          <button class="col">
            <div v-if="clicked">수정</div>
            <div v-else>확인</div>
          </button>
          <button class="col">삭제</button>
          <button class="col">답글</button>
        </div>
      </div>
    </template>
  </div>
</template>

<style scoped></style>
