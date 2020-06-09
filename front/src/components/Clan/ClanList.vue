<template>
  <div class="clan-main-wrapper">
    <div class="clan-main-header">
      <div class="clan-main-header-left">
        <div class="clan-main-title">
          Clan List
        </div>
        <div class="clan-main-description">
          ☕Coffy 사이트의 클랜 리스트를 볼 수 있습니다.
        </div>
      </div>
      <div class="clan-main-header-right" v-if="!loading">
        <div class="my-clan-btn" v-if="myClanId !== 0" @click="goMyClan">내 클랜 페이지로 이동</div>
        <div class="add-clan-btn" v-else @click="goAddForm"><i class="fas fa-plus"></i> 클랜 생성</div>
      </div>
    </div>
    <div class="clan-search">
      <form class="search-box" @submit.prevent="searchClan">
        <input type="text" id="search-clan" placeholder="Search Clan Name" v-model="keyword">
        <label for="search-clan" @click="searchClan"><i class="fas fa-search"></i></label>
      </form>
    </div>
    <div class="clan-list" v-if="!loading">
      <div v-for="(clan, idx) in clanList" :key="idx" class="clan-card">
        <div class="clan-header">
          <div v-if="!clan.locked" class="clan-private open"><i class="fas fa-lock-open"></i>공개</div>
          <div v-else class="clan-private private"><i class="fas fa-lock"></i>비공개</div>
          <div class="clan-master">Master: {{ clan.mastername }}</div>
        </div>
        <div class="clan-card-info">
          <div class="clan-mark">
            <img src="../../assets/images/clan/clan_mark_no_image.png" alt="clan_no_mark" v-if="!clan.clanmark">
            <img :src="clan.clanmark" alt="clan_no_mark" v-else>
          </div>
          <div class="clan-info">
            <div class="clan-name">
              <span>{{ clan.name }}</span>
            </div>
            <div class="clan-footer">
              <div class="clan-detail-btn" @click="goClanDetail(clan.id)"><i class="fas fa-info"></i> 상세보기</div>
              <div class="clan-register-btn" @click="showClanRegister(idx)" v-if="myClanId === 0"><i class="fas fa-plus"></i> 클랜 가입</div>
              <div class="my-clan-sign" v-if="clan.id === Number(myClanId)">My Clan</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Pagination :itemCount="this.$store.state.clan.clanData.length" @setNowPage="setNowPage" v-if="!loading"></Pagination>
    <div class="spinner-section" v-else>
      <SpinnerLoading></SpinnerLoading>
    </div>
    <Modal v-if="showClanRegisterModal">
      <ClanRegisterModal :clanInfo="clanList[registerClanId]" @closeModal="closeModal"></ClanRegisterModal>
    </Modal>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchAllClans } from '@/api/clan.js'
import { fetchMyInfo } from '@/api/user.js'
import Modal from '@/components/common/Modal.vue';
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import Pagination from '@/components/common/Pagination.vue'
import ClanRegisterModal from '@/components/Clan/ClanRegisterModal.vue'

