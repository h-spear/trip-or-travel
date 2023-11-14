import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { logoutUser } from '@/api/user.js'
import { useRouter } from 'vue-router'

export const loginStore = defineStore('loginUser', () => {
  //state
  const userId = ref('')
  const userProfile = ref('')
  const userNickname = ref('')

  // const count = ref(0)
  // const doubleCount = computed(() => count.value * 2)

  function Funclogout() {
    logoutUser(
      (data) => {
        console.log('logout success', data)
        userId.value = ''
        userProfile.value = ''
        userNickname.value = ''
        const router = useRouter()
        router.push({ name: 'main' })
      },
      (error) => {
        console.log('logout fail', error)
      }
    )
  }
  return { userId, userProfile, userNickname, Funclogout }
})
