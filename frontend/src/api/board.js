import { localAxios } from '../utils/http-commons'

const local = localAxios()

function listBoard(success, fail) {
  // http://localhost:8080/post?boardId=3 get
  local.get('post?boardId=3').then(success).catch(fail)
}
function detailBoard(postId, success, fail) {
  local.get(`post/${postId}`).then(success).catch(fail)
}
function registBoard(board, success, fail) {
  local.post('board', JSON.stringify(board)).then(success).catch(fail)
}
function updateBoard(board, success, fail) {
  local.put('board', JSON.stringify(board)).then(success).catch(fail)
}
function removeBoard(articleNo, success, fail) {
  local.delete(`board/${articleNo}`).then(success).catch(fail)
}

export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
