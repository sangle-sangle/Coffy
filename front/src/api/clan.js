import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchAllClans() {
  return instance.get('clans')
}

function fetchClanInfo(clanId) { // 클랜 정보 가져오는 API
  return instance.get(`clan/${clanId}`)
}

function registerClan(data) { // 클랜 가입 API
  return instance.post('joinclan', data)
}

function signOutClan(data) { // 클랜 탈퇴 API
  return instance.delete('joinclan', data)
}

function addClan(data) { // 클랜 생성 API
  return instance.post('clan', data)
}

function updateClan(data) { // 클랜 정보 수정 API
  return instance.put('clan', data)
}

function deleteClan(clanId) { // 클랜 삭제 API
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