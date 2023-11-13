// needtobe update

import { localAxios } from '../utils/http-commons'

const local = localAxios()

function loginUser(email, password, success, fail) {
  const loginInfo = {
    email: email,
    password: password
  }
  console.log('to json :', JSON.stringify(loginInfo))
  local.post('login', loginInfo).then(success).catch(fail)
}
// function listBoard(success, fail) {
//   local.get('board').then(success).catch(fail)
// }
// function detailBoard(articleNo, success, fail) {
//   local.get(`board/${articleNo}`).then(success).catch(fail)
// }
// function registBoard(board, success, fail) {
//   local.post('board', JSON.stringify(board)).then(success).catch(fail)
// }
// function updateBoard(board, success, fail) {
//   local.put('board', JSON.stringify(board)).then(success).catch(fail)
// }
// function removeBoard(articleNo, success, fail) {
//   local.delete(`board/${articleNo}`).then(success).catch(fail)
// }

export { loginUser }
// export { listBoard, detailBoard, registBoard, updateBoard, removeBoard }
