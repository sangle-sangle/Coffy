const state = {
  clanData: []
}

const mutations = {
  saveClanData(state, clan) {
    state.clanData = clan
  }
}

export default {
  state,
  mutations
}