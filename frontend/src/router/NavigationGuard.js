import { useRouter } from 'vue-router'
import { loginStore } from '@/stores/LoginStore.js'

const requireLogin = () => (to, from, next) => {
  const loginstore = loginStore()
  if (!loginstore.userId) {
    alert('로그인 후 이용해주세요')
    const router = useRouter()
    router.push({ name: 'login' })
    return
  } else {
    next()
  }
}

const requireLogout = () => (to, from, next) => {
  const loginstore = loginStore()
  if (loginstore.userId) {
    alert('로그아웃 후 이용해주세요')
    // const router = useRouter()
    // router.push({ name: 'login' })
    return
  } else {
    next()
  }
}

export { requireLogin, requireLogout }
