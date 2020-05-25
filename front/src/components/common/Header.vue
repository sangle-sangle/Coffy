<template>
  <div>
    <div class="header-wrapper">
      <div class="service-icon">
        <router-link to="/">☕</router-link>
      </div>
      <div class="mobile-menu-icon" @click="toggleMobileMenu">
        <i class="fas fa-caret-square-down"></i>
      </div>
      <form class="search-box" @submit.prevent="searchTemplate">
        <input type="text" id="search" placeholder="Search keyword" v-model="keyword">
        <label for="search" @click="searchTemplate"><i class="fas fa-search"></i></label>
      </form>
      <div v-if="!isLogin && this.$route.path !== '/signup'" class="signup-btn" @click="goPage('/signup')">
        <span v-if="!mobileSize">Sign Up</span>
        <i v-else class="fas fa-user-plus"></i>
      </div>
      <div v-if="this.$route.path !== '/login'" class="login-btn">
        <span v-if="!mobileSize && !isLogin" @click="goPage('/login')">Login</span>
        <span v-else-if="!mobileSize && isLogin" @click="logout">Logout</span>
        <i v-else-if="mobileSize && !isLogin" class="fas fa-sign-in-alt" @click="goPage('/login')"></i>
        <i v-else class="fas fa-sign-out-alt" @click="logout"></i>
      </div>
    </div>
    <div class="mobile-menu" v-show="showMobileMenus">
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
  </div>
</template>

<script>
import { mapState } from 'vuex';
import ToggleSwitch from '@/components/common/ToggleSwitch.vue';

export default {
  name: 'Header',
  components: {
    ToggleSwitch
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      isLogin: state => state.user.isLogin,
    })
  },
  data() {
    return {
      keyword: '',
      showMobileMenus: false,
      mobileSize: false,
    }
  },
  mounted() {
    this.mobileSize = window.innerWidth <= 900
    window.addEventListener('resize', () => {
      if (window.innerWidth > 900) {
        this.showMobileMenus = false
        this.mobileSize = false
      } else {
        this.mobileSize = true
      }
    })
    window.addEventListener('scroll', () => this.showMobileMenus = false)
  },
  methods: {
    goPage(url) {
      this.$router.push(url)
    },
    toggleMobileMenu() {
      this.showMobileMenus = !this.showMobileMenus
    },
    searchTemplate() {
      if (this.keyword) {
        alert(`추후 ${this.keyword} 키워드를 포함하는 템플릿 보여주는 검색 결과 페이지 구현 예정`)
      } else {
        alert('키워드를 입력해주세요.')
      }
    },
    logout() {
      if (confirm('로그아웃 하시겠습니까?')) {
        this.$store.commit('logout')
        if (this.$route.path !== '/') {
          this.$router.push('/')
        }
      }
    }
  },
  watch: {
    '$route'() {
      this.showMobileMenus = false
    }
  }
}
</script>

<style scoped>
.header-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  width: 100%;
  padding: 15px 0 15px 15px;
  background-color: rgb(30, 30, 34);
  font-family: 'Gothic A1';
}

.header-wrapper > div,
.header-wrapper > form {
  margin-right: 10px;
}

.search-box {
  display: flex;
  align-items: center;
  flex-grow: 1;
}

.search-box > input {
  height: 25px;
  width: 100%;
  padding: 5px;
  border: transparent;
  border-radius: 10px;
  background-color: rgb(37, 40, 48);
  color: #eee;
  margin-right: 8px;
}

.signup-btn,
.login-btn {
  display: flex;
  align-items: center;
  align-self: stretch;
  padding: 0 6px;
  color: #333;
  font-weight: 600;
  border-radius: 10px;
}

.signup-btn:hover,
.login-btn:hover {
  cursor: pointer;
}

.signup-btn {
  background-color: rgb(119, 209, 194);
}

.login-btn {
  background-color: rgb(247, 247, 217);
}

.service-icon,
.mobile-menu-icon {
  display: none;
}

.mobile-menu {
  visibility: hidden;
}

.divider {
  margin: 30px 0 15px;
  border-top: 1px solid silver;
}

.try-btn {
  margin: 0 auto 10px;
  text-align: center;
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

@media (max-width: 900px) {
  .service-icon,
  .mobile-menu-icon {
    display: block;
    font-size: 20px;
  }

  .mobile-menu {
    position: absolute;
    z-index: 3;
    visibility: visible;
    width: 150px;
    padding: 15px;
    background-color: #2c303a;
  }
}
</style>