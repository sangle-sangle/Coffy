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
              <div class="clan-register" @click="toggleClanRegisterModal" v-if="this.userInfo['access-Token'].id !== clanInfo.leaderId && checkMyClan === 0"><i class="fas fa-plus"></i> 클랜 가입</div>
              <div class="clan-sign-out" @click="toggleClanSignOutModal" v-if="this.userInfo['access-Token'].id !== clanInfo.leaderId && checkMyClan === 2"><i class="fas fa-minus"></i> 클랜 탈퇴</div>
            </div>
          </div>
        </div>
      </div>
      <div class="clan-master-section" v-show="showClanMaster">
        <div class="clan-info-edit">
          <div class="edit-clan-info">
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
        </div>
      </div>
      <div class="clan-notice" v-if="checkMyClan === 1">
        <div class="notice-header">
          <div class="notice-icon"><i class="fas fa-list"></i> 게시판</div>
          <div class="add-btn-wrapper" v-if="!showPostForm && editPostId === 0">
            <div class="notice-add-btn" @click="togglePostForm">
              <i class="fas fa-plus"></i>게시글 작성
            </div>
          </div>
        </div>
        <div class="notice">
          <div class="notice-form" v-if="showPostForm">
            <div class="title-form">
              <label for="title">제목</label>
              <input type="text" name="title" id="title" v-model="postInfo.title">
            </div>
            <textarea v-model="postInfo.content" class="content" placeholder="내용"></textarea>
            <div class="notice-footer">
              <div class="notice-alert">
                <i class="fas fa-exclamation-triangle"></i>'취소' 버튼을 누르면 작성된 내용이 사라집니다.
              </div>
              <div class="submit-btn-wrapper">
                <div class="submit-btn" @click="addPost">작성</div>
                <div class="cancel-btn" @click="togglePostForm">취소</div>
              </div>
            </div>
          </div>
          <div class="notice-list">
            <div class="notice-item" v-for="post in clanPosts" :key="post.postid">
              <div v-if="editPostId !== post.postid">
                <div class="notice-item-header">
                  <div class="notice-title">{{ post.title }}</div>
                  <div class="notice-writer">{{ post.writername }}</div>
                </div>
                <div class="notice-content">{{ post.content }}</div>
                <div class="notice-item-footer">
                  <div class="notice-date">{{ post.created_at.slice(0, 10) }}</div>
                  <div class="notice-btn-wrapper" v-if="post.writerid === userInfo['access-Token'].id && editPostId === 0">
                    <i class="fas fa-edit" @click="changeEditMode(post.postid, post.title, post.content)"></i>
                    <i class="fas fa-trash-alt" @click="deleteClanPost(post.postid)"></i>
                  </div>
                </div>
              </div>
              <div v-else>
                <div class="notice-form">
                  <div class="title-form">
                    <label for="title">제목</label>
                    <input type="text" name="title" id="title" v-model="editPostTitle">
                  </div>
                  <textarea v-model="editPostContent" class="content" placeholder="내용"></textarea>
                  <div class="notice-footer">
                    <div class="notice-alert">
                      <i class="fas fa-exclamation-triangle"></i>'취소' 버튼을 누르면 수정한 내용이 사라집니다.
                    </div>
                    <div class="submit-btn-wrapper">
                      <div class="submit-btn" @click="editPost">수정</div>
                      <div class="cancel-btn" @click="toggleEditPostForm">취소</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <Pagination :itemCount="allClanPosts.length" @setNowPage="setNowPage" v-if="!loading"></Pagination>
          </div>
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
import { fetchClanInfo, fetchClanPosts, addClanPost, updateClanPost, deleteClanPost } from '@/api/clan.js'
import Modal from '@/components/common/Modal.vue'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'
import ClanRegisterModal from '@/components/Clan/ClanRegisterModal.vue'
import ClanSignOutModal from '@/components/Clan/ClanSignOutModal.vue'
import ClanDeleteModal from '@/components/Clan/ClanDeleteModal.vue'
import Pagination from '@/components/common/Pagination.vue'

