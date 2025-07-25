import { getToken } from '@/utils/cookie'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/oj/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/',
      redirect: '/oj/login',
    },
    {
      path: '/oj/layout',
      name: 'layout',
      component: () => import('@/views/Layout.vue'),
      children: [
        {
          path: 'question',
          name: 'question',
          component: () => import('@/views/Question.vue')
        },
        {
          path: 'cuser',
          name: 'cuser',
          component: () => import('@/views/Cuser.vue')
        },
        {
          path: 'exam',
          name: 'exam',
          component: () => import('@/views/Exam.vue')
        },
      ]
    },

  ]
})

router.beforeEach((to, from, next) => {
  if(getToken()) {
    if(to.path === '/oj/login') {
      next({ path: '/oj/layout' })
    } else {
      next()
    }
  } else {
    if(to.path !== '/oj/login') {
      next({ path: '/oj/login' })
    } else {
      next()
    }
  }
})

export default router
