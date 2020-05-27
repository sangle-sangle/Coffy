<template>
  <div class="switch-wrapper">
    <div class="mode-color-switch" @click="toggleColorMode">
      <i class="fas fa-exchange-alt"></i>
      <span>{{ mode }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ToggleSwitch',
  data() {
    return {
      mode: sessionStorage.getItem('mode')
    }
  },
  methods: {
    toggleColorMode() {
      if (sessionStorage.getItem('mode') === 'dark') {
        sessionStorage.setItem('mode', 'white');
      } else {
        sessionStorage.setItem('mode', 'dark');
      }
      this.mode = sessionStorage.getItem('mode')
      this.$store.commit('toggleMode');
    }
  }
}
</script>

<style scoped>
.switch-wrapper {
  position: relative;
}

.mode-color-switch {
  text-align: center;
}

.mode-color-switch i {
  padding-right: 10px;
}

.mode-color-switch:hover {
  cursor: pointer;
  font-weight: 600;
}
</style>