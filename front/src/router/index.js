import Vue from 'vue'
import Router from 'vue-router'
// import store from '@/store/index.js'
import { loadView, loadComponent } from '@/utils/loadPage.js'

Vue.use(Router)
export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '',
      component: loadComponent('Main', 'Main'),
    },
    {
      path: '/about',
      component: loadView('About'),
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
    },
    {
      path : '/code',
      component : loadComponent('code','codemain'),
    },
    {
      path: '/login',
      component: loadComponent('Login', 'LoginForm'),
    },
    {
      path: '/signup',
      component: loadComponent('Login', 'SignupForm'),
    },
    {
      path: '*',
      name: 'NotFound',
      component: loadView('NotFoundPage'),  // 등록된 URL 주소가 아닌 곳으로 접근할 때 Not Found Page
    }
  ]
})