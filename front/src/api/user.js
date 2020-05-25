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

export { 
  fetchMyInfo,
  registerUser,
  loginUser
}