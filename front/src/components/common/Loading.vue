<template>
  <div class="loading-wrapper">
    <i class="far fa-clock"></i>
    <p>데이터를 불러오는 중입니다.</p>
    <p>조금만 기다려주세요.</p>
  </div>
</template>

<style scoped>
.loading-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 70vh;
  text-align: center;
}

i {
  font-size: calc(6rem + 1vw);
  margin-bottom: 10px;
}

p {
  font-size: calc(0.7rem + 0.4vw);
  line-height: 1.6;
}
</style>