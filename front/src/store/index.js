import Vue from "vue";
import Vuex from "vuex";
import common from "./modules/common.js";
import user from "./modules/user.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    common,
    user,
  }
});
