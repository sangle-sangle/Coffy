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
    </div>
    <div class="clan-search">
      <form class="search-box" @submit.prevent="searchClan">
        <input type="text" id="search-clan" placeholder="Search Clan Name" v-model="keyword">
        <label for="search-clan" @click="searchClan"><i class="fas fa-search"></i></label>
      </form>
      <div class="clan-main-header-right" v-if="this.userInfo['access-Token'].clanid === 0">
        <div class="add-clan-btn" @click="goAddForm"><i class="fas fa-plus"></i> 클랜 생성</div>
      </div> 
    </div>
    <div class="clan-list" v-if="!loading">
      <div v-for="clan in clanList" :key="clan.id" class="clan-card">
        <div class="clan-header">
          <div v-if="!clan.locked" class="clan-private open"><i class="fas fa-lock-open"></i>공개</div>
          <div v-else class="clan-private private"><i class="fas fa-lock"></i>비공개</div>
          <div class="clan-master">Master: {{ clan.leaderId }}</div>
        </div>
        <div class="clan-name">
          {{ clan.name }}
        </div>
        <div class="clan-footer">
          <div class="clan-register-btn" @click="showClanRegister(clan.id)"><i class="fas fa-plus"></i> 클랜 가입</div>
          <div class="clan-detail-btn" @click="goClanDetail(clan.id)"><i class="fas fa-info"></i> 상세보기</div>
        </div>
      </div>
    </div>
    <Pagination :itemCount="this.$store.state.clan.clanData.length" @setNowPage="setNowPage" v-if="!loading"></Pagination>
    <SpinnerLoading v-else></SpinnerLoading>
    <Modal :showModal="showClanRegisterModal">
      <ClanRegisterModal :clanInfo="clanList[registerClanId - 1]" @closeModal="closeModal"></ClanRegisterModal>
    </Modal>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchAllClans } from '@/api/clan.js'
import ClanRegisterModal from '@/components/Clan/ClanRegisterModal.vue'
import Modal from '@/components/common/Modal.vue';
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import Pagination from '@/components/common/Pagination.vue'

export default {
  components: {
    ClanRegisterModal,
    Modal,
    SpinnerLoading,
    Pagination
  },
  data() {
    return {
      clanList: [],
      keyword: '',
      showClanRegisterModal: false,
      registerClanId: null,
      loading: false,
      nowPage: 1
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
    showClanRegister(clanId) {
      this.registerClanId = clanId;
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

.clan-main-title {
  display: inline-block;
  font-size: calc( 2rem + 0.5vw );
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 15px;
  border-bottom: 1px solid silver;
}

.clan-main-description {
  font-size: calc(0.7rem + 0.3vw);
}

.clan-search {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
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

.clan-main-header-right{
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
  background-color: #47cf73;
  margin-left:8px;
}

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
  font-size: calc(0.3rem + 0.3vw);
  font-weight: 600;
  padding: 6px;
  border-radius: 8px;
}

.clan-private.private {
  background-color: rgb(185, 114, 8);
}

.clan-private.open {
  background-color: rgb(27, 105, 196);
}

.clan-name {
  font-size: calc(1.3rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  padding-bottom: 10px;
  margin: 20px 0 10px;
  border-bottom: 1px solid silver;
}

.clan-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.clan-footer {
  display: flex;
  flex-direction: row-reverse;
}

.clan-footer > .clan-register-btn,
.clan-footer > .clan-detail-btn {
  font-size: calc(0.5rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  padding: 6px;
  color: black;
  border-radius: 8px;
  margin-left: 4px;
}

.clan-register-btn {
  background-color: #ffdd40;
}

.clan-detail-btn {
  background-color: #f7c389;
}

.clan-footer > .clan-detail-btn:hover,
.clan-footer > .clan-register-btn:hover {
  cursor: pointer;
}

@media (min-width: 600px) and (max-width: 960px) {
  .clan-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .clan-list {
    display: grid;
    grid-template-columns: 1fr;
  }

  .clan-private,
  .clan-master {
    font-size: calc(0.3rem + 0.3vw);
  }

  .clan-search {
    display: block;
  }

  .add-clan-btn {
    margin-left: 0;
  }
}
</style>