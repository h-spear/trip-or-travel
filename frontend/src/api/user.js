// needtobe update

import { localAxios } from '../utils/http-commons'

const local = localAxios()

function loginUser(email, password, success, fail) {
  const loginInfo = {
    email: email,
    password: password
  }
  console.log('local', local)
  // json으로 변환해서 보낼 생각하고 있자
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
