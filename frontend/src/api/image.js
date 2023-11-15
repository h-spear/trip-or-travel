import { imageAxios } from '../utils/http-commons'
const { VITE_IMGBB_API_KEY } = import.meta.env

const local = imageAxios()

function uploadImage(url, success, fail) {
  const info = {
    image: url
  }
  local.post(`upload?key=${VITE_IMGBB_API_KEY}`, info).then(success).catch(fail)
}

export { uploadImage }
