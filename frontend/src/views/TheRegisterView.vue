<script setup>
import { ref } from 'vue'
import { loginUser } from '@/api/user.js'

const userEmail = ref('')
const password = ref('')

userEmail.value = 'test@test.com'
password.value = 'pass'

function login() {
  if (!userEmail.value) {
    alert('이메일을 입력해주세요')
  } else if (!password.value) {
    alert('비밀번호를 입력해주세요')
  } else {
    console.log('login....')
    loginUser(
      userEmail.value,
      password.value,
      (data) => {
        console.log('login success', data)
      },
      (error) => {
        console.log('error : ', error)
      }
    )
  }
  //여기에서 아이디랑 비번 서버에 날려보내기
  // 성공 시 jwt 토큰 반환될 에정
  // id는 같이 가지고 있짜
}
</script>

<template>
  <!-- <main class="container box">
    <div class="login-page" style="margin-top: 100px">
      <div class="title">
        <img src="@/assets/logo.png" class="login-logo" />
      </div>
      <div class="form">
        <form name="login-form" class="login-form" method="post" @submit.prevent="login">
          <input type="hidden" name="action" value="login" />
          <input
            id="userEmail"
            name="userEmail"
            type="Email"
            placeholder="이메일을 입력해주세요"
            required
            :key="userEmail"
          />
          <input
            id="password"
            name="password"
            type="password"
            placeholder="비밀번호를 입력해주세요"
            :key="password"
          />
          <button class="login-btn" type="submit">login</button>
          <div class="go">
            <a href="${root }/member/resgister.jsp" class="last">회원가입&nbsp;</a>|
            <a href="#" class="last">&nbsp;아이디 찾기&nbsp;</a>|
            <a href="#" class="last">&nbsp;비밀번호 찾기</a>
          </div>
        </form> -->

  <!-- "email": "test@test.com",
    "password": "1234",
    "passwordConfirm": "1234",
    "name": "test",
    "nickname": "test",
    "age": 10,
    "gender": "MALE",
    "address": {
        "sido": "Seoul",
        "gugun": "Gangnam"
    },
    "profileImageUrl": "imageURL" -->

  <main class="container box">
    <div class="register-page" style="margin-top: 100px">
      <div class="title">
        <img src="@/assets/logo.png" class="login-logo" />
      </div>
      <div class="form">
        <form name="register-form" class="register-form">
          <input id="email" name="email" type="email" placeholder="이메일" required />
          <button class="dupcheck" @click="checkDuplicateEmail">중복 체크</button>
          <input id="nickname" name="nickname" type="text" placeholder="닉네임" required />
          <button class="dupcheck" @click="checkDuplicateNickname">중복 체크</button>
          <input id="password" name="password" type="password" placeholder="비밀번호" required />
          <input
            id="passwordConfirm"
            name="passwordConfirm"
            type="password"
            placeholder="비밀번호 확인"
            required
          />
          <input id="name" name="name" type="text" placeholder="이름" required />
          <input id="age" name="age" type="number" placeholder="나이" />
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
