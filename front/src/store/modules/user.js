import { loginUser } from '@/api/user.js'
import jwtDecode from 'jwt-decode'

const state = {
  adminAuth: 0,
  token: sessionStorage.getItem('token'),
  isLogin: sessionStorage.getItem('token') === null ? false : true,
  isLoginError: false,
  userInfo : sessionStorage.getItem('token') === null ? {} : jwtDecode(sessionStorage.getItem('token')),
  isPasswordConfirmed: false
};

const mutations = {
  setToken(state, token) {
    state.token = token
    sessionStorage.setItem('token', token)
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
  }
}

const actions = {
  async LOGIN({ commit }, userData) {
    const result = await loginUser(userData)
    if (result.headers['access-token']) {
      commit('setToken', result.headers['access-token'])
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

const getters = {
  // info(state) {
  //   return state.token ? jwtDecode(state.token) : {}
  // }
}

export default {
  state,
  mutations,
  actions,
  getters
}
