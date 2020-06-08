<template>
  <div id="app">
    <nav>
      <SideBar></SideBar>
    </nav>
    <section>
      <Header id="header-section"></Header>
      <router-view id="page-view" />
      <Footer id="footer-section"></Footer>
    </section>
    <transition name="fade">
      <div class="scorllTopBtn" v-if="fab" @click="goTop">
        <i class="fas fa-arrow-up"></i>
      </div>
    </transition>
  </div>
</template>

<script>
import SideBar from '@/components/common/SideBar.vue';
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';
import '@/assets/css/default.css';

export default {
  name: 'App',
  components: {
    SideBar,
    Header,
    Footer
  },
  data() {
    return {
      fab: false
    }
  },
  created() {
    window.addEventListener('scroll', () => {
      let scrollHegiht = document.scrollingElement.scrollTop
      this.fab = scrollHegiht >= 180
    })
  },
  updated() {
    this.$store.commit('toggleMode')
  },
  methods: {
    goTop() {
      window.scrollTo(0, 0);
    }
  },
  watch: {
    '$route'() {
      if (this.$route.name !== 'AboutPage') {
        this.goTop();
      }
    }
  }
}
</script>

<style scoped>
#app {
  display: flex;
}

nav {
  position: fixed;
  min-width: 170px;
  min-height: 100%;
}

section {
  padding-left: 170px;
  width: 100%;
}

.scorllTopBtn {
  position: fixed;
  right: 20px;
  bottom: 14px;
  padding: 16px 18px;
  border-radius: 50%;
  background-color: darkgray;
  font-size: 19px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.4);
}

.scorllTopBtn:hover {
  cursor: pointer;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity .15s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

#page-view {
  min-height: 798px;
}

@media (max-width: 900px) {
  nav {
    display: none;
  }

  section {
    padding-left: 0;
  }

  #header-section {
    position: fixed;
    width: 100%;
    z-index: 4;
  }

  #page-view {
    padding-top: 65px;
  }
}
</style>