import { localAxios } from '../utils/http-commons';

const local = localAxios();

function registPlan(plans, success, fail) {
  console.log('in api', plans);
  local.post(`plan`, JSON.stringify(plans)).then(success).catch(fail);
}

function listBoard(success, fail) {
  local.get('post?boardId=3').then(success).catch(fail);
}
function detailBoard(postId, success, fail) {
  local.get(`post/${postId}`).then(success).catch(fail);
}
function registBoard(post, success, fail) {
  local.post('post', JSON.stringify(post)).then(success).catch(fail);
}
function updateBoard(postId, post, success, fail) {
  local.put(`post/${postId}`, JSON.stringify(post)).then(success).catch(fail);
}
function removeBoard(postId, success, fail) {
  local.delete(`post/${postId}`).then(success).catch(fail);
}

// export { listBoard, detailBoard, registBoard, updateBoard, removeBoard };
export { registPlan };
