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
      name: 'AboutPage',
      component: loadView('AboutPage'),
    },
    {
      path : '/code',
      component : loadView('CodePage'),
      children: [
        { path: '', name: 'CodeList', component: loadComponent('Code', 'CodeList') },
        { path: 'form', name: 'CodeForm', component: loadComponent('Code', 'CodeForm') },
        { path: 'detail/:id', name: 'CodeDetail', component: loadComponent('Code', 'CodeDetail'), props: true },
        { path: 'edit/:id', name: 'CodeEdit', component: loadComponent('Code', 'CodeForm'), props: true, beforeEnter: checkLoginUser, },
      ]
    },
    {
      path: '/game',
      component: loadView('GamePage'),
      beforeEnter: checkRegisteredClan,
      children: [
        { path: '', name: 'GameList', component: loadComponent('CodeGame', 'GameList') },
        { path: 'flex/:id', name: 'FlexGame', component: loadComponent('CodeGame', 'Category/FlexGame'), props : true},
        { path: 'text/:id', name : 'TextGame', component: loadComponent('CodeGame', 'Category/TextGame'), props: true},
        { path: 'grid/:id', name : 'GridGame', component: loadComponent('CodeGame', 'Category/GridGame'), props: true},
        { path: 'transition/:id', name : 'TransitionGame', component: loadComponent('CodeGame', 'Category/TransitionGame'), props: true},

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
      beforeEnter: checkRegisteredClan,
      children: [
        { path: '', name: 'ClanList', component: loadComponent('Clan', 'ClanList') },
        { path: 'addform', name: 'ClanForm', component: loadComponent('Clan', 'ClanForm') },
        { path: 'detail/:id', name: 'ClanDetail', component: loadComponent('Clan', 'ClanDetail'), props: true },
        { path: 'edit/:id', name: 'ClanEdit', component: loadComponent('Clan', 'ClanForm'), props: true },
      ]
    },
    {
      path : '/search',
      component : loadView('CodePage'),
      children : [
        { path : ':q' , name : 'Search', component : loadComponent('common','Search')}
      ]
    },
    {
      path: '*',
      name: 'NotFound',
      component: loadView('NotFoundPage'),
    },
    {
      path: '/mypage',
      component: loadView('MyPage'),
      beforeEnter: checkLoginUser,
      children: [
        { path: 'dashboard', name: 'DashBoard', component: loadComponent('MyPage', 'DashBoard') },
        { path: 'passwordconfirm', name: 'PasswordConfirm', component: loadComponent('MyPage', 'PasswordConfirm') },
        { path: 'account', name: 'Account', component: loadComponent('MyPage', 'Account'), beforeEnter: checkPasswordConfirm }
      ]
    },
    {
      path: '/admin',
      name: 'Admin',
      component: loadView('Admin')
    }
  ]
})

function checkNoLoginUser(to, from, next) {  // 로그인이 안 된 경우에 로그인창, 회원가입창 접근 가능
  store.state.user.isLogin ? next('/') : next()
}

function checkLoginUser(to, from, next) {  // 로그인이 안 된 경우에 로그인창, 회원가입창 접근 가능
  store.state.user.isLogin ? next() : next('/login')
}

function checkPasswordConfirm(to, from, next) {
  store.state.user.isPasswordConfirmed ? next() : next('/login')
}

function checkRegisteredClan(to, from, next) { // 로그인한 유저 중 가입된 클랜이 없는 경우에만 클랜 리스트, 클랜 생성 페이지 접근 가능
  if (!store.state.user.isLogin) { // 비로그인 상태이면 로그인을 먼저 하라는 문구 표시 후 로그인 페이지로 이동
    alert('로그인을 먼저 해주세요.')
    next('/login')
  } else {
    next()
  }
}