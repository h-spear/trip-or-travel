import { createRouter, createWebHistory } from 'vue-router'
import TheHomeView from '@/views/TheHomeView.vue'
import userRouter from './UserRouter'
import boardRouter from './BoardRouter'
import commentRouter from './CommentRouter'
import App from '@/App.vue'

const router = createRouter({
  history: createWebHistory(),
  // history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/trip-or-travel', //처음 실행시 보여줄 main
      name: 'main',
      component: App,
      children: [
        ...userRouter
        // ...boardRouter, ...commentRouter
      ]
    }
  ]
})

export default router
