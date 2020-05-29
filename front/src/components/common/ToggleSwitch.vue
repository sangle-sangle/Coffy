<template>
  <div class="switch-wrapper">
    <div class="mode-color-switch">
      <i class="fas fa-exchange-alt"></i>
      <span>{{ mode }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ToggleSwitch',
  props: {
    mode: {
      type: String,
      default: sessionStorage.getItem('mode') === null ? 'dark' : sessionStorage.getItem('mode')
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