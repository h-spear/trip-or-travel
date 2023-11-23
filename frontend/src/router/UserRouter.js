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
      }
    ],
    beforeEnter: requireLogin()
  },
  {
    path: '/detail/:id',
    name: 'my-plans-detail',
    component: () => import('@/components/member/Detail.vue')
  },
  {
    path: '/plans',
    name: 'plans',
    component: () => import('@/components/member/Plans.vue')
  }
];

export default userRouter;
