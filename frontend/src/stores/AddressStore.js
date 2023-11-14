import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const AddressStore = defineStore('AddressCode', () => {
  //state
  // const Sidos = ref([])
  // const Guguns = ref([])
  // const SidoGuguns = ref([])
  
  // 여기에서 바로 api요청을 통해 모든 시도와 구군을 불러온다. 
  const sidos = ref([
    {text: '서울', value: '서울'},
    {text: '경기', value: '경기'}
  ])
  const gugunBySido = ref({
    '서울':[
      {text: '강동구', value: '강동구'},
      {text: '강남구', value: '강남구'}
    ],
    '경기':[
      {text: '용인시', value: '용인시'},
      {text: '성남시', value: '성남시'}
    ],
  })


  // 시도 조회

  // const count = ref(0)
  // const doubleCount = computed(() => count.value * 2)

  // function increment() {
  //   count.value++
  // }

  return {sidos, gugunBySido}
})
