import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { listComment, registComment, updateComment } from '@/api/comment.js'
import { useRouter } from 'vue-router'

export const commentStore = defineStore(
  'commentList',
  () => {
    //state
    const postId = ref(0)
    const commentList = ref([])

    function FuncgetCommentList() {
      listComment(
        postId.value,
        ({ data }) => {
          console.log('get comment list : ', data.data)
          commentList.value = data.data
        },
        (error) => {
          console.log('error : ', error)
        }
      )
    }

    function FuncwriteComment(data) {
      if (data.comment == '') {
        alert('내용을 적어주세요')
      } else {
        registComment(
          postId.value,
          data,
          ({ data }) => {
            console.log('registed ', data.data)
            FuncgetCommentList(postId.value)
          },
          (error) => {
            console.log('error ', error)
          }
        )
      }
    }

    // function FuncupdateComment(data) {
    //   updateComment(
    //     data,
    //     ({ data }) => {
    //       console.log('updated comment : ', data)
    //       FuncgetCommentList(postId.value)
    //     },
    //     (error) => {
    //       console.log('error : ', error)
    //     }
    //   )
    // }

    return { postId, commentList, FuncgetCommentList, FuncwriteComment }
  },
  {
    persist: true
  }
)
