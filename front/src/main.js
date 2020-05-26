import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js'
import Swiper from 'swiper'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
import VueCodemirror from 'vue-codemirror'

Vue.config.productionTip = false
Vue.use(VueCodemirror)

Vue.use(VueAwesomeSwiper, {})

new Vue({
  router,
  store,
  Swiper,
  render: h => h(App)
}).$mount('#app')
