import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { getSido, getGugun } from '@/api/region.js';

export const AddressStore = defineStore(
  'AddressCode',
  () => {
    //state
    const sidos = ref([]);
    const gugunBySido = ref({});

    function funcGetItems() {
      sidos.value = [];
      getSido(
        ({ data }) => {
          data.data.forEach((elem) => {
            sidos.value.push({
              text: elem.sidoName,
              value: elem.sidoCode
            });
            gugunBySido.value[elem.sidoCode] = [];
            elem.guguns.forEach((subElem) => {
              gugunBySido.value[elem.sidoCode].push({
                text: subElem.gugunName,
                value: subElem.gugunCode
              });
            });
          });
        },
        (error) => {
          console.log('error', error);
        }
      );
    }

    return { sidos, gugunBySido, funcGetItems };
  },
  {
    persist: {
      beforeRestore: (ctx) => console.log(`store 복구 시작: ${ctx.store.$id}`),
      // 여기에 유저 체크 함수를 넣으면 임의의 로그인하는 척하는 공격을 막을 수 있을 듯
      afterRestore: (ctx) => console.log(`store 복구 완료: ${ctx.store.$id}`)
    }
  }
);
