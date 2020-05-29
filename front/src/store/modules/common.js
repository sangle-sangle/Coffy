import { toggleMode } from '@/utils/toggleMode.js';

const state = {
  mode: sessionStorage.getItem('mode')
}

const mutations = {
  toggleMode(state) {
    if (sessionStorage.getItem('mode') === null) {
      state.mode = 'dark';
      sessionStorage.setItem('mode', 'dark');
    } else {
      state.mode = sessionStorage.getItem('mode');
    }
    toggleMode(state.mode);
  }
}

export default {
  state,
  mutations
}