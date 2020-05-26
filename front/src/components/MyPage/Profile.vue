<template>
  <div class="profile-wrapper">
    내가 작성한 코드 확인 페이지
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  data() {
		return {
			
		}
  },
  mounted() {
    this.$store.commit('toggleMode', 0);
    this.changeColor(this.mode)
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

<style>

</style>
