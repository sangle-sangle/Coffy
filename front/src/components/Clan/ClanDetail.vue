<template>
  <div class="clan-detail-wrapper">
    <div class="clan-detail-title">
      Clan Information
    </div>
    <div class="clan-detail-header">
      <div class="clan-header-left">
        <div class="clan-mark">
          <img src="../../assets/images/clan/clan_mark_no_image.png" alt="clan_no_mark">
        </div>
        <div class="clan-short-info">
          <div class="clan-name">{{ clanInfo.name }}</div>
          <div class="clan-master">Master : {{ clanInfo.leaderid }}</div>
          <div class="clan-btn-group">
            <div class="clan-master" @click="toggleMasterSection"><i class="fas fa-tools"></i> 관리자 모드</div>
            <div class="clan-register" @click="toggleClanRegisterModal"><i class="fas fa-plus"></i> 클랜 가입</div>
          </div>
        </div>
      </div>
    </div>
    <div class="clan-master-section" v-show="showClanMaster">
      클랜 관리자만 볼 수 있는 영역
    </div>
    <div class="clan-description">
      <div class="description-icon"><i class="fas fa-list"></i>설명</div>
      <div class="description">
        {{ clanInfo.description !== '' ? clanInfo.description : '내용 없음' }}
      </div>
    </div>
    <Modal :showModal="showClanRegisterModal">
      <ClanRegisterModal :clanInfo="clanInfo" @closeModal="toggleClanRegisterModal"></ClanRegisterModal>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue'
import clanList from '@/assets/json/sampleClanList.json'
import ClanRegisterModal from '@/components/Clan/ClanRegisterModal.vue'
// import { fetchClanInfo } from '@/api/clan.js'

export default {
  components: {
    Modal,
    ClanRegisterModal
  },
  data() {
    return {
      clanId: this.$route.path.split('/').reverse()[0],
      clanInfo: {},
      showClanMaster: false,
      showClanRegisterModal: false
    }
  },
  created() {
    this.getClanInfo()
  },
  methods: {
    async getClanInfo() { // 해당 클랜 정보 받아오는 로직 작성
      // let getClanData = await fetchClanInfo(this.clanid)
      // this.clanInfo = getClanData.data

      // 우선 지금은 임시로 asset에 json 파일로 만든 데이터 사용
      this.clanInfo = clanList[this.clanId]
    },
    toggleMasterSection() {
      this.showClanMaster = !this.showClanMaster
    },
    toggleClanRegisterModal() {
      this.showClanRegisterModal = !this.showClanRegisterModal;
    }
  }
}
</script>

<style scoped>
.clan-detail-wrapper {
  height: 100vh;
}

.clan-detail-title {
  display: inline-block;
  font-size: 2em;
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
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 10px;
}

.clan-header-left .clan-master {
  font-size: 1.1rem;
  margin-bottom: 10px;
}

.clan-btn-group > .clan-master,
.clan-btn-group > .clan-register {
  display: inline-block;
  margin-right: 6px;
  font-size: 14px;
  font-family: 'Gothic A1';
  font-weight: 600;
  padding: 6px;
  color: black;
  border-radius: 8px;
}

.clan-btn-group > div:hover {
  cursor: pointer;
}

.clan-btn-group > .clan-master {
  background-color: orange;
}

.clan-btn-group > .clan-register {
  background-color: #ffdd40;
}

.clan-master-section {
  padding: 10px;
  border: 1px solid silver;
  margin-bottom: 20px;
}

.clan-description > .description-icon {
  display: inline-block;
  border: 1px solid silver;
  border-bottom: transparent;
  padding: 10px;
}

.clan-description > .description {
  border: 1px solid silver;
  padding: 10px;
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
  .clan-btn-group > .clan-register {
    display: block;
    margin: 0 auto 10px;
    width: 100%;
  }
}
</style>