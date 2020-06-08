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
    await addClan(data);
    await commit('toggleModal');
  },
  async updateClanData({ commit }, data) {
    await updateClan(data);
    await commit('toggleModal');
  }
}

export default {
  state,
  mutations,
  actions
}