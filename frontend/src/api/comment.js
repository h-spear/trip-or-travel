import { localAxios } from '../utils/http-commons'

const local = localAxios()

function listComment(postId, success, fail) {
  local.get(`post/${postId}/comment`).then(success).catch(fail)
}
function registComment(postId, comment, success, fail) {
  local.post(`post/${postId}/comment`, JSON.stringify(comment)).then(success).catch(fail)
}
function updateComment(postId, commentId, comment, success, fail) {
  local
    .put(`post/${postId}/comment/${commentId}`, JSON.stringify(comment))
    .then(success)
    .catch(fail)
}
function removeComment(postId, commentId, success, fail) {
  local.delete(`post/${postId}/comment/${commentId}`).then(success).catch(fail)
}

export { registComment, listComment, updateComment, removeComment }
// export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
