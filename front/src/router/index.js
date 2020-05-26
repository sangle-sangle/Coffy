import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store/index.js'
import { loadView, loadComponent } from '@/utils/loadPage.js'

Vue.use(Router)
export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '',
      name: 'Main',
      component: loadComponent('Main', 'Main'),
    },
    {
      path: '/about',
      component: loadView('About'),
    },
    {
      path : '/code',
      component : loadView('CodePage'),
      children: [
        { path: '', name: 'CodeMain', component: loadComponent('Code', 'CodeMain') }
      ]
    },
    {
      path: '/login',
      component: loadView('LoginPage'),
      beforeEnter: checkNoLoginUser,
      children: [
        { path: '', name: 'Login', component: loadComponent('Login', 'LoginForm') },
        { path: 'findaccount', name: 'FindPassword', component: loadComponent('Login', 'FindPassword') }
      ]
    },
    {
      path: '/signup',
      component: loadComponent('Login', 'SignupForm'),
      beforeEnter: checkNoLoginUser,
    },
    {
      path: '/clan',
      component: loadView('ClanPage'),
      children: [
        { path: '', name: 'ClanMain', component: loadComponent('Clan', 'ClanMain') }
      ]
    },
    {
      path: '*',
      name: 'NotFound',
      component: loadView('NotFoundPage'),  // 등록된 URL 주소가 아닌 곳으로 접근할 때 Not Found Page
    },
    {
      path: '/mypage',
      component: loadView('MyPage'),
      // beforeEnter: checkLoginUser,
      children: [
        { path: 'dashboard', name: 'DashBoard', component: loadComponent('MyPage', 'DashBoard') },
        { path: 'profile', name: 'Profile', component: loadComponent('MyPage', 'Profile') },
      ]
    }
  ]
})

function checkNoLoginUser(to, from, next) {  // 로그인이 안 된 경우에 로그인창, 회원가입창 접근 가능
  store.state.user.isLogin ? next('/') : next()
}

// function checkLoginUser(to, from, next) { // 로그인이 된 경우에 mypage 접근 가능
//   !store.state.user.isLogin ? next('/') : next()
// }