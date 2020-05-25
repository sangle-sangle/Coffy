<template>
  <div class="footer-wrapper">
    <div class="left-footer">
      <span>Service Introduction</span>
      <span>Team Introduction</span>
    </div>
    <div class="right-footer">☕Coffy</div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.footer-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
}

.left-footer > span {
  font-size: 14px;
  padding-right: 10px;
  margin-right: 10px;
}

.right-footer {
  font-size: 1.8rem;
  font-family: 'Jua';
}

@media (max-width: 600px) {
  .left-footer > span {
    display: block;
    font-size: 13px;
    padding-bottom: 10px;
  }
}
</style>