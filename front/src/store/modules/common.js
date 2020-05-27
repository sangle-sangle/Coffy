import { toggleMode } from '@/utils/toggleMode.js';

const state = {
  mode: sessionStorage.getItem('mode')
}

const mutations = {
  toggleMode(state) {
    state.mode = sessionStorage.getItem('mode');
    toggleMode(state.mode);
  }
}

export default {
  state,
  mutations
}