import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const loginStore = defineStore('loginUser', () => {
  //state
  const userId = ref('')
  const userProfile = ref('')
  const userNickname = ref('')

  // const count = ref(0)
  // const doubleCount = computed(() => count.value * 2)

  // function increment() {
  //   count.value++
  // }

  return { userId, userProfile, userNickname }
})
