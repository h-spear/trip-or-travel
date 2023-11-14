// needtobe update

import { localAxios } from '../utils/http-commons'

const local = localAxios()

function loginUser(email, password, success, fail) {
  const loginInfo = {
    email: email,
    password: password
  }
  local.post('login', loginInfo).then(success).catch(fail)
}
function getSimpleInfo(success, fail) {
  local.get('member/simple').then(success).catch(fail)
}
function logoutUser(success, fail) {
  local.post('logout').then(success).catch(fail)
}
// function listBoard(success, fail) {
//   local.get('board').then(success).catch(fail)
// }
// function registBoard(board, success, fail) {
//   local.post('board', JSON.stringify(board)).then(success).catch(fail)
// }
// function detailBoard(articleNo, success, fail) {
//   local.get(`board/${articleNo}`).then(success).catch(fail)
// }
// function updateBoard(board, success, fail) {
//   local.put('board', JSON.stringify(board)).then(success).catch(fail)
// }
// function removeBoard(articleNo, success, fail) {
//   local.delete(`board/${articleNo}`).then(success).catch(fail)
// }

export { loginUser, getSimpleInfo, logoutUser }
// export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
