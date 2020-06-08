<template>
  <div class="dashboard-wrapper">
    <div class="dashboard-header">
      <div class="dashboard-header-left">
        <div class="dashboard-title">
          Dashboard
        </div>
        <div class="dashboard-description">
          ☕좋아요, 스크랩한 코드들을 볼 수 있습니다.
        </div>
      </div>
    </div>
    <div class="dashboard-main">
      <div class="dashboard-main-header">
        <ul class="dashboard-tab-menu">
          <li :class="sortProperty.currentTab === index ? mode === 'white'?'active-tab-white':'active-tab':''" class="dashboard-tab-item" v-for="(tab, index) in tabs" :key="tab" @click="sortProperty.currentTab = index">
            {{tab}}
          </li>
        </ul>
        <div class="filter-items-wrapper">
          <div class="filter-left">
            <div class="filter-item filter-search">
              <div class="filter-search-bar">
                <input type="text" class="filter-search-input" placeholder="Search keyword" v-model="sortProperty.keyword">
              </div>
              <button class="filter-search-button"><i class="fas fa-search"></i></button>
            </div>
          </div>
          <div class="filter-right">
            <div class="filter-item filter-menu">
              <span class="filter-sort-method span-items">정렬 방식</span>
              <div class="dropdown-menu">
                <span class="filter-drop-button">{{menuItems[sortProperty.menuIndex]}}</span>
                <div class="dropdown-contents">
                  <li class="d" v-for="(item, index) in menuItems" :key="index" @click="sortProperty.menuIndex=index">{{item}}</li>
                </div>
              </div>
            </div>
            <div class="filter-item filter-sort">
              <span class="filter-sort-direction span-items">정렬 방향</span>
              <div class="sort_button_group">
                <button class="filter-sort-button" :class="sortProperty.filterButton === false ? 'active-sort' : ''" @click="sortProperty.filterButton = false">
                  <i class="fas fa-chevron-up"></i>
                </button>
                <button class="filter-sort-button" :class="sortProperty.filterButton === true ? 'active-sort' : ''" @click="sortProperty.filterButton = true">
                  <i class="fas fa-chevron-down"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="dash-board-tab-contents">
      <div class="tab-content">
        <CodeList :codeList = sortedData />
      </div>
    </div>
  </div>
</template>

<script>
import CodeList from '@/components/MyPage/CodeList.vue'
import { mapState } from 'vuex'
import { getMyCode, getLikedCode } from '@/api/code.js'

