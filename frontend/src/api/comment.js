import { localAxios } from '../utils/http-commons'

const local = localAxios()

function listComment(articleNo,success, fail) {
  local.get(`comment/${articleNo}`).then(success).catch(fail)
}
function detailBoard(articleNo, success, fail) {
  local.get(`board/${articleNo}`).then(success).catch(fail)
}
function registComment(comment, success, fail) {
  local.post('comment', JSON.stringify(comment)).then(success).catch(fail)
}
function updateComment(comment, success, fail) {
  local.put('comment', JSON.stringify(comment)).then(success).catch(fail)
}
function removeComment(commentId, success, fail) {
  local.delete(`comment/${commentId}`).then(success).catch(fail)
}

export { registComment, listComment, updateComment, removeComment}
// export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
