<script setup>
import { ref } from 'vue'
import { emailDupCheck } from '@/api/user.js'

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

const isValid = ref(false)

function checkDuplicateEmail() {
  if (!email.value) {
    alert('이메일을 입력해주세요')
  } else {
    emailDupCheck(
      email.value,
      (data) => {
        console.log('request success', data)
        if (data) {
          alert('가능')
        } else {
          alert('불가능')
        }
      },
      (error) => {
        console.log('dup check failed', error)
      }
    )
  }
}

function onRegister() {
  console.log('go register')
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
          <button class="dupcheck" @click="checkDuplicateEmail">중복 체크</button>
          <input
            id="nickname"
            name="nickname"
            type="text"
            placeholder="닉네임"
            required
            v-model="nickname"
          />
          <button class="dupcheck" @click="checkDuplicateNickname">중복 체크</button>
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
            name="passwordConfirm"
            type="password"
            placeholder="비밀번호 확인"
            required
            v-model="passwordConfirm"
          />
          <input id="name" name="name" type="text" placeholder="이름" required v-model="name" />
          <input id="age" name="age" type="number" placeholder="나이" v-model="age" />
          <div class="form-check form-check-inline">
            <input class="" id="male" name="gender" type="radio" value="MALE" />
            <label class="form-check-label" for="male">남성</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="" id="female" name="gender" type="radio" value="FEMALE" />
            <label class="form-check-label" for="female">여성</label>
          </div>

          <button onclick="regist()" type="button" class="regi-btn">회원 등록</button>
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
