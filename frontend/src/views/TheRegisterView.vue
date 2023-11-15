<script setup>
import { ref } from 'vue'
import { emailDupCheck, nicknameDupCheck, registUser } from '@/api/user.js'
import { AddressStore } from '@/stores/AddressStore.js'
import { useRouter } from 'vue-router'
import VSelect from '@/components/common/VSelect.vue'

const router = useRouter()

const email = ref('test@test.com')
const password = ref('1234')
const passwordConfirm = ref('1234')
const name = ref('test')
const nickname = ref('test')
const age = ref(10)
const gender = ref('MALE')
// 아래 둘은 합쳐서 address로 만들어야함
const sido = ref('Seoul')
const gugun = ref('Gangnam')
const profileImageUrl = ref('imageURL')

const addressStore = AddressStore()
const { sidos, gugunBySido } = addressStore
const guguns = ref([{ text: '', value: '' }])

const isValid = ref(false)
const dupChecked1 = ref(false)
const dupChecked2 = ref(false)

function checkDuplicateEmail() {
  if (!email.value) {
    alert('이메일을 입력해주세요')
  } else {
    emailDupCheck(
      email.value,
      ({ data }) => {
        console.log('request success', data.data)
        if (data.data) {
          alert('중복된 이메일이 존재합니다.')
          dupChecked1.value = false
        } else {
          alert('가능한 이메일입니다.')
          dupChecked1.value = true
        }
      },
      (error) => {
        console.log('dup check failed', error)
      }
    )
  }
}
function checkDuplicateNickname() {
  if (!nickname.value) {
    alert('닉네임을 입력해주세요')
  } else {
    nicknameDupCheck(
      nickname.value,
      ({ data }) => {
        console.log('request success', data.data)
        if (data.data) {
          dupChecked2.value = false
          alert('중복된 닉네임이 존재합니다.')
        } else {
          dupChecked2.value = true
          alert('가능한 닉네임입니다.')
        }
      },
      (error) => {
        console.log('dup check failed', error)
      }
    )
  }
}

function onRegister() {
  if (!(dupChecked1.value && dupChecked2.value)) {
    alert('중복 체크를 완료해주십시오')
  } else {
    const address = { sido: sido.value, gugun: gugun.value }
    const user = {
      email: email.value,
      password: password.value,
      passwordConfirm: passwordConfirm.value,
      name: name.value,
      nickname: nickname.value,
      age: age.value,
      gender: gender.value,
      address: address,
      profileImageUrl: profileImageUrl.value
    }
    registUser(
      user,
      (data) => {
        console.log('register success :', data)
        router.push({ name: 'login' })
      },
      (error) => {
        console.log('error : ', error)
      }
    )
    //submit 값을 바로 확인할 방법이 없을까
  }
}

function selectSido(selectedSido) {
  console.log('selected sido', gugunBySido[selectedSido])
  guguns.value = gugunBySido[selectedSido]
  sido.value = selectedSido
}
function selectGugun(selectedGugun) {
  console.log('selected gugun', selectedGugun)
  gugun.value = selectedGugun
}
</script>

<template>
  <main class="container box">
    <div class="register-page" style="margin-top: 100px">
      <div class="title">
        <img src="@/assets/logo.png" class="login-logo" />
      </div>
      <div class="form">
        <form name="register-form" class="register-form" @submit.prevent="onRegister">
          <input
            id="email"
            name="email"
            type="email"
            placeholder="이메일"
            required
            v-model="email"
          />
          <a class="btn dupcheck" @click="checkDuplicateEmail">중복 체크</a>
          <input
            id="nickname"
            name="nickname"
            type="text"
            placeholder="닉네임"
            required
            v-model="nickname"
          />
          <a class="btn dupcheck" @click="checkDuplicateNickname">중복 체크</a>
          <input
            id="password"
            name="password"
            type="password"
            placeholder="비밀번호"
            required
            v-model="password"
          />
          <input
            id="passwordConfirm"
            name="passwordConfirm1"
            type="password"
            placeholder="비밀번호 확인"
            required
            v-model="passwordConfirm"
          />
          <input id="name" name="name" type="text" placeholder="이름" required v-model="name" />
          <input id="age" name="age" type="number" placeholder="나이" v-model="age" />
          <div class="form-check form-check-inline">
            <input class="" id="male" name="gender" type="radio" value="MALE" v-model="gender" />
            <label class="form-check-label" for="male">남성</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class=""
              id="female"
              name="gender"
              type="radio"
              value="FEMALE"
              v-model="gender"
            />
            <label class="form-check-label" for="female">여성</label>
          </div>
          <label for="sido">시도</label>
          <VSelect id="sido" :selectOptions="sidos" @onKeySelect="selectSido"></VSelect>
          <label for="gugun">구군</label>
          <VSelect id="gugun" :selectOptions="guguns" @onKeySelect="selectGugun"></VSelect>

          <button type="submit" class="regi-btn" :disabled="isValid">회원 등록</button>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped>
body {
  background-color: #e4f1ff;
  background-size: cover; /* 이미지를 화면에 꽉 채우도록 크기 조정 */
  background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
  background-attachment: fixed; /* 스크롤 시 배경 이미지 고정 */
  padding-top: 50px;
}
.register-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
  border-radius: 30px;
  background-color: white;
  width: 500px;
  padding-bottom: 50px;
  box-shadow: 0 0 10px #adc4ce;
  margin-bottom: 140px;
}
.form {
  width: 300px;
  display: flex;
  margin: 0 auto;
}
.login-logo {
  width: 100px;
  height: 100px;
  margin-left: 200px;
  margin-top: 100px;
  margin-bottom: 70px;
}
input {
  border: 1px solid #a1ccd1;
  border-radius: 20px;
  width: 200px;
  height: 42px;
  margin-bottom: 20px;
  padding-left: 15px;
}
.dupcheck {
  border: none;
  font-size: 15px;
  border-radius: 20px;
  width: 90px;
  height: 38px;
  margin-bottom: 20px;
  background-color: #e4f1ff;
}
.dupcheck:hover {
  color: white;
  box-shadow: 0 0 3px #e4f1ff;
}
.regi-btn {
  border: none;
  font-size: 15px;
  border-radius: 20px;
  width: 90px;
  height: 38px;
  margin-bottom: 20px;
  background-color: #aed2ff;
  width: 300px;
  margin-top: 50px;
}
.regi-btn:hover {
  color: white;
  box-shadow: 0 0 3px #aed2ff;
}
#password,
#passwordConfirm,
#name,
#age {
  width: 100%;
}
</style>
