<template>
  <div class="code-list-wrapper">
    <div class="code-list-header">
      <div class="code-list-header-left">
        <div class="code-list-title">
          Code List
        </div>
        <div class="code-list-description">
          Coffy 사이트의 유저들이 만든 템플릿을 볼 수 있습니다.
        </div>
      </div>
      <div class="code-list-header-right">
        <div class="add-code-btn" @click="goAddCodePage"><i class="fas fa-plus"></i> 코드 작성</div>
        <div class="go-game-btn" @click="goGamePage"><i class="fas fa-gamepad"></i> 코드 게임</div>
      </div>
    </div>
    <div class="code-list" v-if="!loading">
      <div v-for="code in codeList" :key="code.id" class="code-card">
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
          <div class="detail-btn" @click="goCodeEditPage(code.id)" v-if="isLogin && code.writerid === userInfo['access-Token'].id">
            <i class="fas fa-edit"></i> 코드 수정
          </div>
        </div>
      </div>
    </div>
    <Pagination :itemCount="this.$store.state.code.codeData.length" @setNowPage="setNowPage" v-if="!loading"></Pagination>
    <div class="spinner-section" v-else>
      <SpinnerLoading></SpinnerLoading>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchAllCode } from '@/api/code.js'
import ApplyCode from '@/components/Code/ApplyCode.vue'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import Pagination from '@/components/common/Pagination.vue'

export default {
  components: {
    ApplyCode,
    SpinnerLoading,
    Pagination,
  },
  data() {
    return {
      codeList: [],
      loading: false,
      nowPage: 1
    }
  },
  computed: {
    ...mapState({
      isLogin: state => state.user.isLogin,
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    })
  },
  created() {
    this.loading = true;
    this.getAllCode();
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    async getAllCode() {
      const { data } = await fetchAllCode();
      this.$store.commit('saveCodeData', data);
      this.setNowPage(1);
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
    codeData(html, css, js) {
      let code = {
        htmlText : html,
        cssText : css,
        jsText : js    
      }
      return code
    },
    setNowPage(pageNm) {
      let allCodes = this.$store.state.code.codeData;
      this.nowPage = pageNm;
      this.codeList = allCodes.slice(12 * (this.nowPage - 1), 12 * this.nowPage);
      this.loading = false;
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
  font-size: calc(0.65rem + 0.3vw);
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
  background-color: #03a9f4;
  margin-right: 8px;
}

.go-game-btn {
  background-color: #8003f4;
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
  display: -webkit-box;
  font-size: calc(.9rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  padding-bottom: 4px;
  margin-bottom: 10px;
  border-bottom: 1px solid silver;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  line-height: 1.5em;
  height: 1.5em;
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
  margin-left: 8px;
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
  float: right;
  font-size: calc(0.7rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  background-color: #03a9f4;
  color: white;
  padding: 4px 6px;
  border-radius: 6px;
}

.detail-btn:hover {
  cursor: pointer;
}

.itembox {
  background-color : #eee;
}

.spinner-section {
  position: relative;
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