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
  solved : sessionStorage.getItem('solved') === null ? new Array(10).fill(0) : jwtDecode(sessionStorage.getItem('token')).split(',')
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
  },
  gamesolve(state, data){
    state.solved = data
    sessionStorage.setItem('solved', data)
  }
}

const actions = {
  async LOGIN({ commit }, userData) {
    const result = await loginUser(userData)
    if (result.headers['access-token']) {
      commit('setToken', result.headers['access-token'])
      solvedCount().then(response=>{
        let solved = new Array(10).fill(0)
        console.log(solved)
        for (let i in response.data){
          console.log(response.data[i])
          solved[response.data[i].category_id-1]=response.data[i].count
        }
        commit('gamesolve',solved)

      })
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
