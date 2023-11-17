import { localAxios } from '../utils/http-commons'

const local = localAxios()

function getSido(success, fail) {
  local.get(`region/sido`).then(success).catch(fail)
}
function getGugun(id, success, fail) {
  local.get(`region/sido/${id}`).then(success).catch(fail)
}
export { getSido, getGugun }
