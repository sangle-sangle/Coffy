<template>
  <div class="find-account-wrapper">
    <div class="find-account-box">
      <div class="find-account-title">
        Find Password
      </div>
      <form class="find-account-form" @submit.prevent="submitForm">
        <div class="username-form">
          <label for="username"></label>
          <input id="username" v-model="username" type="text" placeholder="이름">
        </div>
        <div class="email-form">
          <label for="email"></label>
          <input id="email" v-model="email" type="text" placeholder="E-mail">
        </div>
        <div class="message">
          작성하신 E-mail 주소로 임시 비밀번호를 발급해드립니다.
        </div>
        <button type="submit">비밀번호 찾기</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      email: ''
    }
  },
  methods: {
    submitForm() {
      
    }
  }
}
</script>

<style scoped>
.find-account-wrapper {
  height: 100vh;
}

.find-account-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.find-account-title {
  font-size: 26px;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

input {
  color: white;
  width: 100%;
  background-color: #252830;
  padding: 10px 5px;
  border: transparent;
  border-radius: 15px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
  margin-bottom: 10px;
  margin-left: -5px;
  font-size: 18px;
}

button {
  font-size: 18px;
  font-weight: 600;
  display: block;
  width: 100%;
  margin: 2rem auto;
  text-align: center;
  background-color: #47cf73;
  font-size: 17px;
  padding: 8px;
}
</style>