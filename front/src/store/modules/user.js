import { loginUser } from '@/api/user.js'
import jwtDecode from 'jwt-decode'

const state = {
  adminAuth: 0,
  token: sessionStorage.getItem('token'),
  isLogin: sessionStorage.getItem('token') === null ? false : true,
  isLoginError: false
};

const mutations = {
  setToken(state, token) {
    state.token = token
    sessionStorage.setItem('token', token)
    state.isLogin = true
    state.isLoginError = false
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
  }
}

const actions = {
  async LOGIN({ commit }, userData) {
    const result = await loginUser(userData)
    if (result.data.token) {
      commit('setToken', result.data.token)
    } else {
      commit('loginError')
    }
    return result
  }
};

const getters = {
  info(state) {
    return state.token ? jwtDecode(state.token) : {}
  }
}

export default {
  state,
  mutations,
  actions,
  getters
}
