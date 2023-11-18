const tripRouter = [
  {
    path: '/trip',
    name: 'trip',
    component: () => import('@/views/TheTripView.vue'),
    children: [
    ]
  }
]

export default tripRouter
