import { localAxios } from '../utils/http-commons';

const local = localAxios();

// 현재의 api는 오직 자유게시판(3)을 목적으로 보내는 중이다.
function listBoard(params, success, fail) {
  let url = `post?board=`;
  for (let key in params) {
    url += `&${key}=${params[key]}`;
  }
  console.log(url);
  local.get(url).then(success).catch(fail);
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

export { listBoard, detailBoard, registBoard, updateBoard, removeBoard };
