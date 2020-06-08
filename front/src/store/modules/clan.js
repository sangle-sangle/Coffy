import { addClan, updateClan } from '@/api/clan.js'

const state = {
  clanData: [],
  clanModal: false
}

const mutations = {
  saveClanData(state, clan) {
    state.clanData = clan
  },
  toggleModal(state) {
    state.clanModal != this.clanModal
  }
}

const actions = {
  async addClanData({ commit }, data) {
    let response = await addClan(data);
    sessionStorage.setItem('myClanId', response.data.id);
    await commit('toggleModal');
  },
  async updateClanData({ commit }, data) {
    let response = await updateClan(data);
    sessionStorage.setItem('myClanId', response.data.id);
    await commit('toggleModal');
  }
}

export default {
  state,
  mutations,
  actions
}