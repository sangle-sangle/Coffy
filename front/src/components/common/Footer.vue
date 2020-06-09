<template>
  <div class="footer-wrapper">
    <div class="left-footer">
      <span @click="goIntroPage('service-intro')">Service Introduction</span>
      <span @click="goIntroPage('team-intro')">Team Introduction</span>
    </div>
    <div class="right-footer">☕Coffy</div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    setTimeout(() => this.changeColor(this.mode), 250);
  },
  methods: {
    goIntroPage(idVal) {
      this.$router.push(`/about#${idVal}`).catch(()=>{})
      if (idVal === 'service-intro') {
        window.scrollTo(0, 0);
      }
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('.footer-wrapper').style.backgroundColor = '#eee';
      } else {
        document.querySelector('.footer-wrapper').style.backgroundColor = '#000';
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

<style scoped>
.footer-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 80px 20px 20px;
}

.left-footer > span {
  font-size: 14px;
  padding-right: 10px;
  margin-right: 10px;
}

.left-footer > span:hover {
  font-weight: 600;
  cursor: pointer;
}

.right-footer {
  font-size: 1.8rem;
  font-family: 'Jua';
}

@media (max-width: 600px) {
  .left-footer > span {
    display: block;
    font-size: 13px;
    padding-bottom: 10px;
  }
}
</style>