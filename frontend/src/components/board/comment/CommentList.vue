<script setup>
import CommentWrite from "./item/CommentWrite.vue";
import CommentListItem from "./item/CommentListItem.vue";
import {ref, onMounted} from 'vue';
import {listComment, registComment} from '@/api/comment.js'
import { useRoute } from "vue-router";
import { updateComment, removeComment } from "../../../api/comment";

const route = useRoute()
const comments = ref([])

onMounted(()=>{
    getCommentList()
})

const getCommentList = ()=>{
    listComment(
        route.params.articleNo,
        ({ data }) => {
            console.log('get comment list : ', data)
            comments.value = data
        },
        (error) => {
            console.log('error : ', error)
        }
        )
        
    }    


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

function writeComment (data){
    console.log(data.content)
    comment.value = data
    if (comment.value.content==''){
        alert('내용을 적어주세요')
    }else{
        registComment(
            comment.value,
            (data)=>{
               console.log('registed ', data) 
            //효율적인 업데이트를 위해 다시 디비 조회..
                getCommentList()
            },
            (error)=>{
               console.log('error ', error) 
            }
        )
    }
}

function onUpdateComment(data){
    updateComment(
        data, 
        ({ data }) => {
            console.log('updated comment : ', data)
            getCommentList()
        },
        (error) => {
            console.log('error : ', error)
        }
    )
}

function onDeleteComment(data){
    removeComment(
        data, 
        ({ data }) => {
            console.log('deleted comment : ', data)
            getCommentList()
        },
        (error) => {
            console.log('error : ', error)
        }
    )
}

</script>

<template>
    <div class="container">
        <ol class="list-group list-group-numbered">
                <CommentListItem
                class=""
                v-for="comment in comments"
                :key="comment.commentId"
                :comment="comment"
                @updating-comment="onUpdateComment"
                @deleting-comment="onDeleteComment"
                >
            </CommentListItem>
        </ol>
        <CommentWrite :comments='comments'  @registing-comment="writeComment"></CommentWrite>
    </div>
</template>

<style scoped>

</style>