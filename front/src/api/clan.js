import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchClanInfo(clanid) {
  return instance.get(`clan/${clanid}`)
}

function registerClan(data) {
  return instance.post('')
}

function addClan(data) {
  return instance.post('')
}

export { 
  fetchClanInfo,
  registerClan,
  addClan
}