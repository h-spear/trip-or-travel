const boardRouter = [
  {
    path: '/board',
    name: 'board',
    redirect: { name: 'board-list' },
    component: () => import('@/views/TheBoardView.vue'),
    props: true,
    children: [
      {
        path: '',
        name: 'board-list',
        component: () => import('@/components/board/BoardList.vue'),
        props: true
      },
      {
        path: 'detail',
        name: 'board-detail',
        component: () => import('@/components/board/BoardDetail.vue'),
        props: true
      },
      {
        path: 'write',
        name: 'board-write',
        component: () => import('@/components/board/BoardWrite.vue')
      },
      {
        path: 'modify',
        name: 'board-modify',
        component: () => import('@/components/board/BoardModify.vue')
      }
    ]
  }
];

export default boardRouter;
