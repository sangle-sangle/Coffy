<template>
  <div class="switch-wrapper">
    <div class="mode-color-switch">
      <i class="fas fa-exchange-alt" @click="toggleMode"></i>
      <span>{{ mode }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ToggleSwitch',
  props: {
    mode: {
      type: String
    }
  },
  methods: {
    toggleMode() {
      this.$store.commit('toggleMode', 1);
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

.mode-color-switch i:hover {
  cursor: pointer;
}
</style>