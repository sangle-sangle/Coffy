<template>
  <div class="dash-board-wrapper">
    <div class="dash-board-title">
      DashBoard
    </div>
    <div class="dash-board-header">
      <ul class="dash-board-tab-menu">
        <li :class="currentTab === index ? 'active-tab' : ''" class="dash-board-tab-item" v-for="(tab, index) in tabs" :key="tab" @click="currentTab = index">
          <button>{{tab}}</button>
        </li>
      </ul>
    </div>
    <div class="filter-items-wrapper">
      
      <div class="filter-item filter-search" style="margin-bottom:10px;">
        <div class="filter-search-bar">
          <input type="text" class="filter-search-input" placeholder="Search keyword" v-model="keyword">
        </div>
        <button class="filter-search-button">검색</button>
      </div>

      <div class="filter-item filter-sort">
        <span class="filter-sort-direction span-items">정렬 방향</span>
        <div class="sort_button_group">
          <button class="filter-sort-button" :class="filterButton === false ? 'active-sort' : ''" @click="filterButton = false">
            <i class="fas fa-chevron-up"></i>
          </button>
          <button class="filter-sort-button" :class="filterButton === true ? 'active-sort' : ''" @click="filterButton = true">
            <i class="fas fa-chevron-down"></i>
          </button>
        </div>
      </div>

      <div class="filter-item filter-menu">
        <span class="filter-sort-method span-items">정렬 방식</span>
        <div class="dropdown-menu">
          <span class="filter-drop-button span-items">{{menuItems[menuIndex]}}</span>
          <div class="dropdown-contents">
            <li class="d" v-for="(item, index) in menuItems" :key="index" @click="menuIndex=index">{{item}}</li>
          </div>
        </div>
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
      filterButton: false,
      filterMenu: false,
      menuItems: ['작성일자순', '이름순', '인기순', ],
      menuIndex: 0
		}
  },
  mounted() {
    this.$store.commit('toggleMode', 0);
    this.changeColor(this.mode);
    window.onclick = function(event) {
      console.log(event.target)
      var dropdowns = document.getElementsByClassName("filter-menu");
      if (event.target.matches('.dropdown-menu') | event.target.matches('.filter-sort-method') | event.target.matches('.filter-drop-button')){
        document.getElementsByClassName("dropdown-contents")[0].classList.toggle('active-dropdown')
      }
      else {
        var dropdowns = document.getElementsByClassName("dropdown-contents");

        if (dropdowns[0].classList.contains('active-dropdown')) {
          dropdowns[0].classList.remove('active-dropdown');
        }
      }
    }
  },
  methods: {
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        console.log('white')
      } else { // 다크 모드일 때
        console.log('dark')
      }
    },
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

.dash-board-tab-item {
  display: inline-block;
  padding: 0.5rem 0.3rem
}

.active-tab {
  background-color: #444857;
}

.filter-items-wrapper {
  display: flex;
  flex-flow: row wrap;
  background-color: #444857;
  padding:0.5rem;
}

.filter-item {
  display: flex;
  flex-flow: wrap;
  margin-right: 10px;
}

.filter-search {
  width: 80%;
  max-width: 430px;
}

.filter-search-bar {
  height: 35px;
  width: 75%;
  background-color: rgb(37, 40, 48);
}

.filter-search-input {
  height: 35px;
  width: calc(100% - 20px);
  border: 0px;
  padding: 0px 10px;
  background-color: rgb(37, 40, 48);
  color: #eee;
}

.filter-search-button {
  height: 35px;
  width: 25%;
  border: transparent;
  background-color: rgb(30, 30, 34);
  color: #eee;
}

.filter-sort {
  background-color: rgb(37, 40, 48);
  width: 25%;
  height: 35px;
  max-width: 170px;
  margin-right: 11px;
}

.filter-sort-direction {
  width: calc(100% - 57.35px);
}

.span-items {
  padding: 5px 10px;
  line-height: 25px;
  font-size: 13.3333px;
  border: transparent;
  color: #eee;
}

.sort_button_group {
  width: 57.35px;
  height: 35px;
  padding: 5px;
}

.filter-sort-button {
  height: 25px;
  background-color: #9498a7;
}

.active-sort {
  background-color: #646877;
}

.filter-menu {
  background-color: rgb(37, 40, 48);
  width: 35%;
  height: 35px;
  margin-right: 11px;
}

.filter-sort-method {
  width: calc(100% - 82.66px);
}

.dropdown-menu{
  position: relative;
  width: 82.66px;
  padding-top:5px;
}

.dropdown-contents {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  width: 100%;

  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.active-dropdown {
  display: block;
}

.dropdown:hover .dropdown-content {display: block;}

.dropdown-contents li {
  color: black;
  padding: 5px 10px;
  display: block;
}

.dropdown-contents li:hover {background-color: #ddd}
@media (max-width: 600px) {
  .filter-item {
    width: 100%;
    margin-right: 0px;
  }
}
</style>
