import { createRouter, createWebHistory } from 'vue-router'
import TheHomeView from '@/views/TheHomeView.vue'
import memberRouter from './MemberRouter'
import boardRouter from './BoardRouter'
import commentRouter from "./CommentRouter"

const router = createRouter({
  history: createWebHistory(),
  // history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', //처음 실행시 보여줄 main
      name: 'main',
      component: TheHomeView
    },
    ...memberRouter,
    ...boardRouter,
    ...commentRouter
  ]
})

export default router
