import { requireLogin } from './NavigationGuard.js'

const userRouter = [
  {
    //
    path: '/login',
    name: 'login',
    component: () => import('@/views/TheLoginView.vue')
  },
  {
    path: '/regist',
    name: 'regist',
    component: () => import('@/views/TheRegisterView.vue')
    // beforeEnter: requireLogin()
  }
  // {
  //   //
  //   path: 'mypage',
  //   name: 'mypage',
  //   component: () => import('@/views/TheTestView.vue')
  // },
  // {
  //   //
  //   path: 'member',
  //   name: 'member',
  //   component: () => import('@/views/TheTestView.vue'),
  //   children: []
  // }
]
// 로그인 페이지
// 회원가입 페이지
// 마이 페이지

// 관리자용
// 회원 관리 페이지
// 회원 리스트 페이지

export default userRouter
