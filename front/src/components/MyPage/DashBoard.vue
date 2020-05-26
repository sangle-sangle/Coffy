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
    <div class="dash-board-tab-contents">
      <div class="tab-content">
        <div v-if="currentTab == 0">
          0
        </div>
        <div v-if="currentTab == 1">
          1
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
		return {
			currentTab: 0, // 기본 currentTab 값 지정
			tabs: ['좋아요', '스크랩']
		}
	},
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
  padding: 10 0 15;
}

.active {
  background-color: #444857;
}
</style>
