<template>
  <div class="game-list-wrapper">
    <div class="game-list-header">
      <div class="game-list-header-left">
        <div class="game-list-title">
          Game List
        </div>
        <div class="code-list-description">
          <p>CSS 관련 지식들을 Game으로 익혀보아요.</p>
          <div v-if="!$store.state.user.isLogin">
           게임을 진행하려면 로그인 후에 진행해 주세요
          </div>
          <p> 카테고리의 문제를 단계별로 풀 수 있습니다</p>
        </div>
      </div>
    </div>
    <div class="game-set" v-for="(category,index) in categorys" :key="category.id">
      <div class="game-category">{{ `${indexIcons[index]} ${category.title}` }}</div>
      <div class="game-description">{{ category.description }}</div>
      <div class="game-button-set">
        <div :class="{solved: gameNum<=$store.state.user.solved[index] }"  class="game-button" v-for="gameNum in category.game_cnt" :key="gameNum" @click="goGamePage(category.title, gameNum)">
          GAME {{ gameNum }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCategorys } from '@/api/game'

export default {
  data() {
    return {
      solved : [],
      categorys : [],
      indexIcons: ['1️⃣', '2️⃣', '3️⃣', '4️⃣', '5️⃣', '6️⃣', '7️⃣', '8️⃣', '9️⃣', '🔟'],
      categories: ['flex', 'text', 'grid', 'transition', 'animation', 'selector'],
    }
  },
  mounted() {
    this.fetchCategory()
  },
  methods: {
    async fetchCategory(){
      let data = await getCategorys()
      this.categorys = data.data
    },
    goGamePage(category, gameNum) {
      this.$router.push(`game/${category}/${gameNum}`)
    }
  }
}
</script>

<style scoped>
.game-list-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.game-list-title {
  display: inline-block;
  font-size: calc(1.2rem + .5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.game-description
.game-list-description {
  font-size: calc(0.6rem + 0.3vw);
}

.game-set {
  margin-bottom: 60px;
}

.game-category {
  font-size: calc(1rem + .5vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  margin-bottom: 8px;
}

.solved{
  background: linear-gradient(90deg,#9ff797,#a8ff8e 90%)!important;
}
.game-button-set {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 15px;
}

.game-button {
  font-size: 18px;
  font-weight: 600;
  text-align: center;
  color: black;
  background: linear-gradient(90deg,#a0aaa3,#8b8b8b 90%);
  border-radius: 8px;
  padding: 18px 24px;
  width: 90%;
  margin: 20px auto 0;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.game-button:hover {
  cursor: pointer;
  background: linear-gradient(90deg,#dde0de,#c5c5c5 90%);
}

@media (min-width: 600px) and (max-width: 960px) {
  .game-button-set {
    grid-template-columns: repeat(3, 1fr);
    gap: 12px;
  }
}

@media (max-width: 600px) {
  .game-button-set {
    font-size: 17px;
    grid-template-columns: repeat(2, 1fr);
    gap: 12px;
  }
}
</style>