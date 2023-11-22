<script setup>
import { ref } from 'vue';
import { emailDupCheck, nicknameDupCheck, registUser } from '@/api/user.js';
import { AddressStore } from '@/stores/AddressStore.js';
import { useRouter } from 'vue-router';
import { uploadImage } from '@/api/image.js';

const router = useRouter();

const email = ref('');
const password = ref('');
const passwordConfirm = ref('');
const name = ref('');
const nickname = ref('');
const age = ref(null);
const gender = ref('MALE');
// 아래 둘은 합쳐서 address로 만들어야함
const sido = ref('시도');
const gugun = ref('구군');
const profileImageUrl = ref('https://i.ibb.co/jhrRmpY/anonymous.png');

const addressStore = AddressStore();
const { sidos, gugunBySido } = addressStore;
const guguns = ref([{ text: '', value: '' }]);

const isValid = ref(false);
const dupCheckEmail = ref(false);
const dupCheckNickname = ref(false);
const dupCheckEmailBtn = ref('중복 체크');
const dupCheckNicknameBtn = ref('중복 체크');

function checkDuplicateEmail() {
  if (!email.value) {
    alert('이메일을 입력해주세요');
  } else {
    emailDupCheck(
      email.value,
      ({ data }) => {
        console.log('request success', data.data);
        if (data.data) {
          resetEmailCheck();
          alert('중복된 이메일이 존재합니다.');
        } else {
          alert('가능한 이메일입니다.');
          dupCheckEmail.value = true;
          dupCheckEmailBtn.value = '체크 완료';
        }
      },
      (error) => {
        console.log('dup check failed', error);
      }
    );
  }
}
function checkDuplicateNickname() {
  if (!nickname.value) {
    alert('닉네임을 입력해주세요');
  } else {
    nicknameDupCheck(
      nickname.value,
      ({ data }) => {
        console.log('request success', data.data);
        if (data.data) {
          resetNicknameCheck();
          alert('중복된 닉네임이 존재합니다.');
        } else {
          dupCheckNickname.value = true;
          dupCheckNicknameBtn.value = '체크 완료';
          alert('가능한 닉네임입니다.');
        }
      },
      (error) => {
        console.log('dup check failed', error);
      }
    );
  }
}

function getFileName(data) {
  const fileReader = new FileReader();
  fileReader.readAsDataURL(data[0]);
  fileReader.onload = () => {
    console.log('value', fileReader);
    uploadImage(
      fileReader.result,
      ({ data }) => {
        console.log('success', data);
        profileImageUrl.value = data.data.url;
      },
      (error) => {
        console.log('error ', error);
      }
    );
  };
}

const resetEmailCheck = () => {
  dupCheckEmail.value = false;
  dupCheckEmailBtn.value = '중복 체크';
};
const resetNicknameCheck = () => {
  dupCheckNickname.value = false;
  dupCheckNicknameBtn.value = '중복 체크';
};

function onRegister() {
  if (!(dupCheckEmail.value && dupCheckNickname.value)) {
    alert('중복 체크를 완료해주십시오');
  } else {
    const address = {
      sido: sido.value !== '시도' ? sido.value : null,
      gugun: gugun.value !== '구군' ? gugun.value : null
    };
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
    };
    console.log(user);
    registUser(
      user,
      (data) => {
        console.log('register success :', data);
        alert('회원가입이 완료되었습니다!');
        router.push({ name: 'login' });
      },
      (error) => {
        console.log('error : ', error);
      }
    );
  }
}

const handleSido = (selectedSido) => {
  guguns.value = gugunBySido[selectedSido];
  gugun.value = '구군';
  sido.value = selectedSido;
  console.log('selected sido', selectedSido, gugunBySido[selectedSido]);
  console.log('selected gugun', gugun.value);
};
</script>

