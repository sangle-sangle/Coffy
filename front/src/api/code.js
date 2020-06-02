import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

// 전체 코드 리스트 불러오기
function fetchAllCode() {
  return instance.get('codes')
}

// 특정 코드 정보 가져오기
function fetchCodeInfo(codeid) {
  return instance.get(`code/${codeid}`)
}

// 코드 등록
function addCode(data) {
  return instance.post('code', data)
}

// 코드 수정
function updateCode(data) {
  return instance.put('code', data)
}

// 코드 삭제
function deleteCode(codeid) {
  return instance.delete(`code/${codeid}`)
}


export {
  fetchAllCode,
  fetchCodeInfo,
  addCode,
  updateCode,
  deleteCode
}