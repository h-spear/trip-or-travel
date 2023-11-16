// import { ref, computed } from 'vue'
// import { defineStore } from 'pinia'
// import { listComment, registComment, updateComment } from '@/api/comment.js'
// import { useRouter } from 'vue-router'

// export const commentStore = defineStore(
//   'commentList',
//   () => {
//     //state
//     //한 게시글의 댓글을 전부 관리하기 위한 데이터
//     // 게시글 번호와 댓글리스트를 보유
//     // 게시글 디테일에 들어갈 때 해당 state들을 반영
//     // 댓글 관련 요청 사항들은 그때그때 받는 방식으로 진행
//     const postId = ref(1000000)
//     const commentList = ref([])

//     function FuncgetCommentList(postId, comments) {
//       console.log('curr', postId)
//       listComment(
//         postId,
//         ({ data }) => {
//           console.log('get comment list : ', data.data)
//           comments.value = data.data
//           console.log('updated weell?', commentList.value)
//         },
//         (error) => {
//           console.log('error : ', error)
//         }
//       )
//     }

//     function FuncwriteComment(data) {
//       if (data.comment == '') {
//         alert('내용을 적어주세요')
//       } else {
//         registComment(
//           postId.value,
//           data,
//           ({ data }) => {
//             console.log('registed ', data.data)
//             FuncgetCommentList(postId.value)
//           },
//           (error) => {
//             console.log('error ', error)
//           }
//         )
//       }
//     }

//     // function FuncupdateComment(data) {
//     //   updateComment(
//     //     data,
//     //     ({ data }) => {
//     //       console.log('updated comment : ', data)
//     //       FuncgetCommentList(postId.value)
//     //     },
//     //     (error) => {
//     //       console.log('error : ', error)
//     //     }
//     //   )
//     // }

//     return { postId, commentList, FuncgetCommentList, FuncwriteComment }
//   },
//   {
//     persist: true
//   }
// )
