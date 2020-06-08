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

// 로그인 및 비밀번호 재 확인
function loginUser(userData) {
  return instance.post('login/', userData)
}

// github 로그인
function githubLogin() {

}
// 비밀번호 수정
function changePassword(userData){
  return instance.put('member/password/', userData)
}

// 내 정보 수정
function updateMyInfo(userData) {
  return instance.put('member/', userData)
}

// 회원탈퇴
function deleteMyInfo(memberid) {
  return instance.delete(`member/${memberid}/`)
}

// 유저 전체 목록 불러오기(관리자용)
function fetchAllUsers() {
  return instance.get('memberlist')
}

// 가입된 회원수 가져오기(관리자용)
function fetchUsersCount() {
  return instance.get('member')
}

export { 
  fetchMyInfo,
  registerUser,
  loginUser,
  githubLogin,
  changePassword,
  updateMyInfo,
  deleteMyInfo,
  fetchAllUsers,
  fetchUsersCount
}