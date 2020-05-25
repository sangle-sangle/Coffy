<template>
  <div class="best-templates-wrapper">
    <div class="best-templates-intro">
      <div>BEST Template TOP 20</div>
      <small>Coffy에서 인기 있는 BEST Template을 소개해드립니다.</small>
    </div>
    <!-- template card component 생성 후 구성 예정 -->
    <div class="best-templates-section">

    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.best-templates-intro {
  text-align: center;
}

.best-templates-intro > div {
  display: inline-block;
  font-size: 2rem;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 6px;
  margin-bottom: 8px;
  border-bottom: 1px solid silver;
}

.best-templates-intro > small {
  display: block;
  margin-bottom: 20px;
}

.best-templates-section {
  display: flex;
  justify-content: center;
  margin: 0 auto;
  width: 90%;
}
</style>