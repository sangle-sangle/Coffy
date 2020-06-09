<template>
  <div class="code-list-wrapper">
    <div class="code-list">
      <div v-for="code in codeList" :key="code.id" class="code-card">
        <div class="code-preview">
          <div @click="goCodeDetail(code.id)" class="go-code-page"></div>
          <ApplyCode class='itembox' :idTag="`frame${code.id}`" :code="codeData(code.html, code.css, code.javascript)" />
        </div>
        <div class="info-wrapper" v-if="!loading">
          <div class="code-info">
            <div class="writer-info">
              <img
                src="https://user-images.githubusercontent.com/52685250/73902320-c72d6c00-48d8-11ea-82b4-eb9bfebfe9fb.png"
                alt="no-image">
              <span>{{ code.writername }}</span>
            </div>
            <div class="code-like">
              <div class="like-info"><i class="fas fa-heart"></i> {{ code.likes }}</div>
            </div>
          </div>
          <div class="detail-btn" @click="goCodeEditPage(code.id)">
            <i class="fas fa-edit"></i> 코드 수정
          </div>
        </div>
      </div>
    </div>
    <Pagination :itemCount="this.$store.state.code.codeData.length" @setNowPage="setNowPage" v-if="!loading"></Pagination>
    <SpinnerLoading v-else></SpinnerLoading>
  </div>
</template>

<script>
import ApplyCode from '@/components/Code/ApplyCode.vue'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import Pagination from '@/components/common/Pagination.vue'

export default {
  components: {
    ApplyCode,
    SpinnerLoading,
    Pagination,
  },
  props: {
    codeList: Array,
  },
  data() {
    return {
      loading: false,
      nowPage: 1
    }
  },
  methods: {
    codeData(html, css, js) {
      let code = {
        htmlText : html,
        cssText : css,
        jsText : js    
      }
      return code
    },
    goAddCodePage() {
      this.$router.push('/code/form');
    },
    goGamePage() {
      this.$router.push('/game');
    },
    goCodeDetail(id) {
      this.$router.push(`/code/detail/${id}`);
    },
    goCodeEditPage(id) {
      this.$router.push(`/code/edit/${id}`);
    },
    setNowPage(pageNm) {
      let allCodes = this.$store.state.code.codeData;
      this.nowPage = pageNm;
      this.codeList = allCodes.slice(12 * (this.nowPage - 1), 12 * this.nowPage);
      this.loading = false;
    },
  }
}
</script>

<style>
.code-list-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.code-list-title {
  display: inline-block;
  font-size: calc(1.2rem + .5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.code-list-description {
  font-size: calc(0.7rem + 0.3vw);
}

.code-list-header-right > div {
  display: inline-block;
}

.add-code-btn,
.go-game-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  text-align: center;
  padding: 10px;
  border-radius: 8px;
}

.add-code-btn {
  background-color: #47cf73;
  margin-right: 8px;
}

.go-game-btn {
  background-color: #5793e2;
}

.add-code-btn:hover,
.go-game-btn:hover {
  cursor: pointer;
}

.code-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
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

.info-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
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

.detail-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  background-color: #f7c389;
  color: black;
  padding: 4px 6px;
  border-radius: 6px;
}

.detail-btn:hover {
  cursor: pointer;
}

.itembox {
  background-color : #eee;
}

@media (min-width: 600px) and (max-width: 960px) {
  .code-list {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .code-list-header {
    display: block;
  }

  .code-list {
    grid-template-columns: 1fr;
  }

  .code-list-header-right > div {
    display: block;
    margin: 16px 0;
    padding: 10px;
  }

  .add-code-btn {
    margin-right: 0;
  }
}
</style>