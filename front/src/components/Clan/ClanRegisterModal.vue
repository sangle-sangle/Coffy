<template>
  <div class="clan-register-modal-wrapper">
    <div class="modal-header">
      <div class="clan-modal-title">클랜 가입</div>
      <div class="modal-close-btn" @click="closeModal">CLOSE</div>
    </div>
    <div v-if="!registerStatus" class="message">
      <p class="register-message">'{{ this.clanInfo.name }}' 클랜({{ privateStatus }})에 가입하시겠습니까?</p>
      <p v-if="this.clanInfo.locked" class="waiting-message">(비공개 클랜은 신청 후 클랜장의 승인을 기다려야합니다.)</p>
    </div>
    <div v-else class="message private-clan">
      <p class="register-message" v-if="!this.clanInfo.locked">{{ this.clanInfo.name }} 클랜에 가입되었습니다.</p>
      <p class="register-message" v-else>{{ this.clanInfo.name }} 클랜 가입 신청되었습니다.<br>클랜장의 승인을 기다려주세요.</p>
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
// import { registerClan } from '@/api/clan.js'

export default {
  props: {
    clanInfo: {
      type: Object
    }
  },
  data() {
    return {
      registerStatus: false
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    }),
    privateStatus() {
      return this.clanInfo.locked ? '비공개' : '공개'
    }
  },
  mounted() {
    // this.$store.commit('toggleMode');
    this.changeColor(this.mode);
  },
  methods: {
    closeModal() {
      this.$emit('closeModal')
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('.clan-register-modal-wrapper').style.backgroundColor = '#eee';
      } else {
        document.querySelector('.clan-register-modal-wrapper').style.backgroundColor = '#2c303a';
      }
    },
    async clanRegister() {
      this.registerStatus = true;
      // 하단에 await문으로 실제 클랜 가입 신청 로직 작성
      console.log(this.clanInfo.id);
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