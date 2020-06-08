import axios from "axios"

import store from '@/store/index.js'

export function setInterceptors() {
  let instance = axios.create({
    baseURL: 'https://k02b2021.p.ssafy.io:8197/api/'
    // baseURL : 'http://172.30.1.59:8198/api/',
  })
  instance.interceptors.request.use(
    config => {
      let token = store.state.user.token
      if (token) {
        config.headers['access-token'] = token
      }
      return config
    },
    error => Promise.reject(error.response)
  )
  instance.interceptors.response.use(
    config => config,
    error => Promise.reject(error.response)
  )
  return instance
}