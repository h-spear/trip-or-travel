const tripRouter = [
  {
    path: '/trip',
    name: 'trip',
    component: () => import('@/views/TheTripView.vue'),
    // 로그인 필수로 걸어두자
    children: []
  }
];

export default tripRouter;