export default {
  components:{
    CodeList,
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  data() {
		return {
      sortProperty:{
        currentTab: 0, // 현재 탭 위치
        keyword: '', // 검색 키워드
        filterButton: false, // 오름차순/내림차순
        menuIndex: 0, // 정렬방식
      },
      tabs: ['찜한 템플릿', '내가 만든 템플릿'], // 탭 아이템 내용
      menuItems: ['작성일자순', '이름순', '인기순', ],
      initMyCode: [],
      sortedData: [],
		}
  },
  created() {
    this.getCodeData();
  },
  mounted() {
    this.changeColor(this.mode);
    window.onclick = function(event) {
      let dropDown = document.getElementsByClassName("dropdown-contents")[0]
      if (event.target.matches('.dropdown-menu') | event.target.matches('.filter-sort-method') | event.target.matches('.filter-drop-button')){
        dropDown.classList.toggle('active-dropdown')
      }
      else {
        if (dropDown && dropDown.classList.contains('active-dropdown')) {
          dropDown.classList.remove('active-dropdown');
        }
      }
    };
  },
  methods: {
    changeColor(mode) {
      let activeTab = document.querySelector('.active-tab')
      let filterWrapper = document.querySelector('.filter-items-wrapper')
      let filterSearchInput = document.querySelector('.filter-search-input')
      let filterSort = document.querySelector('.filter-sort')
      let filterMenu = document.querySelector('.filter-menu')
      let filterSearchButton = document.querySelector('.filter-search-button')
      let spanItems = document.querySelectorAll('.span-items')
      if (mode === 'white') { // 화이트 모드일 때
        if (activeTab){
          activeTab.classList.remove('active-tab')
          activeTab.classList.add('active-tab-white')
        }
        filterWrapper.style.backgroundColor = '#0d0d0f42'
        filterSearchInput.style.backgroundColor = 'rgb(238, 238, 238)'
        filterSort.style.backgroundColor = 'rgb(238, 238, 238)'
        filterMenu.style.backgroundColor = 'rgb(238, 238, 238)'
        filterSearchInput.style.color = 'black'
        filterSearchButton.style.backgroundColor = 'rgb(212, 212, 212)'
        spanItems.forEach(span=> {span.style.color = '#717790'})
      } else { // 다크 모드일 때
        if (activeTab){
          activeTab.classList.remove('active-tab-white')
          activeTab.classList.add('active-tab-black')
        }
        filterWrapper.style.backgroundColor = '#444857'
        filterSearchInput.style.backgroundColor = 'rgb(37, 40, 48)'
        filterSort.style.backgroundColor = 'rgb(37, 40, 48)'
        filterMenu.style.backgroundColor = 'rgb(37, 40, 48)'
        filterSearchInput.style.color = 'white'
        filterSearchButton.style.backgroundColor = 'rgb(30, 30, 34)'
        spanItems.forEach(span=> {span.style.color = '#717790'})
      }
    },
    async getCodeData(){
      const myCode = await getMyCode()
      const likedCode = await getLikedCode()
      this.initMyCode = [likedCode.data, myCode.data, ]
      this.setFilter()
    },
    setFilter(){
      this.sortedData = []
      if (this.initMyCode.length === 0)
        return
      const sortingIndex = ["created_at", "title", "likes"]
      const notFilteredItems = this.initMyCode[this.sortProperty.currentTab]
      const idx = sortingIndex[this.sortProperty.menuIndex]
      const acending = this.sortProperty.filterButton
      if (notFilteredItems.length > 1){
        notFilteredItems.sort(function(a,b) {
          if (acending)
            return a[idx] < b[idx] ? -1 : a[idx] > b[idx] ? 1 : 0;
          else
            return a[idx] > b[idx] ? -1 : a[idx] < b[idx] ? 1 : 0;
        })
      }
      const keyword = this.sortProperty.keyword.toLowerCase()
      for (var i=0; i<notFilteredItems.length;i++){
        if (notFilteredItems[i].description.includes(keyword) || notFilteredItems[i].title.includes(keyword) || notFilteredItems[i].writername.includes(keyword) )
          this.sortedData.push(notFilteredItems[i])
      }
    },
  },
  watch: {
    mode() {
      this.changeColor(this.mode)
    },
    sortProperty:{
      deep:true,
      immediate:true,
      handler:"setFilter"
    },
  }
}
</script>

<style>
.dashboard-wrapper {
  padding:15px;
}
.dashboard-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.dashboard-title {
  display: inline-block;
  font-size: calc( 2rem + 0.5vw );
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.dashboard-tab-item {
  display: inline-block;
  font-size: calc( 0.7rem + 0.3vw );
  font-weight: bolder;
  padding: 0.6rem 1rem
}

.active-tab {
  background-color: #444857;
}

.active-tab-white {
  background-color: #0d0d0f42;
}

.filter-items-wrapper {
  display: flex;
  flex-wrap: wrap;
  padding:0.5rem 0.75rem;
}

.filter-left {
  flex-grow: 1;
  margin: 4px 0;
  max-width: 620px;
  min-width: 320px;
  margin-right: 15px;
}

.filter-right {
  display: flex;
  width: 300px;
  flex-grow: 1;
  margin: 4px 0;
}

.filter-item {
  display: flex;
  flex-flow: wrap;
}

.filter-search-bar {
  flex-grow: 1;
  height: 35px;
  width: 75%;
  max-width: 700px;
}

.filter-search-input {
  height: 35px;
  width: calc(100% - 20px);
  border: 0px;
  padding: 0px 10px;
  font-size: calc(0.6rem + 0.3vw);
}

.filter-search-button {
  height: 35px;
  width: 50px;
  border: transparent;
  padding: 0px 10px;
  line-height: 25px;
  font-size: calc(0.6rem + 0.3vw);
}

.filter-sort {
  background-color: rgb(37, 40, 48);
  height: 35px;
  width: calc(4rem + 1vw + 80px);
}

.span-items {
  padding: 5px 10px;
  line-height: 25px;
  font-size: calc(0.6rem + 0.3vw);
  border: transparent;
  font-weight: bolder;
  width: calc(4rem + 1vw);
}

.sort_button_group {
  width: 67.35px;
  height: 35px;
  padding: 5px;
}

.filter-sort-button {
  height: 25px;
  background-color: #9498a7;
  font-weight: bold;
}

.active-sort {
  background-color: #646877;
}

.filter-menu {
  background-color: rgb(37, 40, 48);
  height: 35px;
  margin-right: 15px;
  width: calc(9rem + 2vw + 10px);
}

.dropdown-menu{
  position: relative;
  width: calc(5rem + 1vw);
}

.filter-drop-button{
  line-height: 35px;
  padding-left: 10px;
  text-align: center;
  font-size: calc(0.6rem + 0.3vw);
  font-weight: 300;
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
  font-size: calc(0.6rem + 0.3vw);
}

.dropdown-contents li:hover {background-color: #ddd}

@media (max-width: 750px) {
  .filter-items-wrapper {
    display: block;
  }

  .filter-right {
    display: flex;
    width: 100%;
  }
}
</style>