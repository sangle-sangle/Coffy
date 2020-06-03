const state = {
  codeData: []
}

const mutations = {
  saveCodeData(state, code) {
    state.codeData = code
  }
}

export default {
  state,
  mutations
}