export default {
  components: {
    Modal,
    SpinnerLoading,
    ClanRegisterModal,
    ClanSignOutModal,
    ClanDeleteModal,
    Pagination
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
      checkMyClan: 0,
      loading: false,
      myClanId: 0,
      clanPosts: [],
      showPostForm: false,
      postInfo: {
        clanId: 0,
        content: '',
        title: '',
        writerid: ''
      },
      editPostTitle: '',
      editPostContent: '',
      editPostId: 0,
      nowPage: 1,
      allClanPosts: []
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
    this.getClanInfo();
  },
  mounted() {
    this.fixButtonTextColor();
  },
  methods: {
    async getClanInfo() {
      let getClanData = await fetchClanInfo(this.clanId);
      this.clanInfo = getClanData.data.clan;
      this.checkMyClan = getClanData.data.clan_status;
      if (this.checkMyClan === 1) {
        const { data } = await fetchClanPosts(this.clanId);
        this.allClanPosts = data;
      }
      this.postInfo.writerid = this.userInfo['access-Token'].id;
      this.setNowPage(1);
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
    togglePostForm() {
      this.showPostForm = !this.showPostForm;
      this.initPostForm();
    },
    initPostForm() {
      this.postInfo.clanId = 0
      this.postInfo.content = ''
      this.postInfo.title = ''
    },
    async addPost() {
      if (!this.postInfo.title.length) {
        alert('제목을 입력해주세요.');
        return
      } else if (!this.postInfo.content.length) {
        alert('내용을 입력해주세요.');
        return
      } else {
        await addClanPost(this.postInfo);
        // alert('게시글이 등록되었습니다.');
        const { data } = await fetchClanPosts(this.clanId);
        this.allClanPosts = data;
        this.togglePostForm();
        this.setNowPage(1);
      }
    },
    async deleteClanPost(postId) {
      if (confirm('해당 게시글을 정말로 삭제하시겠습니까?')) {
        await deleteClanPost(postId);
        const { data } = await fetchClanPosts(this.clanId);
        this.allClanPosts = data;
        this.setNowPage(1);
      }
    },
    async editPost() {
      if (!this.editPostTitle.length) {
        alert('제목을 입력해주세요.');
        return
      } else if (!this.editPostContent.length) {
        alert('내용을 입력해주세요.');
        return
      }
      if (confirm('해당 게시글을 수정하시겠습니까?')) {
        let paramsData = {
          clanid: this.postInfo.clanId,
          content: this.editPostContent,
          postid: this.editPostId,
          title: this.editPostTitle,
          writerid: this.userInfo['access-Token'].id
        }
        await updateClanPost(paramsData);
        const { data } = await fetchClanPosts(this.clanId);
        this.allClanPosts = data;
        this.setNowPage(1);
        this.editPostId = 0;
      }
    },
    changeEditMode(postId, title, content) {
      this.editPostId = postId;
      this.editPostTitle = title
      this.editPostContent = content
    },
    toggleEditPostForm() {
      this.editPostId = 0;
      this.editPostTitle = '';
      this.editPostContent = '';
    },
    setNowPage(pageNm) {
      this.nowPage = pageNm;
      this.clanPosts = this.allClanPosts.slice(6 * (this.nowPage - 1), 6 * this.nowPage);
      this.loading = false;
    },
    goEditPage(clanId) {
      this.$router.push(`/clan/edit/${clanId}`)
    },
    fixButtonTextColor() {
      document.querySelectorAll('button').forEach(buttonTag => {
        buttonTag.style.color = 'white';
      })
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
        document.querySelector('textarea').forEach(elem => {
          elem.style.backgroundColor = '#eee';
          elem.style.color = 'black';
        })
      } else { // 다크 모드일 때
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        })
        document.querySelector('textarea').forEach(elem => {
          elem.style.backgroundColor = '#252830';
          elem.style.color = 'white';
        })
      }
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
  font-size: calc(1.2rem + .5vw);
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
  background-color: #8003f4;
}

.clan-btn-group > .clan-register {
  background-color: #03a9f4;
}

.clan-btn-group > .clan-sign-out {
  background-color: #fc69f7;
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

.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.clan-description > .description-icon,
.clan-notice .notice-icon {
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

.add-btn-wrapper {
  display: flex;
  flex-direction: row-reverse;
}

.add-btn-wrapper > .notice-add-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  text-align: center;
  padding: 8px;
  border-radius: 8px;
  background-color: #03a9f4;
}

.notice-form {
  margin-bottom: 15px;
}

.add-btn-wrapper > .notice-add-btn:hover,
.notice-form .submit-btn-wrapper div:hover {
  cursor: pointer;
}

.notice-form .title-form {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.notice-form .title-form input {
  flex-grow: 1;
}

.notice-form input,
.notice-form textarea {
  box-sizing: border-box;
  width: 100%;
  color: white;
  background-color: #252830;
  padding: 10px 5px;
  border: transparent;
  border-radius: 10px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
}

.notice-form input {
  height: 30px;
}

.notice-form textarea {
  height: 60px;
}

.notice-form label {
  width: 30px;
}

.notice-form .notice-footer {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}

.notice-form .submit-btn-wrapper {
  display: flex;
}

.notice-form .submit-btn-wrapper div {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  padding: 6px 8px;
  border-radius: 8px;
}

.submit-btn {
  background-color: #03a9f4;
  margin-right: 8px;
}

.cancel-btn {
  background-color: #8003f4;
}

.notice-item {
  padding: 8px 0;
  margin: 8px 0;
  border-bottom: 1px solid silver;
}

.notice-item:first-child {
  border-top: 1px solid silver;
}

.notice-item-header,
.notice-item-footer {
  display: flex;
  justify-content: space-between;
}

.notice-item-header,
.notice-item-footer {
  font-size: calc(0.8rem + 0.3vw);
}

.notice-item-header > .notice-title {
  padding-bottom: 2px;
  margin-bottom: 6px;
  border-bottom: 1px dotted #777;
}

.notice-content {
  font-size: calc(0.7rem + 0.3vw);
  padding: 4px 0;
}

.notice-item-footer .notice-btn-wrapper > i {
  padding-left: 12px;
  margin-left: 8px;
}

.notice-item-footer .notice-btn-wrapper > i:first-child {
  color: goldenrod;
}

.notice-item-footer .notice-btn-wrapper > i:last-child {
  color: crimson;
}

.notice-item-footer .notice-btn-wrapper > i:hover {
  cursor: pointer;
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

  .edit-clan-info > div > button {
    font-size: 0.9rem;
  }
}
</style>