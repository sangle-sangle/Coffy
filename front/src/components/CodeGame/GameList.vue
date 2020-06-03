<template>
  <div class="game-list-wrapper">
    <div class="game-list-header">
      <div class="game-list-header-left">
        <div class="game-list-title">
          Game List
        </div>
        <div class="code-list-description">
          ⌨️CSS 관련 지식들을 Game으로 익혀보아요.
        </div>
      </div>
    </div>
    <div class="game-set" v-for="(category, index) in categories" :key="index">
      <div class="game-category">{{ `${indexIcons[index]} ${category}` }}</div>
      <div class="description">📌{{ description[index] }}</div>
      <div class="game-button-set">
        <div class="game-button" v-for="gameNum in 6" :key="gameNum" @click="goGamePage(category, gameNum)">
          GAME {{ gameNum }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      indexIcons: ['1️⃣', '2️⃣', '3️⃣', '4️⃣', '5️⃣', '6️⃣', '7️⃣', '8️⃣', '9️⃣', '🔟'],
      categories: ['flex', 'text', 'grid', 'transition', 'animation', 'selector'],
      description: [
        'flex와 관련된 속성들을 익혀보아요.',
        'CSS로 text를 꾸미는 여러 가지 기법들을 익혀보아요.',
        'grid와 관련된 속성들을 익혀보아요.',
        'transition와 관련된 속성들을 익혀보아요.',
        'animation와 관련된 속성들을 익혀보아요.',
        '다양한 선택자들을 익혀보아요.',
      ]
    }
  },
  methods: {
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
  font-size: 2em;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.game-list-description {
  font-size: 13.5px;
}

.game-set {
  margin-bottom: 60px;
}

.game-category {
  font-size: 24px;
  font-weight: 600;
  font-family: 'Gothic A1';
  margin-bottom: 8px;
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