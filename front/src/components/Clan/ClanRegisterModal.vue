<template>
  <div class="clan-register-modal-wrapper">
    <div class="modal-header">
      <div class="clan-modal-title">클랜 가입</div>
      <div class="modal-close-btn" @click="closeModal">CLOSE</div>
    </div>
    <div v-if="!registerStatus" class="message">
      <p class="register-message">'{{ this.clanInfo.name }}' 클랜({{ privateStatus }})에 가입하시겠습니까?</p>
      <p v-if="this.clanInfo.locked" class="waiting-message">(비공개 클랜은 클랜 비밀번호를 입력해야 가입할 수 있습니다.)</p>
      <input v-if="this.clanInfo.locked" type="password" id="clan-pwd" v-model="password" placeholder="클랜 비밀번호를 입력하세요.">
    </div>
    <div v-else class="message private-clan">
      <p class="register-message">{{ this.clanInfo.name }} 클랜에 가입되었습니다.</p>
    </div>
    <div class="register-btn-wrapper">
      <div class="register-btn">
        <span v-if="!registerStatus" @click="clanRegister">클랜 가입</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { registerClan } from '@/api/clan.js'

export default {
  props: {
    clanInfo: {
      type: Object
    }
  },
  data() {
    return {
      registerStatus: false,
      password: null
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    }),
    privateStatus() {
      return this.clanInfo.locked ? '비공개' : '공개'
    }
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    closeModal() {
      this.$emit('closeModal');
      if (this.registerStatus) {
        sessionStorage.setItem('myClanId', this.clanInfo.id);
        this.$router.push(`/clan/detail/${this.clanInfo.id}`)
        .catch(()=>{})
      }
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('.clan-register-modal-wrapper').style.backgroundColor = '#eee';
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
      } else {
        document.querySelector('.clan-register-modal-wrapper').style.backgroundColor = '#2c303a';
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        });
      }
    },
    async clanRegister() {
      if (this.clanInfo.locked && (this.password !== this.clanInfo.password)) {
        alert('비밀번호를 틀렸습니다. 다시 입력해주세요.');
        return
      }
      this.registerStatus = true;
      const paramsData = {
        clan_id: this.clanInfo.id,
        pwd: this.password,
        user_id: this.userInfo['access-Token'].id
      }
      await registerClan(paramsData);
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
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: 'Noto Sans KR';
}

.modal-header .clan-modal-title {
  font-size: calc(1.3rem + 0.3vw);
  font-weight: 600;
}

.modal-close-btn:hover {
  cursor: pointer;
}

.message {
  padding: 18px 0;
  margin: 14px 0;
  border-top: 1px solid silver;
  border-bottom: 1px solid silver;
}

.message > p {
  line-height: 1.5;
}

.message > p.waiting-message {
  font-weight: 600;
  color: goldenrod;
}

.message > input {
  box-sizing: border-box;
  color: white;
  width: 100%;
  background-color: #252830;
  padding: 10px 5px;
  border: transparent;
  border-radius: 15px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
  margin-top: 15px;
  font-size: 15px;
}

.register-btn-wrapper {
  text-align: right;
}

.register-btn > span {
  font-size: calc(0.7rem + 0.3vw);
  font-weight: 600;
  color: black;
  border-radius: 8px;
  padding: 4px 6px;
  background-color: #ffdd40;
}

.register-btn > span:hover {
  cursor: pointer;
}
</style>