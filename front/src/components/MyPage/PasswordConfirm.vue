<template>
  <div class="password-confirm-wrapper">
    <div class="password-confirm-box">
      <div class="password-confirm-title">
        비밀번호 확인
      </div>
      <div class="password-confirm-description">
        개인정보 조회를 위해서는 인증이 필요합니다.
        <br>
        비밀번호 입력후 확인 버튼을 눌러주세요.
      </div>
      <div class="password-confirm-form">
        <div class="password-form">
          <label for="password"></label>
          <input id="password" v-model="password" type="password" placeholder="Password">
        </div>
        <button class="password-confirm-button" @click="submitForm">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      password: ''
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
    })
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    async submitForm() {
      if (!this.password) {
        alert('패스워드를 입력해주세요.')
        return
      }
      try {
        const response = await this.$store.dispatch('PASSWORDCONFIRM', {
          email: this.$store.state.user.userInfo['access-Token'].email,
          password: this.password
        })
        if (response.status === 204) {
          alert('비밀번호를 다시 입력해주세요.')
          return
        }
        this.initForm()

        this.$router.push('/mypage/account')
      } catch (error) {
        if(error.status === 500) {
          alert('잘못된 접근입니다.')
        }
      }
    },
    initForm() {
      this.password = ''
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
        document.querySelectorAll('button').forEach(buttonTag => {
          buttonTag.style.backgroundColor = '#eee'
          buttonTag.style.color = 'black'
        });
      } else {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        });
        document.querySelectorAll('button').forEach(buttonTag => {
          buttonTag.style.backgroundColor = '#252830'
          buttonTag.style.color = 'white'
        });
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
.password-confirm-wrapper {
  height: 100vh;
}

.password-confirm-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.password-confirm-title {
  font-size: 26px;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.password-confirm-description {
  font-size: 20px;
  font-family: 'Noto Sans KR';
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

.password-confirm-button {
  font-size: 18px;
  font-weight: 600;
  display: block;
  width: 100%;
  margin: 2rem auto;
  text-align: center;
  font-size: 17px;
  padding: 8px;
  border-radius: 15px;
}
</style>