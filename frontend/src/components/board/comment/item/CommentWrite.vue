<script setup>
import { ref } from "vue";
import { registComment, listComment } from "@/api/comment";
import { useRoute } from "vue-router";

const route = useRoute()

const props = defineProps({
    comments: Object
})

const comment = ref({
    articleNo: 0,
    content: '',
    userId: ''
})
const userId = 'ssafy' // 댓글 다는 유저는 잠시 고정
comment.value.userId = userId
// 토큰이나 provide로 뿌려야 할 듯
// comment.value.articleNo = props.articleNo
comment.value.articleNo = route.params.articleNo


const emits = defineEmits(['registingComment'])
function writeComment(){
    emits('registingComment', comment.value)
    comment.value.content = ''
}
</script>

<template>
    <!-- <form > -->
        <form @submit.prevent="writeComment">
            <label for="content" class="d-md-flex justify-content-md-start">댓글 달기</label>
            <textarea type="text" id="content" class=" container" placeholder="댓글을 적어주세요" v-model="comment.content"></textarea>
            <div  class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="submit" class="btn btn-success" value="">등록</button>
            </div>
        </form>
    </template>

<style scoped>

</style>