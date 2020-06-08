<template>
  <div class="notfound-wrapper">
    <div class="notfound-mark">
      <i class="fas fa-exclamation-triangle d-block"></i>
    </div>
    <div class="alert-message">
      <p>이 페이지는 없는 페이지입니다.</p>
    </div>
    <div class="alter-intro">
      <p><span class="link" @click="goMain">(여기)</span>를 누르면 메인 페이지로 이동합니다.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound',
  methods: {
    goMain() {
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.notfound-wrapper {
  height: 100vh;
  padding: 30px 0;
}

.notfound-mark {
  text-align: center;
  height: 110px;
  padding: 20px 0;
  animation: emphasis 1s linear infinite alternate;
}

.notfound-mark > .fas {
  font-size: 110px;
  color: crimson;
  text-align: center;
}

.alert-message p {
  font-size: 22px;
  font-weight: bold;
  font-family: 'Nanum Gothic';
  color: crimson;
  padding-top: 40px;
  margin: 30px 0;
  text-align: center;
}

.alter-intro p {
  font-size: 15px;
  font-weight: 600;
  font-family: 'Gothic A1';
  text-align: center;
}

.alter-intro .link {
  font-size: 17px;
  font-weight: 700;
  color: blue;
  text-decoration: underline;
}

.alter-intro .link:hover {
  cursor: pointer;
}

@keyframes emphasis {
  from {
    transform: scale(1);
  }

  to {
    transform: scale(1.15);
  }
}
</style>