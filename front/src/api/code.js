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

// 코드 키워드 검색 
function fetchSearchCode(q) {
  return instance.get(`codes/${q}`)
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

// 해당 코드에 내가 좋아요를 눌렀는지 확인
function checkLikeCode(params) {
  return instance.get('like', params)
}

// 코드 좋아요 추가
function postLikeCode(codeid) {
  return instance.post(`likecode/${codeid}`)
}

// 코드 좋아요 삭제
function deleteLikeCode(codeid) {
  return instance.delete(`likecode/${codeid}`)
}

// 코드 댓글 리스트 보기
function fetchCodeComments(codeid) {
  return instance.get(`commentcode/${codeid}`)
}

// 코드 댓글 수 가져오기
function fetchCodeCommentsCount(codeid) {
  return instance.get(`commentcounts/${codeid}`)
}

// 코드 댓글 등록
function addCodeComment(params) {
  return instance.post('commentcode', params)
}

// 코드 댓글 수정
function updateCodeComment(params) {
  return instance.put('commentcode', params)
}

// 코드 댓글 삭제
function deleteCodeComment(commentId, codeId) {
  return instance.delete(`commentcode/${commentId}/${codeId}`)
}

// 내가 작성한 코드 가져오기
function getMyCode() {
  return instance.get('mycodes')
}

// 내가 찜한 코드 가져오기
function getLikedCode(){
  return instance.get('mylikes')
}

export {
  fetchAllCode,
  fetchCodeInfo,
  fetchSearchCode,
  addCode,
  updateCode,
  deleteCode,
  checkLikeCode,
  postLikeCode,
  deleteLikeCode,
  fetchCodeComments,
  fetchCodeCommentsCount,
  addCodeComment,
  updateCodeComment,
  deleteCodeComment,
  getMyCode,
  getLikedCode
}