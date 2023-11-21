const tripRouter = [
  {
    path: '/trip',
    name: 'trip',
    redirect: { name: 'trip-build' },
    // component: () => import('@/views/TheTripView.vue'),
    // 로그인 필수로 걸어두자
    children: [
      {
        path: 'build',
        name: 'trip-build',
        component: () => import('@/views/TheTripView.vue')
      },
      {
        path: 'detail',
        name: 'trip-detail',
        component: () => import('@/components/map/detail.vue')
      }
    ]
  }
];

export default tripRouter;
