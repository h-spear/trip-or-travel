import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const AddressStore = defineStore('AddressCode', () => {
  //state
  const Sidos = ref([])
  const Guguns = ref([])
  const SidoGuguns = ref([])
  const selectedSido = ref(-1)
  const selectedGugun = ref(-1)


  // 시도 조회

  // const count = ref(0)
  // const doubleCount = computed(() => count.value * 2)

  // function increment() {
  //   count.value++
  // }

  return {}
})
