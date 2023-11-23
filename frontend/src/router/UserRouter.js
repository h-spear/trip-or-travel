import { requireLogin, requireLogout } from './NavigationGuard.js';

const userRouter = [
  {
    //
    path: '/login',
    name: 'login',
    component: () => import('@/views/TheLoginView.vue'),
    beforeEnter: requireLogout()
  },
  {
    path: '/regist',
    name: 'regist',
    component: () => import('@/views/TheRegisterView.vue'),
    beforeEnter: requireLogout()
  },
  {
    //
    path: '/mypage',
    name: 'mypage',
    component: () => import('@/views/TheUserView.vue'),
    redirect: { name: 'my-info' },
    children: [
      {
        path: 'info',
        name: 'my-info',
        component: () => import('@/components/member/Info.vue')
      },
      {
        path: 'plans',
        name: 'my-plans',
        component: () => import('@/components/member/Plans.vue')
      }
    ],
    beforeEnter: requireLogin()
  }
  // {
  //   //
  //   path: 'member',
  //   name: 'member',
  //   component: () => import('@/views/TheTestView.vue'),
  //   children: []
  // }
];
// 회원가입 페이지
// 마이 페이지

// 관리자용
// 회원 관리 페이지
// 회원 리스트 페이지

export default userRouter;
