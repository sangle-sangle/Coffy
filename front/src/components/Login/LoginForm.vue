<template>
  <div class="login-wrapper">
    <div class="login-box">
      <div class="login-title">
        Login
      </div>
      <div class="login-form">
        <div class="email-form">
          <label for="email"></label>
          <input id="email" v-model="username" type="text" placeholder="ID">
          <div class="log-message" v-if="!(isEmailValid || isUsernameValid)">
            (이름 또는 이메일 양식으로 입력하세요.)
          </div>
        </div>
        <div class="password-form">
          <label for="password"></label>
          <input id="password" v-model="password" type="password" placeholder="Password">
        </div>
        <div class="btn-group">
          <button class="log-btn" @click="submitForm">로그인</button>
          <button class="find-pw-btn" @click="goFindAccount">비밀번호 찾기</button>
        </div>
      </div>
      <div class="social-login-box">
        <div class="social-message">Github 계정만 있어도 이용 가능합니다.</div>
        <div class="social-btn-group">
          <button class="github" @click="githubLogin">
            <i class="fab fa-github"></i>
            <p>Github 로그인</p>
          </button>
          <!-- <button class="kakao">
            <img src="../../assets/images/social_icon/kakao-talk.png" alt="kakao-btn">
            <p>카카오톡 로그인</p>
          </button>
          <button class="google">
            <img src="../../assets/images/social_icon/google.png" alt="google">
            <p>구글 로그인</p>
          </button> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { validateUserName } from '@/utils/validation/userNameValidation.js'
import { validateEmail } from '@/utils/validation/emailValidation.js'

export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
    }),
    isEmailValid() {
      return validateEmail(this.username)
    },
    isUsernameValid() {
      return validateUserName(this.username)
    }
  },
  methods: {
    async submitForm() {
      if (!this.username) {
        alert('이름 또는 이메일을 작성하세요.')
        return
      } else if (!this.password) {
        alert('패스워드를 작성하세요.')
        return
      }
      try {
        await this.$store.dispatch('LOGIN', {
          username: this.username,
          password: this.password
        })
        this.initForm()
        this.$router.push('/')
      } catch (error) {
        let errorMessage = ''
        if (error.status === 400) {
          errorMessage = '아이디와 비밀번호를 다시 확인하세요.'
        } else if(error.status === 500) {
          errorMessage = '등록되지 않은 아이디입니다.'
        } else {
          errorMessage = '예기치 못한 오류가 발생했습니다. 관리자에게 문의해주세요.'
        }
        alert(errorMessage)
      }
    },
    githubLogin() {
      
    },
    initForm() {
      this.username = ''
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

.btn-group {
  margin: 10px 0;
  text-align: center;
}

.btn-group > button {
  font-size: 15px;
  padding: 0 5px;
  margin: 0 5px;
}

.social-login-box {
  margin: 40px 0 20px;
  text-align: center;
}

.social-login-box > .social-message {
  margin-bottom: 10px
}

.social-btn-group > button {
  display: inline-flex;
  align-items: center;
  padding: 6px;
  border: 0.5px solid silver;
  border-radius: 10px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.6);
}

.social-btn-group > button > i {
  font-size: 30px;
  margin-right: 8px;
}

.social-btn-group > button:not(:first-child) {
  margin-left: 10px;
}

.social-btn-group > button > img {
  height: 45px;
  margin-right: 6px;
}
</style>