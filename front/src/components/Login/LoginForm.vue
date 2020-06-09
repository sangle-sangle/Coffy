<template>
  <div class="login-wrapper">
    <div class="login-box">
      <div class="login-title">
        Login
      </div>
      <form class="login-form" @submit.prevent="submitForm">
        <div class="email-form">
          <label for="email"></label>
          <input id="email" v-model="userName" type="text" placeholder="ID">
          <div class="log-message">
            (E-mail로 작성해주세요.)
          </div>
        </div>
        <div class="password-form">
          <label for="password"></label>
          <input id="password" v-model="password" type="password" placeholder="Password">
        </div>
        <div class="btn-wrapper">
          <button type="submit" class="log-btn">로그인</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { githubLogin } from '@/api/user.js'

export default {
  data() {
    return {
      userName: '',
      password: ''
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      nextPageUrl: state => state.common.goNextPage
    })
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    async submitForm() {
      if (!this.userName) {
        alert('ID(E-mail)를 작성하세요.')
        return
      } else if (!this.password) {
        alert('패스워드를 작성하세요.')
        return
      }
      try {
        const response = await this.$store.dispatch('LOGIN', {
          email: this.userName,
          password: this.password
        })
        if (response.status === 204) {
          alert('비밀번호를 다시 입력해주세요.')
          return
        }
        this.initForm()
        this.$router.push('/');
      } catch (error) {
        if(error.status === 500) {
          alert('등록되지 않은 계정입니다.')
        }
      }
    },
    async socialLogin() {
      await githubLogin()
    },
    initForm() {
      this.userName = ''
      this.password = ''
    },
    goFindAccount() {
      this.$router.push('/login/findaccount')
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
      } else {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        });
        document.querySelectorAll('button').forEach(buttonTag => {
          buttonTag.style.color = 'white'
        })
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
.login-wrapper {
  height: 100vh;
}

.login-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.login-title {
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

.log-message {
  margin-bottom: 20px;
}

.btn-wrapper {
  margin: 30px 0;
}

.log-btn {
  width: 100%;
  font-size: 15px;
  background-color: #8003f4;
  padding: 8px 0;
}
</style>