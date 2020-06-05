<template>
  <div class="clan-detail-wrapper">
    <div class="clan-detail-title">
      Clan Information
    </div>
    <div v-if="!loading">
      <div class="clan-detail-header">
        <div class="clan-header-left">
          <div class="clan-mark">
            <img src="../../assets/images/clan/clan_mark_no_image.png" alt="clan_no_mark" v-if="!clanInfo.clanmark">
            <img :src="clanInfo.clanmark" alt="clan_no_mark" v-else>
          </div>
          <div class="clan-short-info">
            <div class="clan-name">{{ clanInfo.name }}</div>
            <div class="clan-info-tab">
              <span class="clan-master-tag">Master</span>
              <span class="clan-master">{{ clanInfo.mastername }}</span>
              <span class="clan-point-tag">Points</span>
              <span class="clan-point">{{ clanInfo.clanpoint }}</span>
            </div>
            <div class="clan-btn-group">
              <div class="clan-master" @click="toggleMasterSection" v-if="this.userInfo['access-Token'].id === clanInfo.leaderId"><i class="fas fa-tools"></i> 관리자 모드</div>
              <div class="clan-register" @click="toggleClanRegisterModal" v-if="this.userInfo['access-Token'].id !== clanInfo.leaderId && !checkMyClan"><i class="fas fa-plus"></i> 클랜 가입</div>
              <div class="clan-sign-out" @click="toggleClanSignOutModal" v-if="this.userInfo['access-Token'].id !== clanInfo.leaderId && checkMyClan"><i class="fas fa-minus"></i> 클랜 탈퇴</div>
            </div>
          </div>
        </div>
      </div>
      <div class="clan-master-section" v-show="showClanMaster">
        <span class="clan-master-tab" @click="toggleMasterTab(0)">클랜 관리</span>
        <span class="clan-master-tab" @click="toggleMasterTab(1)">유저 리스트</span>
        <div class="clan-info-edit">
          <div v-show="masterTab === 0" class="edit-clan-info">
            <div>
              <div class="edit-clan-info-title">클랜 정보 수정</div>
              <small>하단 버튼을 클릭하면 클랜 정보를 수정할 수 있습니다.</small>
              <button @click="goEditPage(clanId)">수정 페이지로 이동</button>
            </div>
            <div>
              <div class="edit-clan-info-title">클랜 삭제</div>
              <small>하단 버튼을 클릭하면 클랜을 삭제할 수 있습니다.</small>
              <button @click="toggleClanDeleteModal">클랜 삭제</button>
            </div>
          </div>
          <div v-show="masterTab === 1">
            이 곳에 유저 리스트 보여줄 계획
          </div>
        </div>
      </div>
      <div class="clan-notice" v-if="checkMyClan">
        <div class="notice-icon"><i class="fas fa-list"></i> 게시판</div>
        <div class="notice">
          {{ clanInfo.name }} 클랜의 게시판 영역
        </div>
      </div>
      <div class="clan-description">
        <div class="description-icon"><i class="fas fa-list"></i> 설명</div>
        <div class="description">
          {{ clanInfo.description !== '' ? clanInfo.description : '내용 없음' }}
        </div>
      </div>
    </div>
    <div class="spinner-section" v-else>
      <SpinnerLoading></SpinnerLoading>
    </div>
    <Modal v-if="showModal">
      <ClanRegisterModal v-if="showClanRegisterModal" :clanInfo="clanInfo" @closeModal="toggleClanRegisterModal"></ClanRegisterModal>
      <ClanSignOutModal v-if="showClanSignOutModal" :clanInfo="clanInfo" @closeModal="toggleClanSignOutModal"></ClanSignOutModal>
      <ClanDeleteModal v-if="showClanDeleteModal" :clanInfo="clanInfo" @closeModal="toggleClanDeleteModal"></ClanDeleteModal>
    </Modal>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchClanInfo } from '@/api/clan.js'
import Modal from '@/components/common/Modal.vue'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import ClanRegisterModal from '@/components/Clan/ClanRegisterModal.vue'
import ClanSignOutModal from '@/components/Clan/ClanSignOutModal.vue'
import ClanDeleteModal from '@/components/Clan/ClanDeleteModal.vue'

