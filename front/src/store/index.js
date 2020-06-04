import Vue from "vue";
import Vuex from "vuex";
import common from "./modules/common.js";
import user from "./modules/user.js";
import code from "./modules/code.js";
import clan from "./modules/clan.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    common,
    user,
    code,
    clan
  }
});
