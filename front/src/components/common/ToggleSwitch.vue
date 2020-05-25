<template>
  <div class="switch-wrapper">
    <div class="mode-color-switch">
      <i class="fas fa-exchange-alt" @click="toggleMode"></i>
      <span>{{ mode }}</span>
    </div>
    <!-- <div class="mode-switch">
      <input type="checkbox" @click="toggleMode" id="toggle-btn">
    </div>
    <div class="now-mode">
      {{ mode }}
    </div> -->
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

.mode-color-switch i:hover {
  cursor: pointer;
}

/* .mode-switch {
  position: absolute;
  top: 50%;
  left: 30%;
  transform: translate(-50%, -30%);
}

.now-mode {
  position: absolute;
  top: 50%;
  left: 70%;
  transform: translate(-50%, -30%);
  letter-spacing: -0.5px;
}

input[type="checkbox"] {
  position: relative;
  width: 40px;
  height: 20px;
  -webkit-appearance: none;
  background: #c6c6c6;
  outline: none;
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  transition: .5s;
}

input:checked[type="checkbox"] {
  background: #03a9f4;
}

input[type="checkbox"]::before {
  content: '';
  position: absolute;
  width: 20px;
  height: 20px;
  border-radius: 10px;
  top: 0;
  left: 0;
  background: #fff;
  transform: scale(1.1);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  transition: 0.4s;
}

input:checked[type="checkbox"]::before {
  left: 20px;
} */
</style>