export default {
  components: {
    Modal,
    SpinnerLoading,
    ClanRegisterModal,
    ClanSignOutModal,
    ClanDeleteModal
  },
  data() {
    return {
      clanId: Number(this.$route.path.split('/').reverse()[0]),
      clanInfo: {},
      showClanMaster: false,
      showModal: false,
      showClanRegisterModal: false,
      showClanSignOutModal: false,
      showClanDeleteModal: false,
      masterTab: 0,
      checkMyClan: false,
      loading: false,
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    })
  },
  created() {
    this.getClanInfo()
  },
  mounted() {
    this.fixButtonTextColor()
  },
  methods: {
    async getClanInfo() {
      this.loading = true;
      let getClanData = await fetchClanInfo(this.clanId);
      this.clanInfo = getClanData.data.clan;
      this.checkMyClan = getClanData.data.myclan;
      this.loading = false;
    },
    toggleMasterSection() {
      this.showClanMaster = !this.showClanMaster
    },
    toggleClanRegisterModal() {
      this.showClanRegisterModal = !this.showClanRegisterModal;
      this.showModal = !this.showModal;
    },
    toggleClanSignOutModal() {
      this.showClanSignOutModal = !this.showClanSignOutModal;
      this.showModal = !this.showModal;
    },
    toggleClanDeleteModal() {
      this.showClanDeleteModal = !this.showClanDeleteModal;
      this.showModal = !this.showModal;
    },
    toggleMasterTab(val) {
      this.masterTab = val
    },
    goEditPage(clanId) {
      this.$router.push(`/clan/edit/${clanId}`)
    },
    fixButtonTextColor() {
      document.querySelectorAll('button').forEach(buttonTag => {
        buttonTag.style.color = 'white';
      })
    }
  },
  watch: {
    mode() {
      this.fixButtonTextColor()
    }
  }
}
</script>

<style scoped>
.clan-detail-title {
  display: inline-block;
  font-size: calc(2rem + 0.5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.clan-detail-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.clan-header-left {
  display: flex;
  align-items: center;
  font-family: 'Gothic A1';
}

.clan-header-left img {
  width: 150px;
  vertical-align: top;
  margin-right: 10px;
}

.clan-name {
  font-size: calc(1.3rem + 0.3vw);
  font-weight: 600;
  margin: 10px 0 15px;
}

.clan-info-tab {
  font-size: calc(0.75rem + 0.3vw);
  margin-bottom: 16px;
}

.clan-info-tab > span {
  background-color: #777;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  color: #eee;
}

.clan-info-tab span[class$='-tag'] {
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  padding: 6px 3px 6px 10px;
}

.clan-info-tab span:not([class$='-tag']) {
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  margin-right: 10px;
  padding: 6px 10px 6px 3px;
}

.clan-btn-group > .clan-master,
.clan-btn-group > .clan-register,
.clan-btn-group > .clan-sign-out {
  display: inline-block;
  margin-right: 6px;
  font-size: calc(0.6rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  padding: 6px;
  color: black;
  border-radius: 8px;
}

.clan-btn-group > div:hover,
.clan-master-tab:hover {
  cursor: pointer;
}

.clan-btn-group > .clan-master {
  background-color: orange;
}

.clan-btn-group > .clan-register {
  background-color: #ffdd40;
}

.clan-btn-group > .clan-sign-out {
  background-color: orangered;
  color: white;
}

.clan-master-section {
  padding: 10px;
  border: 1px solid silver;
  margin-bottom: 20px;
}

.clan-master-tab {
  display: inline-block;
  padding: 4px 0;
  margin: 0 4px 8px 4px;
  border-bottom: 1.5px solid transparent;
  transition: all .2s;
}

.clan-master-tab:hover {
  border-bottom: 1.5px solid silver;
}

.edit-clan-info {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  padding: 4px;
}

.edit-clan-info small {
  display: block;
}

.edit-clan-info-title {
  display: inline-block;
  font-size: 18px;
  font-weight: 600;
  font-family: 'Gothic A1';
  padding-bottom: 4px;
  margin-bottom: 12px;
  border-bottom: 1px solid silver;
}

.edit-clan-info-title + small:before {
  content: '📌 ';
}

.edit-clan-info > div > button {
  display: block;
  margin: 0.75rem auto;
  padding: 8px 12px;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 600;
  font-family: 'Gothic A1';
  letter-spacing: 0.8px;
}

.edit-clan-info > div:first-child > button {
  background-color: #43474b;
}

.edit-clan-info > div:last-child > button {
  background: #cb2431;
}

.clan-description,
.clan-notice {
  margin-bottom: 20px;
}

.clan-description > .description-icon,
.clan-notice > .notice-icon {
  display: inline-block;
  border: 1px solid silver;
  border-bottom: transparent;
  padding: 10px 14px;
}

.clan-description > .description,
.clan-notice > .notice {
  border: 1px solid silver;
  padding: 10px 14px;
  font-size: calc(0.6rem + 0.3vw);
  line-height: 1.7;
}

.spinner-section {
  position: relative;
}

@media (max-width: 600px) {
  .clan-detail-header,
  .clan-header-left {
    display: block;
  }

  .clan-mark img {
    display: block;
    margin: 0 auto;
    width: 200px;
  }

  .clan-short-info {
    text-align: center;
  }

  .clan-btn-group > .clan-master,
  .clan-btn-group > .clan-register,
  .clan-btn-group > .clan-sign-out {
    display: block;
    margin: 0 auto 10px;
    padding: 10px 6px;
    font-size: 12px;
    width: 100%;
  }

  .clan-description .description {
    font-size: 11.5px;
  }

  .edit-clan-info {
    display: block;
    padding-top: 16px;
  }

  .edit-clan-info > div:first-child {
    margin-bottom: 30px;
  }
}
</style>