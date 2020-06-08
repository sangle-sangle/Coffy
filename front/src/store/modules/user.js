import { loginUser } from '@/api/user.js'
import jwtDecode from 'jwt-decode'
import { solvedCount } from '@/api/game'

const state = {
  adminAuth: 0,
  token: sessionStorage.getItem('token'),
  isLogin: sessionStorage.getItem('token') === null ? false : true,
  isLoginError: false,
  userInfo : sessionStorage.getItem('token') === null ? {} : jwtDecode(sessionStorage.getItem('token')),
  isPasswordConfirmed: false,
  solved : sessionStorage.getItem('solved') === null ? new Array(10).fill(0) : sessionStorage.getItem('solved').split(',')
};

const mutations = {
  setToken(state, token) {
    state.token = token
    sessionStorage.setItem('token', token)
    sessionStorage.setItem('myClanId', jwtDecode(token)['access-Token'].clanid)
    state.isLogin = true
    state.isLoginError = false
    state.userInfo = jwtDecode(token)
  },
  logout(state) {
    state.token = ''
    state.isLogin = false
    state.isLoginError = false
    state.agreeToS = false
    sessionStorage.clear()
  },
  loginError(state) {
    state.isLoginError = true
  },
  confirmComplete(state){
    state.isPasswordConfirmed = true
  },
  enteredAccount(state){
    state.isPasswordConfirmed = false
  },
  gamesolve(state){
    solvedCount().then(response=>{
      let solved = new Array(10).fill(0)
      for (let i in response.data){
        solved[response.data[i].category_id-1]=response.data[i].count
      }
      state.solved = solved
      sessionStorage.setItem('solved', solved)
    })
  }
}

const actions = {
  async LOGIN({ commit }, userData) {
    const result = await loginUser(userData)
    if (result.headers['access-token']) {
      commit('setToken', result.headers['access-token'])
      commit('gamesolve')
    } else {
      commit('loginError')
    }
    return result
  },
  async PASSWORDCONFIRM({commit}, userData) {
    const result = await loginUser(userData)
    if (result.headers['access-token']) {
      commit('confirmComplete')
      commit('setToken', result.headers['access-token'])
    } else {
      commit('loginError')
    }
    return result
  }
  
};

export default {
  state,
  mutations,
  actions
}
