<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { loginStore } from '@/stores/LoginStore.js';
import { storeToRefs } from 'pinia';

const router = useRouter();
const loginstore = loginStore();
const { userId, userProfile, userNickname } = storeToRefs(loginstore);
const { Funclogout } = loginstore;

function moveLogin() {
  router.push({ name: 'login' });
}
function moveHome() {
  router.push({ name: 'main' });
}
function moveBoard(boardId) {
  console.log('moveBoard boardId=', boardId);
  router.push({ name: 'board', query: { boardId } });
}
function moveRegist() {
  router.push({ name: 'regist' });
}
function moveMypage() {
  router.push({ name: 'mypage' });
}
function moveMylist() {
  router.push({ name: 'plans' });
}
function moveTrip() {
  router.push({ name: 'trip' });
}

function notPrepare() {
  alert('준비중입니다.');
}
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-white shadow fixed-top">
    <div
      class="container"
      style="display: flex; justify-content: space-between; align-items: center"
    >
      <a id="logo" @click="moveHome">
        <img src="@/assets/logo.png" alt="" width="60" /> Enjoy Trip
      </a>
      <div
        id="collapsibleNavbar"
        style="display: flex; align-items: center; justify-content: center"
      >
        <ul class="navbar-nav me-auto">
          <li class="nav-item nav-link mx-2" @click="moveTrip">여행계획🎈</li>
          <li class="nav-item nav-link mx-2" @click="moveBoard(1)">공지사항</li>
          <li class="nav-item nav-link mx-2" @click="moveBoard(2)">질문게시판</li>
          <li class="nav-item nav-link mx-2" @click="moveBoard(3)">자유게시판</li>
        </ul>
      </div>
      <!-- 로그인 전 -->
      <div class="nav-item dropdown">
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: center;
            margin-right: 10px;
            cursor: pointer;
          "
        >
          <img
            id="profileImg"
            data-bs-toggle="dropdown"
            class="dropdown-toggle"
            :src="userProfile"
            v-if="userProfile != null && userProfile != ''"
          />
          <img
            id="profileImg"
            data-bs-toggle="dropdown"
            class="dropdown-toggle"
            src="@/assets/image/anonymous.png"
            v-if="userProfile == null || userProfile == ''"
          />
          <div style="margin-left: 10px" v-if="userId !== ''">{{ userNickname }}</div>
          <div style="margin-left: 10px" v-if="userId === ''">anonymous</div>
        </div>
        <ul v-if="userId == ''" class="dropdown-menu">
          <li class="dropdown-item" @click="moveRegist">회원가입</li>
          <li class="dropdown-item" @click="moveLogin">로그인</li>
        </ul>
        <div v-else>
          <ul class="dropdown-menu">
            <!-- <li class="dropdown-item" @click="moveMypage">마이페이지</li> -->
            <li class="dropdown-item" @click="notPrepare">마이페이지</li>
            <li class="dropdown-item" @click="moveMylist">여행 계획</li>
            <li class="dropdown-item" @click="Funclogout">로그아웃</li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
#logo {
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-size: 30px;
}
.dropdown:hover .dropdown-menu {
  display: block;
  margin-top: 0;
  cursor: pointer;
}

.nav-link:hover {
  cursor: pointer;
}
</style>

<!-- <ul v-if="userId == ''" class="navbar-nav mb-2 me-2 mb-lg-0 beforeLogin"></ul>
<ul v-else class="navbar-nav mb-2 me-2 mb-lg-0 afterLogin">
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
</ul> -->
