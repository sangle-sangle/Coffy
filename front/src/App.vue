<template>
  <div id="app">
    <nav>
      <SideBar></SideBar>
    </nav>
    <section>
      <Header id="header-section"></Header>
      <router-view id="page-view" />
    </section>
  </div>
</template>

<script>
import SideBar from '@/components/common/SideBar.vue';
import Header from '@/components/common/Header.vue';
import '@/assets/css/default.css';

export default {
  name: 'App',
  components: {
    SideBar,
    Header
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

#page-view {
  background-color: rgb(30, 30, 34);
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