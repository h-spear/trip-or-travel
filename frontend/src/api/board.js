import { localAxios } from '../utils/http-commons'

const local = localAxios()


// 현재의 api는 오직 자유게시판을 목적으로 보내는 중이다. 
function listBoard(success, fail) {
  // http://localhost:8080/post?boardId=3 get
  local.get('post?boardId=3').then(success).catch(fail)
}
function detailBoard(postId, success, fail) {
  local.get(`post/${postId}`).then(success).catch(fail)
}
function registBoard(post, success, fail) {
  console.log('in regist', post)
  const post1 = {
    boardId:post.boardId,
    title:post.title,
    content:post.content,
  }
  console.log('in regist', post1)
  local.post('post', post1).then(success).catch(fail)
  // local.post('post', JSON.stringify(post)).then(success).catch(fail)
}
function updateBoard(board, success, fail) {
  local.put('board', JSON.stringify(board)).then(success).catch(fail)
}
function removeBoard(articleNo, success, fail) {
  local.delete(`board/${articleNo}`).then(success).catch(fail)
}

export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
