import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function addCode(data) {
    return instance.post('Code/', data)
  }

export { 
    addCode
}