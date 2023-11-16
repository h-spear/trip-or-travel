import axios from 'axios'
// axios.defaults.withCredentials = true
const { VITE_API_BASE_URL, VITE_IMGBB_BASE_URL } = import.meta.env

function localAxios() {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    },
    withCredentials: true
  })
  return instance
}

function imageAxios() {
  const instance = axios.create({
    baseURL: VITE_IMGBB_BASE_URL,
    headers: {
      'Content-Type': 'multipart/form-data;'
    }
  })
  return instance
}

export { localAxios, imageAxios }
