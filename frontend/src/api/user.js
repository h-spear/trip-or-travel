import { localAxios } from '../utils/http-commons'

const local = localAxios()

function loginUser(email, password, success, fail) {
  const loginInfo = { email, password }
  local.post('login', loginInfo).then(success).catch(fail)
}
function getSimpleInfo(success, fail) {
  local.get('member/simple').then(success).catch(fail)
}
function logoutUser(success, fail) {
  local.post('logout').then(success).catch(fail)
}
function emailDupCheck(email, success, fail) {
  local.get(`member/email`, { params: { email } }).then(success).catch(fail)
}
function nicknameDupCheck(nickname, success, fail) {
  local.get(`member/nickname`, { params: { nickname } }).then(success).catch(fail)
}
function registUser(user, success, fail) {
  local.post('member/join', user).then(success).catch(fail)
}
export { loginUser, getSimpleInfo, logoutUser, emailDupCheck, nicknameDupCheck, registUser }
