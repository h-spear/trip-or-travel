<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { loginStore } from '@/stores/LoginStore.js'
import { storeToRefs } from 'pinia'

const router = useRouter()
const loginstore = loginStore()
const { userId, userProfile, userNickname } = storeToRefs(loginstore)
const { Funclogout } = loginstore

function moveLogin() {
  router.push({ name: 'login' })
}
function moveHome() {
  router.push({ name: 'main' })
}
function moveBoard() {
  router.push({ name: 'board' })
}
function moveRegist() {
  router.push({ name: 'regist' })
}
function moveMypage() {
  router.push({ name: 'mypage' })
}

function test() {
  router.push({ name: 'test' })
}
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-white shadow fixed-top">
    <div class="container">
      <a class="navbar-brand text-black fw-bold" id="logo" @click="moveHome">
        <img src="@/assets/logo.png" alt="" width="60" /> Enjoy Trip
      </a>
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <a class="nav-link" href="#">공지사항</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" @click="test">테스트용🚗</a>
          </li>
          <li class="nav-item">
            <a class="nav-link">여행계획🎈</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" @click="moveBoard">게시판✨</a>
          </li>
        </ul>
        <!-- 로그인 전 -->
        <ul v-if="userId == ''" class="navbar-nav mb-2 me-2 mb-lg-0 beforeLogin">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" @click="moveRegist">회원가입 ✍🏻</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" aria-current="page" @click="moveLogin">로그인</a>
          </li>
        </ul>
        <ul v-else class="navbar-nav mb-2 me-2 mb-lg-0 afterLogin">
          <li class="nav-item btn"><img id="profileImg" :src="userProfile" alt="프로필" /></li>
          <li class="nav-item">{{ userNickname }}님 안녕하세요</li>

          <li class="nav-item after">
            <a class="nav-link" aria-current="page" @click="Funclogout">로그아웃</a>
          </li>
          <li class="nav-item after">
            <a class="nav-link" aria-current="page" @click="moveMypage">마이페이지</a>
          </li>
          <li v-if="userId == 'admin'" class="nav-item dropdown admin">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              관리자
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#pollModal"
                  >투표만들기</a
                >
              </li>
              <li>
                <a
                  class="dropdown-item"
                  data-bs-toggle="modal"
                  data-bs-target="#announce-register"
                  href="#"
                  >공지사항 등록</a
                >
              </li>
              <li><a class="dropdown-item" href="#">회원관리</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
#logo {
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-size: 30px;
}

#profileImg {
  margin: 10px;
  width: 50px;
  height: 50px;
}
</style>
