import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

// 전체 코드 리스트 불러오기
function getGame(id) {
  return instance.get(`game/${id}`)
}

export {
    getGame,
}