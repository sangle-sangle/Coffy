<template>
  <div class="sidebar-wrapper">
    <div class="service-name" @click="goMain">☕Coffy</div>
    <hr class="divider">
    <ToggleSwitch :mode="this.mode"></ToggleSwitch>
    <hr class="divider">
    <div class="try-btn">
      ✏️try it!
    </div>
    <ul class="menu-items">
      <li>
        <router-link to="/">Home</router-link>
      </li>
      <li>
        <router-link to="/about">About</router-link>
      </li>
      <li>
        <router-link to="/code">code</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ToggleSwitch from '@/components/common/ToggleSwitch.vue';

export default {
  name: 'SideBar',
  components: {
    ToggleSwitch
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
    })
  },
  methods: {
    goMain() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.sidebar-wrapper {
  padding: 15px;
  background-color:#2c303a;
  height: 100vh;
}

.sidebar-wrapper > div,
.sidebar-wrapper > ul {
  margin-bottom: 10px;
}

.service-name,
.try-btn {
  text-align: center;
}

.service-name {
  font-size: 1.8rem;
  font-family: 'Jua';
}

.service-name:hover {
  cursor: pointer;
}

.divider {
  margin: 10px 0 15px;
  border-top: 1px solid silver;
}

.service-name + .divider {
  margin-top: 10px;
}

.try-btn {
  margin: 0 auto;
  width: 120px;
  border: 1px solid lightgray;
  border-radius: 8px;
  background-color: transparent;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  font-weight: 600;
  font-size: 1.1rem;
  position: relative;
  overflow: hidden;
  transition: 0.15s linear;
}

.try-btn:hover {
  cursor: pointer;
  color: #0f0f0d;
  background-color: rgb(252, 251, 225);
  box-shadow: 2px 2px 4px rgba(185, 185, 185, 0.3);
  border: 1px solid transparent;
}

.menu-items > li {
  font-family: 'Gothic A1';
  padding: 8px 0;
}
</style>