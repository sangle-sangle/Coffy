<template>
  <div class="sidebar-wrapper">
    <div class="service-name">
      <router-link to="/">☕Coffy</router-link>
    </div>
    <hr class="divider" v-if="isLogin">
    <div class="login-user" v-if="isLogin">
      Welcome,<br>{{ userInfo['access-Token'].username }}
    </div>
    <hr class="divider">
    <div @click="toggleColorMode">
      <ToggleSwitch :mode="changeMode"></ToggleSwitch>
    </div>
    <hr class="divider">
    <div class="try-btn" @click="goCodeForm">
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
        <router-link to="/code">Code</router-link>
      </li>
      <li>
        <router-link to="/game">Game</router-link>
      </li>
      <li>
        <router-link to="/clan">Clan</router-link>
      </li>
    </ul>
    <hr class="divider" v-if="this.isLogin">
    <ul class="menu-items" v-if="this.isLogin">
      <li>
        <router-link to="/mypage/dashboard">Dashboard</router-link>
      </li>
      <li>
        <router-link to="/mypage/passwordconfirm">Account</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import ToggleSwitch from '@/components/common/ToggleSwitch.vue';

export default {
  name: 'SideBar',
  components: {
    ToggleSwitch
  },
  data() {
    return {
      changeMode: 'dark',
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      isLogin: state => state.user.isLogin,
      userInfo: state => state.user.userInfo
    })
  },
  mounted() {
    this.changeMode = this.mode
  },
  methods: {
    goCodeForm() {
      this.$router.push('/code/form');
    },
    toggleColorMode() {
      if (sessionStorage.getItem('mode') === 'dark') {
        sessionStorage.setItem('mode', 'white');
      } else {
        sessionStorage.setItem('mode', 'dark');
      }
      this.$store.commit('toggleMode');
    }
  },
  watch: {
    mode() {
      this.changeMode = this.mode
    }
  }
}
</script>

<style scoped>
.sidebar-wrapper {
  padding: 15px;
  height: 100vh;
}

.menu-items > li {
  transition : all 0.5s;
}
.menu-items > li:hover {
  font-size : 115%;
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

.login-user {
  text-align: center;
  padding-bottom: 8px;
  line-height: 1.4;
}
</style>