export default {
  components: {
    Modal,
    SpinnerLoading,
    Pagination,
    ClanRegisterModal,
  },
  data() {
    return {
      clanList: [],
      keyword: '',
      showClanRegisterModal: false,
      registerClanId: null,
      loading: false,
      nowPage: 1,
      myClanId: 0
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    })
  },
  created() {
    this.loading = true;
    this.getAllClans();
    this.getMyClanId();
  },
  mounted() {
    this.changeColor(this.mode);
  },
  methods: {
    async getAllClans() {
      const { data } = await fetchAllClans();
      this.$store.commit('saveClanData', data);
      this.setNowPage(1);
    },
    async getMyClanId() {
      const { data } = await fetchMyInfo(this.userInfo['access-Token'].id);
      this.myClanId = data.clanid;
    },
    searchClan() {
      if (this.keyword) {
        alert(`추후 ${this.keyword}를 포함하는 클랜 리스트 보여줄 예정`)
      } else {
        alert('키워드를 입력해주세요.')
      }
    },
    goAddForm() {
      this.$router.push('/clan/addform');
    },
    goClanDetail(clanId) {
      this.$router.push(`/clan/detail/${clanId}`);
    },
    showClanRegister(idx) {
      this.registerClanId = idx;
      this.showClanRegisterModal = true;
    },
    closeModal() {
      this.showClanRegisterModal = false;
    },
    setNowPage(pageNm) {
      let allClans = this.$store.state.clan.clanData;
      this.nowPage = pageNm;
      this.clanList = allClans.slice(12 * (this.nowPage - 1), 12 * this.nowPage);
      this.loading = false;
    },
    goMyClan() {
      this.$router.push(`/clan/detail/${this.myClanId}`);
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        document.querySelector('#search-clan').style.backgroundColor = '#eee';
        document.querySelectorAll('.clan-card').forEach(card => {
          card.style.borderColor = "#333"
        });
      } else { // 다크 모드일 때
        document.querySelector('#search-clan').style.backgroundColor = '#252830';
        document.querySelectorAll('.clan-card').forEach(card => {
          card.style.borderColor = "silver"
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
.clan-main-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.my-clan-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  text-align: center;
  padding: 10px;
  border-radius: 8px;
  background-color: #03a9f4;
}

.clan-main-title {
  display: inline-block;
  font-size: calc( 1.2rem + 0.5vw );
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.clan-main-description {
  font-size: calc(0.65rem + 0.3vw);
}

.clan-search,
.clan-name {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  align-items: center;
  margin-bottom: 20px;
}

.clan-name {
  display: inline-block;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-wrap: normal;
  width: 100%;
  overflow: hidden;
}

.search-box {
  display: flex;
  align-items: center;
  flex-grow: 1;
}

.search-box > input {
  font-size: calc(0.8rem + 0.3vw);
  height: calc(1.2rem + 0.3vw);
  width: 100%;
  padding: calc(0.3rem + 0.3vw) calc(0.7rem + 0.3vw);
  border: transparent;
  border-radius: 10px;
  background-color: rgb(37, 40, 48);
  color: #eee;
  margin-right: 8px;
}

.clan-main-header-right {
  margin-top: auto;
  margin-bottom: auto;
}

.add-clan-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  text-align: center;
  padding: 10px;
  border-radius: 8px;
  background-color: #03a9f4;
  margin-left:8px;
}

.my-clan-btn:hover,
.add-clan-btn:hover {
  cursor: pointer;
}

@media (max-width: 600px) {
  .clan-main-header {
    display: block;
    width: 100%;
  }
  
  .add-clan-btn {
    margin-top: 20px;
  }
}

.clan-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.clan-card {
  border: 1px solid silver;
  border-radius: 20px;
  padding: 12px;
  margin-bottom: 10px;
}

.clan-private {
  display: inline-block;
  font-size: calc(0.6rem + 0.3vw);
  font-weight: 600;
  padding: 6px;
  border-radius: 8px;
}

.clan-private.private {
  background-color: #8003f4;
}

.clan-private.open {
  background-color: #03a9f4;
}

.clan-name {
  font-size: calc(1.3rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  padding-bottom: 5px;
  margin-bottom: 10px;
  border-bottom: 1px solid silver;
}

.clan-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.clan-card-info {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.clan-card-info .clan-mark {
  display: flex;
  align-items: center;
  margin-right: 5px;
}

.clan-info {
  flex-grow: 1;
}

.clan-card-info img {
  width: 65px;
  height: 65px;
  vertical-align: top;
}

.clan-footer {
  display: flex;
}

.clan-footer > .clan-register-btn,
.clan-footer > .clan-detail-btn,
.clan-footer > .my-clan-sign {
  font-size: calc(0.6rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  padding: 6px;
  color: black;
  border-radius: 8px;
  margin-left: 4px;
  letter-spacing: -1px;
}

.clan-register-btn {
  background-color: #03a9f4;
}

.clan-detail-btn {
  background-color: #f09ced;
}

.my-clan-sign {
  background-color: #03a9f4;
}

.clan-footer > .clan-detail-btn:hover,
.clan-footer > .clan-register-btn:hover {
  cursor: pointer;
}

.spinner-section {
  position: relative;
}

@media (min-width: 600px) and (max-width: 960px) {
  .clan-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .clan-main-header-right {
    margin-top: 10px;
  }

  .clan-list {
    display: grid;
    grid-template-columns: 1fr;
  }

  .clan-search {
    display: block;
  }

  .add-clan-btn {
    margin-left: 0;
  }
}
</style>