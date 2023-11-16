import { imageAxios } from '../utils/http-commons'
const { VITE_IMGBB_API_KEY } = import.meta.env

const local = imageAxios()

function uploadImage(url, success, fail) {
  const form = new FormData()
  url = url.substring(url.indexOf(',') + 1)
  form.append('key', VITE_IMGBB_API_KEY)
  form.append('image', url)
  console.log(url)
  local.post(`upload`, form).then(success).catch(fail)
}

export { uploadImage }
