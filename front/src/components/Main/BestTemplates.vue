<template>
  <div class="best-templates-wrapper">
    <div class="best-templates-intro">
      <div style="font-size:calc( 1rem + 1vw)">BEST Template TOP 6</div>
      <small style="font-size:calc(0.8rem + 0.1vw)">Coffy에서 인기 있는 BEST Template을 소개해드립니다.</small>
    </div>
    <div class="best-templates-section" v-if="!loading">
      <div v-for="code in bestCodes" :key="code.id" class="code-card">
        <div class="code-title">{{ code.title }}</div>
        <div class="code-preview">
          <div @click="goCodeDetail(code.id)" class="go-code-page"></div>
          <ApplyCode class='itembox' :idTag="`frame${code.id}`" :code="codeData(code.html, code.css, code.javascript)" />
        </div>
        <div class="info-wrapper">
          <div class="code-info">
            <div class="writer-info">
              <img src="https://user-images.githubusercontent.com/52685250/73902320-c72d6c00-48d8-11ea-82b4-eb9bfebfe9fb.png" alt="">
              <span>{{ code.writername }}</span>
            </div>
            <div class="code-like">
              <div class="like-info"><i class="fas fa-heart"></i> {{ code.likes }}</div>
            </div>
          </div>
        </div> 
      </div>
    </div>
    <div class="more-btn-wrapper" v-if="!loading">
      <div class="more-btn" @click="goCodeListPage">Show More</div>
    </div>
    <div class="spinner-section" v-else>
      <SpinnerLoading></SpinnerLoading>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchAllCode } from '@/api/code.js'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import ApplyCode from '@/components/Code/ApplyCode.vue'

export default {
  components: {
    SpinnerLoading,
    ApplyCode
  },
  data() {
    return {
      bestCodes: [],
      loading: false
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  created() {
    this.loading = true;
    this.getAllCodes();
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    async getAllCodes() {
      const { data } = await fetchAllCode();
      this.bestCodes = data.sort((a, b) => b.likes - a.likes).slice(0, 6);
      this.loading = false;
    },
    goCodeDetail(id) {
      this.$router.push(`/code/detail/${id}`);
    },
    goCodeListPage() {
      this.$router.push('/code');
    },
    codeData(html, css, js) {
      let code = {
        htmlText : html,
        cssText : css,
        jsText : js    
      }
      return code
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        document.querySelectorAll('.code-card').forEach(card => {
          card.style.borderColor = "#333"
        });
      } else { // 다크 모드일 때
        document.querySelectorAll('.code-card').forEach(card => {
          card.style.borderColor = "silver"
        });
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
.best-templates-intro {
  text-align: center;
}

.best-templates-intro > div {
  display: inline-block;
  font-size: 2rem;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 6px;
  margin-bottom: 8px;
  border-bottom: 1px solid silver;
}

.best-templates-intro > small {
  display: block;
  width: 90%;
  margin: 0 auto 20px;
}

.best-templates-section {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin: 0 auto;
  width: 90%;
}

.code-card {
  border: 1px solid silver;
  border-radius: 20px;
  padding: 12px;
  margin-bottom: 10px;
}

.code-card .code-title {
  display: inline-block;
  font-size: calc(1.3rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  padding-bottom: 4px;
  margin-bottom: 10px;
  border-bottom: 1px solid silver;
}

.code-preview {
  position: relative;
}

.go-code-page {
  position: absolute;
  width: 100%;
  height: 154px;
  background-color: transparent;
}

.code-preview:hover {
  cursor: pointer;
}

.code-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.writer-info {
  margin: 8px 0;
}

.writer-info img {
  vertical-align: middle;
  width: 35px;
  border-radius: 50%;
}

.writer-info > span {
  font-size: calc(0.7rem + 0.3vw);
}

.code-like {
  font-size: calc(0.5rem + 0.3vw);
  color: white;
}

.code-like > .like-info {
  margin-right: 6px;
}

.code-like > div[class$='-info'] {
  display: inline-block;
  background-color: #666;
  padding: 3px 6px;
  border-radius: 4px;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.itembox {
  background-color : #eee;
}

.more-btn-wrapper {
  text-align: center;
  color: black;
}

.more-btn {
  display: inline-block;
  font-size: calc(0.9rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  padding: 10px 20px;
  margin-top: 10px;
  border-radius: 8px;
  background-color: #03a9f4;
}

.more-btn:hover {
  cursor: pointer;
}

.spinner-section {
  position: relative;
}

@media (min-width: 600px) and (max-width: 960px) {
  .best-templates-section {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .best-templates-section {
    grid-template-columns: 1fr;
  }
}
</style>