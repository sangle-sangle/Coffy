<template>
  <div class="provide-lang-wrapper">
    <div class="provide-lang-intro">
      <div style="font-size:calc(1rem + .5vw)">지원하는 프로그래밍 언어</div>
      <small style="font-size:calc(0.8rem + 0.1vw)">프론트엔드에서 자주 쓰는 HTML, CSS, Javascript 언어들을 지원합니다.</small>
    </div>
    <div class="provide-lang-section">
      <div class="lang-card" v-for="language in languages" :key="language">
        <img :src="imgUrl(language)" :alt="`${language}`">
        <p class="lang-card-text">{{ language }}</p>
      </div>
    </div>
  </div>  
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ProvideLang',
  data() {
    return {
      languages: ['HTML', 'CSS', 'Javascript']
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
    })
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    imgUrl(lang) {
      return require(`../../assets/images/mainpage/${lang.toLowerCase()}.png`)
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('.lang-card').forEach(card => {
          card.style.backgroundColor = '#eee'
        })
        document.querySelectorAll('.lang-card-text').forEach(cardText => {
          cardText.style.color = 'black'
        })
      } else {
        document.querySelectorAll('.lang-card').forEach(card => {
          card.style.backgroundColor = '#3a3e49'
        })
        document.querySelectorAll('.lang-card-text').forEach(cardText => {
          cardText.style.color = 'white'
        })
      }
    }
  },
  watch: {
    mode() {
      this.changeColor(this.mode);
    }
  }
}
</script>

<style scoped>
.provide-lang-intro {
  text-align: center;
}

.provide-lang-intro > div {
  display: inline-block;
  font-size: 2rem;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 6px;
  margin-bottom: 8px;
  border-bottom: 1px solid silver;
}

.provide-lang-intro > small {
  display: block;
  width: 90%;
  margin: 0 auto 20px;
}

.provide-lang-section {
  display: flex;
  justify-content: center;
  margin: 0 auto;
  width: 90%;
}

.lang-card {
  margin: 0 30px;
  padding: 15px 30px;
  background-color: #3a3e49;
  border-radius: 20px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.2);
}

.lang-card > img {
  display: block;
  margin: 0 auto;
  width: 170px;
}

.lang-card > p {
  margin-top: 12px;
  font-size: 1.3rem;
  font-family: 'Gothic A1';
  font-weight: 600;
  text-align: center;
}

@media (max-width: 700px) {
  .provide-lang-section {
    display: grid;
    row-gap: 40px;
    grid-template-columns: 1fr;
  }

  .lang-card > img {
    width: 150px;
  }
}
</style>