import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchMyInfo() {
  return instance.get('accounts/user/')
}

// 회원가입
function registerUser(userData) {
  return instance.post('accounts/signup/', userData)
}

// 로그인
function loginUser(userData) {
  return instance.post('accounts/login/', userData)
}

// github 로그인
function githubLogin() {

}

export { 
  fetchMyInfo,
  registerUser,
  loginUser,
  githubLogin
}