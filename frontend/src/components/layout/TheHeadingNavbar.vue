<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { loginStore } from '@/stores/LoginStore.js'
import { storeToRefs } from 'pinia'

const router = useRouter()
const loginstore = loginStore()
const { userId, userProfile, userNickname } = storeToRefs(loginstore)
const { Funclogout } = loginstore
console.log(loginstore.userId)

//로그인을 시킨다고 치면
// userId.value = '김김'
// userId.value = 'admin'

watch(
  () => userId.value,
  (a, b) => {
    console.log('what', a, b)
  },
  { deep: true }
)

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

function test() {
  console.log('userid', userId.value)
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
            <a class="nav-link" href="${root}/trip?action=findForm">핫플찾기🚗</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" @click="test">여행계획🎈</a>
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
          <li class="nav-item after">
            <a class="nav-link" aria-current="page" @click="Funclogout">로그아웃</a>
          </li>
          <li class="nav-item after">
            <a class="nav-link" aria-current="page" href="${root}/member?action=mypage"
              >마이페이지</a
            >
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

  <!-- <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <router-link class="navbar-brand" :to="{ name: 'main' }">
        <img src="@/assets/ssafy_logo.png" class="rounded mx-auto d-block" alt="..." />
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <a class="nav-link" href="#">마이캠퍼스</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">강의실</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">커뮤니티</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              HELP DESK
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><a class="dropdown-item" href="#">FAQ</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">학사규정</a></li>
            </ul>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'book' }">도서정보</router-link>
          </li>
        </ul>
        <form class="d-flex" role="search">
          <input
            class="form-control me-2"
            type="search"
            placeholder="검색..."
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="button">search</button>
        </form>
      </div>
    </div>
  </nav> -->
</template>

<style scoped>
#logo {
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-size: 30px;
}
</style>
