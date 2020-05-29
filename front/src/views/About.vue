<template>
  <div class="about">
    <h1>This is an about page</h1>
    <button>
      <router-link to="/code/game/1">Game01</router-link>
    </button>
    <button>
      <router-link to="/code/game/2">Game02</router-link>
    </button>
    <button>
      <router-link to="/code/game/3">Game03</router-link>
    </button>
    <button>
      <router-link to="/code/game/4">Game04</router-link>
    </button>
    <button>
      <router-link to="/code/game/5">Game05</router-link>
    </button>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    // this.$store.commit('toggleMode');
    this.changeColor(this.mode);
  },
  methods: {
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        console.log('white')
      } else { // 다크 모드일 때
        console.log('dark')
      }
    }
  },
  watch: {
    mode() {
      this.changeColor(this.mode)
    }
  }
}
</script>