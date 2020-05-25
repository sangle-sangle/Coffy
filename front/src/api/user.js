import { setInterceptors } from './config/interceptors'

const instance = setInterceptors()

function fetchMyInfo() {
    return instance.get('accounts/user/')
  }

export { 
    fetchMyInfo
}