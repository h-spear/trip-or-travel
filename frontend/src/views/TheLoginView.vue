<script setup>
import { ref } from 'vue'

const userId = ref('')
const password = ref('')
function login() {
  console.log('login....')
  //여기에서 아이디랑 비번 서버에 날려보내기
  // 성공 시 jwt 토큰 반환될 에정
  // id는 같이 가지고 있짜
}
</script>

<template>
  <main class="container box">
    <div class="login-page" style="margin-top: 100px">
      <div class="title">
        <img src="@/assets/logo.png" class="login-logo" />
      </div>
      <div class="form">
        <form name="login-form" class="login-form" action="${root }/member" method="post">
          <input type="hidden" name="action" value="login" />
          <input id="userId" name="userId" type="text" placeholder="ID" required :key="userId" />
          <input
            id="password"
            name="password"
            type="password"
            placeholder="password"
            :key="password"
          />
          <button class="login-btn" type="submit" @click="login">login</button>
          <!-- <div><button class="login-btn" type="submit">login</button></div> -->
          <!-- <button onclick="location.href='register.html'" type="button">회원가입</button> -->
          <div class="go">
            <a href="${root }/member/resgister.jsp" class="last">회원가입&nbsp;</a>|
            <a href="#" class="last">&nbsp;아이디 찾기&nbsp;</a>|
            <a href="#" class="last">&nbsp;비밀번호 찾기</a>
          </div>
        </form>
      </div>
    </div>
  </main>
  <!--------------------------------------------------------------------------->
</template>

<style scoped>
/* body {
  background-color: #e4f1ff;
  padding-top: 50px;
} */
.login-page {
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
/* .login-form {
  border: 2px solid pink;
} */
.go {
  display: flex;
  justify-content: center;
}
.last {
  text-decoration: none;
  color: gray;
  font-weight: bold;
  font-size: 14px;
}
.last:hover {
  color: black;
}
input {
  border: 1px solid #a1ccd1;
  border-radius: 20px;
  width: 100%;
  height: 42px;
  margin-bottom: 20px;
  padding-left: 15px;
}

.login-btn {
  border: none;
  font-size: 20px;
  font-weight: bold;
  font-family: 'Times New Roman', Times, serif;
  color: #0c356a;
  border-radius: 20px;
  width: 90px;
  height: 38px;
  margin-bottom: 20px;
  background-color: #aed2ff;
  width: 300px;
  margin-top: 40px;
}
.login-btn:hover,
.update-btn:hover,
.delete-btn:hover {
  color: white;
  box-shadow: 0 0 3px #aed2ff;
}
/*=======================  마이페이지  =======================*/
td {
  height: 90px;
}

#currUser-email,
#currUser-name,
#currUser-age {
  margin-top: 20px;
}

.info {
  font-size: 20px;
  font-weight: bold;
  padding-left: 20px;
  margin-bottom: 20px;
  color: #213555;
}
.update-btn,
.delete-btn {
  border: none;
  font-size: 17px;
  font-weight: bold;
  color: #0c356a;
  border-radius: 20px;
  width: 90px;
  height: 38px;
  background-color: #aed2ff;
  width: 300px;
  margin-top: 20px;
}
.delete-btn {
  background-color: lightgray;
}
/* My Page */
.top {
  margin-top: 100px;
  margin-left: 35%;
  margin-bottom: 80px;
  font-size: 40px;
  font-family: 'Times New Roman', Times, serif;
  font-weight: bold;
  color: #213555;
}

td {
  text-align: center;
}

.mypage {
  padding-left: 40px;
  padding-right: 40px;
}
</style>
