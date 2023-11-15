// needtobe update

import { localAxios } from '../utils/http-commons'

const local = localAxios()

function getSido(success, fail) {
  local.get(`region/sido`).then(success).catch(fail)
}
function getGugun(id, success, fail) {
  local.get(`region/sido/${id}`).then(success).catch(fail)
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

export { getSido, getGugun }
