<template>
  <div class="signup-wrapper">
    <div class="signup-box">
      <div class="signup-title">
        Sign Up
      </div>
      <div class="essential-message">
        (<i class="fas fa-star" style="color: crimson;"></i>표는 필수 입력 사항입니다.)
      </div>
      <form class="signup-form" @submit.prevent="submitForm">
        <div class="username-form">
          <label for="username">이름<i class="fas fa-star"></i></label>
          <input type="text" id="username" v-model="username">
          <div class="log-message" v-if="!isUserNameValid && clickSignupBtn">
            한글로만 작성해주세요.
          </div>
        </div>
        <div class="email-form">
          <label for="email">E-mail<i class="fas fa-star"></i></label>
          <input type="text" id="email" v-model="email">
          <div class="log-message" v-if="!isEmailValid && clickSignupBtn">
            이메일 양식으로 작성해주세요.
          </div>
        </div>
        <div class="password-form">
          <label for="password">비밀번호<i class="fas fa-star"></i></label>
          <input type="password" id="password" v-model="password">
          <div class="guideline">
            영어 소문자 4자 이상 + 숫자 4자 이상 조합해서 8자 이상으로 작성해주세요.
          </div>
          <div class="log-message" v-if="!isPasswordValid && clickSignupBtn">
            비밀번호 양식을 지켜서 작성해주세요.
          </div>
        </div>
        <div class="re-password-form">
          <label for="re-password">비밀번호 재확인<i class="fas fa-star"></i></label>
          <input type="password" id="re-password" v-model="rePassword">
          <div class="guideline">
            비밀번호를 한 번 더 작성해주세요.
          </div>
          <div class="log-message" v-if="!isRePasswordValid && clickSignupBtn">
            비밀번호가 일치하지 않거나 비밀번호 양식에 어긋납니다.
          </div>
        </div>
        <div class="github-form">
          <label for="github">Github 계정 연동</label>
          <div class="github-auth">
            <input type="text" id="github" v-model="githubid">
            <div class="auth-btn no-valid-github" @click="getGithubAuth" v-if="!checkGithubAuth">연동</div>
            <div class="auth-btn valid-github" @click="getGithubAuth" v-else>확인</div>
          </div>
          <div class="guideline">
            github ID를 입력한 후 우측 '연동' 버튼을 눌러주세요.
          </div>
        </div>
        <div class="lang-form">
          <label for="lang">주 사용 연어</label>
          <select name="lang" id="lang" v-model="mainLang">
            <option value="">주 사용 언어를 선택하세요.</option>
            <option value="HTML">HTML</option>
            <option value="CSS">CSS</option>
            <option value="Javascript">Javascript</option>
          </select>
        </div>
        <button class="signup-btn" type="submit">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { validateUserName } from '@/utils/validation/userNameValidation.js'
import { validateEmail } from '@/utils/validation/emailValidation.js'
import { validatePassword } from '@/utils/validation/passwordValidation.js'
import { registerUser } from '@/api/user.js'

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      rePassword: '',
      githubid: '',
      checkGithubAuth: false,
      mainLang: '',
      clickSignupBtn: false
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
    }),
    isUserNameValid() {
      return validateUserName(this.username)
    },
    isEmailValid() {
      return validateEmail(this.email)
    },
    isPasswordValid() {
      return validatePassword(this.password)
    },
    isRePasswordValid() {
      return validatePassword(this.rePassword) && this.password === this.rePassword
    },
  },
  methods: {
    async submitForm() { // 회원가입 로직 구현(try 부분에 구현)
      this.clickSignupBtn = true
      if (this.isUserNameValid && this.isEmailValid && this.isPasswordValid && this.isRePasswordValid) {
        try {
          // githubid는 github 연동이 된 경우(this.checkGithubAuth === true인 경우에만 등록)
          await registerUser({
            username: this.username,
            email: this.email,
            password: this.password,
            githubid: this.checkGithubAuth ? this.githubid : '',
            mainLang: this.mainLang
          });
          alert('회원가입 성공!')
          // 회원가입 성공 후 자동으로 로그인
          await this.$store.dispatch('LOGIN', {
            username: this.username,
            password: this.password
          });
          this.initForm();
          this.$router.push('/');
        } catch (error) {
          console.log(error)
        }
      }
    },
    getGithubAuth() { // github 계정 인증 (인증되면 checkGithubAuth 값이 false => true로 변경)
      // (1) 우선 DB에 입력한 github id가 있는지 확인(이미 DB에 있으면 다른 ID로 연동하라는 메세지 띄우기)
      // (2) 새롭게 등록하는 github id면 github에 해당 id 계정이 있는지 확인(없으면 해당 github 계정이 없다는 메세지 띄우기)
      // (3) 인증 거친 후 성공하면 checkGithubAuth 값을 false => true로 변경
    },
    initForm() {
      this.username = '';
      this.email = '';
      this.password = '';
      this.rePassword = '';
      this.githubid = '';
      this.checkGithubAuth = false;
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
        document.querySelector('select').style.backgroundColor = '#eee';
        document.querySelector('select').style.color = 'black';
      } else {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        });
        document.querySelector('select').style.backgroundColor = '#252830';
        document.querySelector('select').style.color = 'white';
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
.signup-wrapper {
  height: 100%;
}

.signup-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.signup-title {
  font-size: 26px;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

.essential-message {
  font-size: 14px;
  text-align: right;
  margin-bottom: 30px;
}

form > div[class$='-form'] {
  margin-bottom: 40px;
}

label > i {
  color: crimson;
  font-size: 14px;
  margin-left: 4px;
}

input,
select {
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

.guideline {
  font-size: 14px;
}

.log-message {
  color: rgb(243, 77, 110);
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

.github-auth {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.github-auth input {
  margin-bottom: 0;
}

.github-auth .auth-btn {
  font-size: 14px;
  font-weight: 600;
  display: inline-block;
  text-align: center;
  width: 50px;
  padding: 6px;
  margin-left: 4px;
  border-radius: 5px;
}

.github-auth .auth-btn.no-valid-github {
  background: linear-gradient(-180deg,#34d058,#28a745 90%);
}

.github-auth .no-valid-github:hover {
  cursor: pointer;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
}

.signup-btn {
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

@media (max-width: 600px) {
  .log-message {
    font-size: 14px;
  }
}
</style>