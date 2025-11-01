import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [    
    {
      path: "/",
      redirect: "/home/conference"
    },
    {
      path: "/home",
      name: "home",
      component: () => import("@/views/Home.vue"),
      children: [
        {
          path: 'appointment',
          name: 'appointment',
          component: () => import('@/views/Appointment.vue'),
        },
        {
          path: 'conference',
          name: 'conference',
          component: () => import('@/views/Conference.vue'),
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    }
  ],
})

export default router
