import { imageAxios } from '../utils/http-commons'
const { VITE_IMGBB_API_KEY } = import.meta.env

const local = imageAxios()

function uploadImage(url, success, fail) {
  const form = new FormData()
  // form.append('key', VITE_IMGBB_API_KEY)
  // form.append('image', url)

  const info = {
    key: VITE_IMGBB_API_KEY,
    image: url
  }
  console.log(url)
  // JSON.stringify(info)
  local.post(`upload`, JSON.stringify(info)).then(success).catch(fail)
  // local.post(`upload?key=${VITE_IMGBB_API_KEY}&image=${url}`, JSON.stringify(info)).then(success).catch(fail)
  // local.post(`upload`, form).then(success).catch(fail)
  // local.post(`upload?key=${VITE_IMGBB_API_KEY}&image=${url}`).then(success).catch(fail)
}

export { uploadImage }