<template>
  <main>
    <div class="form-box">
      <form @submit.prevent="onRegister">
        <h2>회원가입</h2>
        <div class="input-box">
          <font-awesome-icon class="icon" icon="fa-solid fa-envelope" />
          <input
            id="email"
            name="email"
            type="email"
            placeholder=""
            v-model="email"
            autocomplete="off"
            @keydown="resetEmailCheck"
            required
          />
          <label for="" :class="{ 'input-valid': email != null && email.length > 0 }"
            >이메일
          </label>
          <a
            class="btn dupcheck-btn"
            @click="checkDuplicateEmail"
            :class="{ show: email != null && email.length > 0, checked: dupCheckEmail }"
            >{{ dupCheckEmailBtn }}</a
          >
        </div>
        <div class="input-box">
          <font-awesome-icon class="icon" icon="fa-solid fa-user" />
          <input
            id="nickname"
            name="nickname"
            type="text"
            placeholder=""
            v-model="nickname"
            autocomplete="off"
            @keydown="resetNicknameCheck"
            required
          />
          <label
            for=""
            :class="{
              'input-valid': nickname != null && nickname.length > 0
            }"
            >닉네임</label
          >
          <a
            class="btn dupcheck-btn"
            :class="{ show: nickname != null && nickname.length > 0, checked: dupCheckNickname }"
            @click="checkDuplicateNickname"
            >{{ dupCheckNicknameBtn }}</a
          >
        </div>
        <div class="input-box">
          <font-awesome-icon class="icon" icon="fa-solid fa-lock" />
          <input id="password" name="password" type="password" placeholder="" v-model="password" />
          <label for="" :class="{ 'input-valid': password != null && password.length > 0 }"
            >비밀번호</label
          >
        </div>
        <div class="input-box">
          <font-awesome-icon class="icon" icon="fa-solid fa-lock" />
          <input
            id="passwordConfirm"
            name="passwordConfirm"
            type="password"
            placeholder=""
            v-model="passwordConfirm"
          />
          <label
            for=""
            :class="{ 'input-valid': passwordConfirm != null && passwordConfirm.length > 0 }"
            >비밀번호 확인</label
          >
        </div>
        <div class="two-input-box">
          <div class="input-box">
            <input
              id="name"
              name="name"
              type="text"
              placeholder=""
              v-model="name"
              autocomplete="off"
              required
            />
            <label for="" :class="{ 'input-valid': name != null && name.length > 0 }">이름</label>
          </div>
          <div class="input-box">
            <input
              id="age"
              name="age"
              type="number"
              placeholder=""
              v-model="age"
              required
              min="1900"
              max="2025"
            />
            <label for="" :class="{ 'input-valid': age > 0 }">태어난 연도</label>
          </div>
        </div>

        <div class="gender-input-box">
          <div for="" class="gender-box-label">성별</div>
          <a-radio-group v-model:value="gender" size="large">
            <a-radio-button value="MALE">남자</a-radio-button>
            <a-radio-button value="FEMALE">여자</a-radio-button>
          </a-radio-group>
        </div>

        <div class="region-input-box">
          <div for="" class="gender-box-label">거주지</div>
          <div class="two-input-box">
            <a-select
              ref="select"
              v-model:value="sido"
              @change="handleSido"
              style="width: 46%"
              :value="0"
              placeholder="시도"
              size="large"
            >
              <a-select-option
                v-for="(sido, index) in sidos"
                :key="index"
                :v-model="sido"
                :value="sido.value"
                >{{ sido.text }}</a-select-option
              >
            </a-select>

            <a-select
              ref="select"
              v-model:value="gugun"
              style="width: 46%"
              placeholder="구군"
              size="large"
            >
              <a-select-option
                v-for="(gugun, index) in guguns"
                :key="index"
                :v-model="gugun"
                :value="gugun.value"
                placeholder="구군"
                @change="handleSido"
                >{{ gugun.text }}</a-select-option
              >
            </a-select>
          </div>
        </div>

        <div class="profile-input-box">
          <div for="" class="profile-box-label">프로필 사진</div>
          <input type="file" id="upload-image" hidden @change="getFileName($event.target.files)" />
          <div class="profile-input">
            <label for="upload-image" class="profile-image-view">
              <img
                :src="profileImageUrl"
                id="profileImg"
                alt="profileImg"
                style="width: 100px; height: 100px"
              />
            </label>
            <p>
              ● <label for="upload-image" style="cursor: pointer"><b>사진 등록</b></label
              ><br />● 32MB 이하<br />● 250px X 250px
            </p>
          </div>
        </div>

        <button type="submit" :disabled="isValid">회원가입</button>
      </form>
    </div>
  </main>
</template>

<style scoped>
main {
  padding-top: 100px;
  display: flex;
  justify-content: center;
  width: 100%;
  min-height: 100vh;
  height: 100%;
}

.form-box {
  position: relative;
  width: 400px;
  height: 1200px;
  padding: 40px 0;
  background: #00000040;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  backdrop-filter: blur(15px);
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 40px 0 80px 0;
}

.form-box form {
  display: flex;
  flex-direction: column;
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
  width: 100%;
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

.input-box .dupcheck-btn {
  position: absolute;
  right: 5px;
  padding: 0;
  top: 50%;
  transition: 0.5s;
  transform: translateY(-50%);
  color: rgb(118, 252, 85);
  font-size: 1em;
  visibility: hidden;
  opacity: 0;
  border: 0;
  font-weight: 700;
}
.input-box .dupcheck-btn.show {
  visibility: visible;
  top: -5px;
  opacity: 1;
}

.input-box .dupcheck-btn.checked {
  top: -5px;
  opacity: 1;
  color: yellow;
}

input[type='number']::-webkit-inner-spin-button {
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
}

.gender-input-box {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.gender-input-box label {
  background-color: #ffffff10;
  color: #fff;
  text-align: center;
  width: 50%;
}
.gender-box-label,
.profile-box-label {
  background-color: transparent;
  color: #fff;
  font-size: 1em;
  margin-bottom: 10px;
  margin-left: 5px;
}

.region-input-box {
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
}

.two-input-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.two-input-box .input-box {
  width: 45%;
}

.profile-input-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  margin-bottom: 40px;
}

.profile-input-box .profile-input {
  margin-left: 5px;
  display: flex;
  align-items: center;
}
.profile-input-box .profile-image-view {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 5px;
}

.profile-input-box .profile-input p {
  color: #fff;
  line-height: 30px;
  margin: 0;
  padding-left: 20px;
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
