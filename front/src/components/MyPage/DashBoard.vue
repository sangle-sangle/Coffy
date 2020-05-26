<template>
  <div class="dash-board-wrapper">
    <div class="dash-board-title">
      DashBoard
    </div>
    <div class="dash-board-header">
      <ul class="dash-board-tab-menu">
        <li :class="currentTab === index ? 'active' : ''" class="dash-board-tab-item" v-for="(tab, index) in tabs" :key="tab" @click="currentTab = index">
          <button>{{tab}}</button>
        </li>
      </ul>
    </div>
    <div class="filter-items-wrapper">
      <div>
        <input type="text" id="search" placeholder="Search keyword" v-model="keyword">
        <!-- <label for="search" @click="searchTemplate"><i class="fas fa-search"></i></label> -->
      </div>
      <div>

      </div>
    </div>
    <div class="dash-board-tab-contents">
      <div class="tab-content">
        <div v-if="currentTab == 0">
          좋아연
        </div>
        <div v-if="currentTab == 1">
          싫어연
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components:{
    
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  data() {
		return {
			currentTab: 0, // 현재 탭 위치
      tabs: ['좋아요', '스크랩'], // 탭 아이템 내용
      keyword: '',
		}
  },
  mounted() {
    this.$store.commit('toggleMode', 0);
    this.changeColor(this.mode)
  },
  methods: {
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        console.log('white')
      } else { // 다크 모드일 때
        console.log('dark')
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

<style>
.dash-board-title {
  font-size: 2em;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.dash-board-wrapper {
  height: 100vh;
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
}

.dash-board-tab-menu {
  padding-bottom: 10px;
}

.dash-board-tab-item {
  display: inline-block;
  padding: 0.5rem 0.3rem
}

.active {
  background-color: #444857;
}
</style>
