const boardRouter = [
  {
    path: '/trip',
    name: 'trip',
    component: () => import('@/views/TheTripView.vue'),
    children: [
    ]
  }
]

export default boardRouter
