import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchAllClans() {
  return instance.get('clans')
}

function fetchClanInfo(clanId) { // 클랜 정보 가져오는 API
  return instance.get(`clan/${clanId}`)
}

function registerClan(data) { // 클랜 가입 API
  console.log(data)
  return instance.post('')
}

function addClan(data) { // 클랜 생성 API
  return instance.put('clan', data)
}

function updateClan(data) { // 클랜 정보 수정 API
  console.log(data)
  return instance.put('')
}

function deleteClan(clanId) { // 클랜 삭제 API
  return instance.delete(`clan/${clanId}`)
}

export {
  fetchAllClans,
  fetchClanInfo,
  registerClan,
  addClan,
  updateClan,
  deleteClan
}