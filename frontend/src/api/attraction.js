import { localAxios } from '../utils/http-commons';

const local = localAxios();

function getAttraction(data, success, fail) {
  local.get(`attraction`, { params: data }).then(success).catch(fail);
}
export { getAttraction };
