import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

// 내 정보 가져오는 API
function fetchMyInfo(memberid) {
  return instance.get(`member/${memberid}`)
}

// 회원가입
function registerUser(userData) {
  return instance.post('member/', userData)
}

// 로그인
function loginUser(userData) {
  return instance.post('login/', userData)
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