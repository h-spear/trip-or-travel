<script setup>
import { ref } from 'vue';
import { loginUser, getSimpleInfo } from '@/api/user.js';
import { loginStore } from '@/stores/LoginStore.js';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const userEmail = ref('');
const password = ref('');
const router = useRouter();
// userEmail.value = 'test@test.com'
// password.value = 'pass'

function login() {
  if (!userEmail.value) {
    alert('이메일을 입력해주세요');
  } else if (!password.value) {
    alert('비밀번호를 입력해주세요');
  } else {
    console.log('login....');
    loginUser(
      userEmail.value,
      password.value,
      (data) => {
        console.log('login success', data);
        const loginstore = loginStore();
        const { userId, userProfile, userNickname } = storeToRefs(loginstore);
        getSimpleInfo(
          ({ data }) => {
            console.log('get info success', data);
            userId.value = data.data.id;
            userProfile.value = data.data.profileImageUrl;
            userNickname.value = data.data.nickname;
            console.log(userId.value, userProfile.value, userNickname.value);
            router.push({ name: 'main' });
          },
          (error) => {
            console.log('get simple info error', error);
          }
        );
      },
      (error) => {
        console.log('login error : ', error);
      }
    );
  }
  //여기에서 아이디랑 비번 서버에 날려보내기
  // 성공 시 jwt 토큰 반환될 에정
  // id는 같이 가지고 있짜
}
</script>

<template>
  <main>
    <div class="form-box">
      <div class="form-value">
        <form @submit.prevent="login">
          <h2>로그인</h2>
          <div class="input-box">
            <font-awesome-icon class="icon" icon="fa-solid fa-envelope" />
            <input
              id="userEmail"
              name="userEmail"
              type="email"
              placeholder=""
              v-model="userEmail"
              autocomplete="off"
              required
            />
            <label for="" :class="{ 'input-valid': userEmail != null && userEmail.length > 0 }"
              >이메일</label
            >
          </div>
          <div class="input-box">
            <font-awesome-icon class="icon" icon="fa-solid fa-lock" />
            <input
              id="password"
              name="password"
              type="password"
              placeholder=""
              v-model="password"
            />
            <label for="" :class="{ 'input-valid': password != null && password.length > 0 }"
              >비밀번호</label
            >
          </div>
          <div class="forget">
            <label for=""><input type="checkbox" />아이디 기억</label>
            <a href="#">Forget Password</a>
          </div>
          <button>Login</button>
          <div class="register">
            <p>Don't have a account <a href="#">Sign Up</a></p>
          </div>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: 100vh;
  height: 100%;
}

.form-box {
  position: relative;
  width: 400px;
  height: 450px;
  padding: 40px 0;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  backdrop-filter: blur(15px);
  display: flex;
  justify-content: center;
  align-items: center;
}

h2 {
  font-size: 2em;
  color: #fff;
  text-align: center;
  font-weight: 600;
}

.input-box {
  position: relative;
  margin: 30px 0;
  width: 310px;
  border-bottom: 2px solid #fff;
}

.input-box label {
  position: absolute;
  top: 50%;
  left: 5px;
  transform: translateY(-50%);
  color: #fff;
  font-size: 1em;
  pointer-events: none;
  transition: 0.5s;
}

.input-box input:focus ~ label {
  top: -5px;
}

.input-box .input-valid {
  top: -5px;
}

.input-box input {
  width: 100%;
  height: 50px;
  background-color: transparent;
  border: none;
  outline: none;
  font-size: 1em;
  padding: 0 35px 0 5px;
  color: #fff;
}

.input-box .icon {
  position: absolute;
  right: 8px;
  color: #fff;
  font-size: 1.2em;
  top: 20px;
}

.input-box input::placeholder {
  color: #fff;
}

.forget {
  margin: -15px 0 15px;
  font-size: 0.9em;
  color: #fff;
  display: flex;
  justify-content: space-between;
}

.forget label input {
  margin-right: 3px;
}

.forget a {
  color: #fff;
  text-decoration: none;
}

.forget a:hover {
  text-decoration: underline;
}

button {
  width: 100%;
  height: 40px;
  border-radius: 40px;
  background: #fff;
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 1em;
  font-weight: 600;
}

.register {
  font-size: 0.9em;
  color: #fff;
  text-align: center;
  margin: 25px 0 10px 0;
}

.register p a {
  text-decoration: none;
  color: #fff;
  font-weight: 600;
}

.register p a:hover {
  text-decoration: underline;
}
</style>
