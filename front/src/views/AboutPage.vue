<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>☕Coffy 서비스 설명서 같은거 적을 계획</p>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AboutPage',
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

<style scoped>
.about {
  padding: 15px;
}
</style>