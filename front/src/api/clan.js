import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchClanInfo(clanId) { // 클랜 정보 가져오는 API
  return instance.get(`clan/${clanId}`)
}

function registerClan(data) { // 클랜 가입 API
  console.log(data)
  return instance.post('')
}

function addClan(data) { // 클랜 생성 API
  console.log(data)
  return instance.post('')
}

function updateClan(data) { // 클랜 정보 수정 API
  console.log(data)
  return instance.put('')
}

function deleteClan(clanId) { // 클랜 삭제 API
  console.log(clanId)
  return instance.delete('')
}

export { 
  fetchClanInfo,
  registerClan,
  addClan,
  updateClan,
  deleteClan
}