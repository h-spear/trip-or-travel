import { localAxios } from '../utils/http-commons';

const local = localAxios();

function getAttrByType(data, success, fail) {
  local.get(`attraction`, { params: data }).then(success).catch(fail);
}
function getAttrByAround(data, success, fail) {
  local.get(`attraction/around`, { params: data }).then(success).catch(fail);
}
export { getAttrByType, getAttrByAround };
