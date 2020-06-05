<template>
  <div class="clan-delete-modal-wrapper">
    <div class="modal-header">
      <div class="clan-modal-title">클랜 삭제</div>
      <div class="modal-close-btn" @click="closeModal">CLOSE</div>
    </div>
    <div class="message">
      <p class="delete-message">'{{ this.clanInfo.name }}' 클랜을 정말로 삭제하시겠습니까?</p>
      <p class="delete-message">('클랜 삭제' 버튼을 클릭하면 Clan 리스트 페이지로 이동합니다.)</p>
      <p class="warning-message">하단 '클랜 삭제' 버튼을 클릭하면 해당 클랜의 정보를 복구할 수 없습니다.</p>
    </div>
    <div class="delete-btn-wrapper">
      <div class="delete-btn">
        <span @click="clanDelete">클랜 삭제</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { deleteClan } from '@/api/clan.js'

export default {
  props: {
    clanInfo: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
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
        document.querySelector('.clan-delete-modal-wrapper').style.backgroundColor = '#eee';
      } else {
        document.querySelector('.clan-delete-modal-wrapper').style.backgroundColor = '#2c303a';
      }
    },
    async clanDelete() {
      await deleteClan(this.clanInfo.id);
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
  margin: 14px 0 20px;
  border-top: 1px solid silver;
  border-bottom: 1px solid silver;
}

.message > p {
  line-height: 1.5;
}

.message > p.warning-message {
  font-size: 17px;
  font-weight: 600;
  color: yellow;
  margin-top: 8px;
}

.delete-btn-wrapper {
  text-align: right;
}

.delete-btn > span {
  font-size: 15px;
  font-weight: 600;
  color: white;
  border-radius: 8px;
  padding: 6px 8px;
  background-color: crimson;
}

.delete-btn > span:hover {
  cursor: pointer;
}
</style>