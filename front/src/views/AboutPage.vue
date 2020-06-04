<template>
  <div class="about">
    <h1>☕Coffy 홈페이지를 소개합니다.</h1>
    <ul class="description-list">
      <li class="description-title">Live Preview</li>
      <ul class="description-contents">
        <li>HTML, CSS, Javascript 언어로 제작한 템플릿의 결과물을 실시간으로 미리 볼 수 있습니다.</li>
      </ul>
      <li class="description-title">CSS Game</li>
      <ul class="description-contents">
        <li>간단한 게임을 통해서 CSS 관련 지식을 학습할 수 있습니다.</li>
        <li>flex, grid, transition 등 다양한 속성들을 익힐 수 있습니다.</li>
      </ul>
      <li class="description-title">Clan</li>
      <ul class="description-contents">
        <li>클랜 기능을 통해서 클랜원들과 함께 템플릿을 만들거나 게임을 만들어서 함께 즐길 수 있습니다.</li>
        <li>뿐만 아니라 프론트엔드 관련 지식을 지식 공유 게시판을 통해 클랜원들과 공유할 수 있습니다.</li>
      </ul>
      <li class="description-title">Dashboard</li>
      <ul class="description-contents">
        <li>내가 만들거나 스크랩한 템플릿을 한 곳에 모아서 볼 수 있습니다.</li>
      </ul>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AboutPage',
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    this.changeColor(this.mode);
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

<style scoped>
.about {
  padding: 15px;
}

h1 {
  display: inline-block;
  padding-bottom: 6px;
  margin-bottom: 20px;
  border-bottom: 1px solid #aaa;
}

.description-list {
  margin: 10px 0;
}

.description-title {
  display: inline-block;
  font-size: calc(1rem + 0.3vw);
  font-family: 'Gothic A1';
  padding-bottom: 6px;
  margin-bottom: 12px;
  border-bottom: 1px solid #aaa;
}

.description-contents {
  line-height: 1.6;
  margin-bottom: 20px;
}
</style>