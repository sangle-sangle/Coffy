import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

// 전체 코드 리스트 불러오기
function getGame(category,id) {
  return instance.get(`game/${category}/${id}`)
}

function getCategorys(){
  return instance.get('categorylist')
}

function solvedProblem(data){
  return instance.post(`solve/`,data)
}

function solvedCount(){
  return instance.get('solvedcounts/')
}
export {
  getGame,
  getCategorys,
  solvedProblem,
  solvedCount
}