import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchAllClans() {
  return instance.get('clans')
}

// 클랜 정보 가져오는 API
function fetchClanInfo(clanId) {
  return instance.get(`clan/${clanId}`)
}

// 클랜 가입 API
function registerClan(data) {
  return instance.post('joinclan', data)
}

// 클랜 탈퇴 API
function signOutClan(data) {
  return instance.put('joinclan', data)
}

// 클랜 생성 API
function addClan(data) {
  return instance.post('clan', data)
}

// 클랜 정보 수정 API
function updateClan(data) {
  return instance.put('clan', data)
}

// 클랜 삭제 API
function deleteClan(clanId) {
  return instance.delete(`clan/${clanId}`)
}

// 각 클랜별 게시판 리스트 불러오기 API
function fetchClanPosts(clanId) {
  return instance.get(`posts/${clanId}`)
}

// 각 클랜별 게시판 내용 추가 API
function addClanPost(data) {
  return instance.post('post', data)
} 

// 각 클랜별 게시판 내용 수정 API
function updateClanPost(data) {
  return instance.post('post', data)
} 

// 각 클랜별 게시판 내용 삭제 API
function deleteClanPost(postId) {
  return instance.delete(`post/${postId}`)
}

export {
  fetchAllClans,
  fetchClanInfo,
  registerClan,
  signOutClan,
  addClan,
  updateClan,
  deleteClan,
  fetchClanPosts,
  addClanPost,
  updateClanPost,
  deleteClanPost
}