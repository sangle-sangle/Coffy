<template>
  <div class="clan-sign-out-modal-wrapper">
    <div class="modal-header">
      <div class="clan-modal-title">클랜 탈퇴</div>
      <div class="modal-close-btn" @click="closeModal">CLOSE</div>
    </div>
    <div class="message">
      <p class="sign-out-message">'{{ this.clanInfo.name }}' 클랜에서 탈퇴하시겠습니까?</p>
      <p class="sign-out-message">('클랜 탈퇴' 버튼을 클릭하면 Clan 리스트 페이지로 이동합니다.)</p>
    </div>
    <div class="sign-out-btn-wrapper">
      <div class="sign-out-btn">
        <span @click="clanSignOut">클랜 탈퇴</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { signOutClan } from '@/api/clan.js'

export default {
  props: {
    clanInfo: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    })
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    closeModal() {
      this.$emit('closeModal')
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('.clan-sign-out-modal-wrapper').style.backgroundColor = '#eee';
      } else {
        document.querySelector('.clan-sign-out-modal-wrapper').style.backgroundColor = '#2c303a';
      }
    },
    async clanSignOut() {
      const paramsData = {
        clan_id: this.clanInfo.id,
        user_id: this.userInfo['access-Token'].id
      }
      await signOutClan(paramsData);
      sessionStorage.setItem('myClanId', 0);
      this.$router.push('/clan');
    }
  },
  watch: {
    mode() {
      this.changeColor(this.mode);
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
  font-size: 22px;
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
  font-size: 14px;
  font-weight: 600;
  color: goldenrod;
}

.sign-out-btn-wrapper {
  text-align: right;
}

.sign-out-btn > span {
  font-size: 15px;
  font-weight: 600;
  color: black;
  border-radius: 8px;
  padding: 4px 6px;
  background-color: orangered;
}

.sign-out-btn > span:hover {
  cursor: pointer;
}
</style>