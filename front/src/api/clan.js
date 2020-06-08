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

export {
  fetchAllClans,
  fetchClanInfo,
  registerClan,
  signOutClan,
  addClan,
  updateClan,
  deleteClan
}