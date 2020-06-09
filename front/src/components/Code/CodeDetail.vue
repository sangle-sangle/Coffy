<template>
  <div>
    <div class="code-detail-wrapper" v-if="!loading">
      <div class="code-title">
        <span>{{ codeDetail.title }}</span>
        <div class="btn-group">
          <div class="edit-btn" @click="editCodePage(codeId)" v-if="checkMyCode">수정</div>
          <div class="delete-btn" @click="toggleDeleteModal" v-if="checkMyCode">삭제</div>
        </div>
      </div>
      <div class="code-short-info">
        <div class="writer-info">
          <img :src="writerImage" alt="writer-image">
          <span>{{ writerName }}</span>
        </div>
        <div class="mini-info">
          <div class="code-date-info"><i class="fas fa-calendar-day"></i> {{ codeDetail.created_at }}</div>
          <div class="like-info" @click="toggleLikeCode" :style="{ 'color': likeCode ? '#f9462a' : '#fff' }"><i class="fas fa-heart"></i> {{ likeCount }}</div>
          <div class="comment-count-info"><i class="far fa-comments"></i> {{ commentCount }}</div>
        </div>
      </div>
      <div class="code-section">
        <div class="language-btn">
          <div class="lang-btn" v-for="(lang, idx) in ['html', 'css', 'javascript']" :key="lang" @click="toggleCodeMirror(idx)">
            <img :src="langImgUrl(lang)" :alt="`${lang}`" width="50">
            <span v-if="!checkMobileSize">{{ lang.toUpperCase() }}</span>
          </div>
          <div class="toggle">
            <input type="radio" id="theme1" value="0" v-model.number="theme" hidden>
            <label for="theme1" v-if="theme"><i class="fas fa-exchange-alt"></i> Dark</label>
            <input type="radio" id="theme2" value="1" v-model.number="theme" hidden>
            <label for="theme2" v-if="!theme"><i class="fas fa-exchange-alt"></i> White</label>
          </div>
        </div>
        <div v-if="htmlShowCode" class="html-section">
          <div class="lang-name border-tag">HTML</div>
          <div class="code-mirror-section border-tag">
            <CodeMirror 
              :value="codeData.htmlText"
              :options="htmlOptions"
            />
          </div>
        </div>
        <div v-if="cssShowCode" class="css-section">
          <div class="lang-name border-tag">CSS</div>
          <div class="code-mirror-section border-tag">
            <CodeMirror 
              :value="codeData.cssText"
              :options="cssOptions"
            />
          </div>
        </div>
        <div v-if="jsShowCode" class="js-section">
          <div class="lang-name border-tag">Javascript</div>
          <div class="code-mirror-section border-tag">
            <CodeMirror 
              :value="codeData.jsText"
              :options="jsOptions"
            />
          </div>
        </div>
        <div class="result-tag border-tag">Result</div>
        <div class="result-section border-tag">
          <ApplyCode class='itembox' :idTag="`frame${codeId}`" :code="codeData" />
        </div>
        <div class="description-tag border-tag">Description</div>
        <div class="description border-tag">{{ codeDetail.description }}</div>
        <div class="comment-tag border-tag">Comments</div>
        <div class="comment-section border-tag">
          <form @submit.prevent="submitComment" class="border-bottom-tag" v-if="isLogin && !editCommentStatus">
            <textarea v-model="comment"></textarea>
            <button type="submit" class="submit-comment-btn">작성</button>
          </form>
          <div class="border-bottom-tag login-message" v-else-if="!isLogin">
            <i class="fas fa-sign-in-alt"></i>
            <span>댓글을 작성하기 위해서는 로그인이 필요합니다.</span>
          </div>
          <div class="comments" v-if="comments.length">
            <div class="comment-item border-bottom-tag" v-for="comment in comments" :key="comment.id">
              <div class="comment-info">
                <div class="left">{{ comment.writername }}</div>
                <div class="right">
                  <div class="comment-btn" v-if="comment.userid === userInfo['access-Token'].id && !editCommentStatus">
                    <i class="fas fa-edit" @click="changeEditMode(comment.id, comment.content)"></i>
                    <i class="fas fa-trash-alt" @click="deleteComment(comment.id)"></i>
                  </div>
                  <div class="comment-date">{{ comment.created_at.slice(0, 10) }}</div>
                </div>
              </div>
              <div class="comment-contents" v-if="editCodeNumber !== comment.id">{{ comment.content }}</div>
              <div class="comment-contents edit-comment-form border-bottom-tag" v-else>
                <textarea v-model="editComment"></textarea>
                <button type="submit" class="submit-comment-btn" @click="submitEditComment(comment.id)">수정</button>
                <button class="edit-cancel-btn" @click="editCancel">취소</button>
              </div>
            </div>
            <Pagination :itemCount="commentCount" @setNowPage="setNowPage" v-if="!loading"></Pagination>
          </div>
          <div class="no-comments border-bottom-tag" v-else>
            <i class="fas fa-pen-square"></i>
            <div>작성된 댓글이 없습니다.<br>댓글을 작성해주세요!</div>
          </div>
        </div>
      </div>
      <Modal v-if="showDeleteCodeModal">
        <div class="modal-header">
          <div class="modal-title">코드 삭제</div>
          <div class="modal-close-btn" @click="toggleDeleteModal">CLOSE</div>
        </div>
        <div class="message">
          <p class="delete-message">해당 코드를 삭제하시겠습니까?</p>
          <p class="warning-message">(하단 '코드 삭제' 버튼을 누르면 코드가 삭제되고 내용을 복구할 수 없습니다.)</p>
        </div>
        <div class="delete-btn-wrapper">
          <div class="delete-btn">
            <span @click="deleteCode">코드 삭제</span>
          </div>
        </div>
      </Modal>
    </div>
    <div class="spinner-section" v-else>
      <SpinnerLoading></SpinnerLoading>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { fetchCodeInfo, deleteCode, postLikeCode, deleteLikeCode } from '@/api/code.js'
import { fetchMyInfo } from '@/api/user.js'
import { fetchCodeComments, fetchCodeCommentsCount, addCodeComment, deleteCodeComment, updateCodeComment } from '@/api/code.js'
import { codemirror as CodeMirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/xml/xml.js'
import ApplyCode from '@/components/Code/ApplyCode.vue'
import Modal from '@/components/common/Modal.vue'
import Pagination from '@/components/common/Pagination.vue'
import SpinnerLoading from '@/components/common/SpinnerLoading.vue'

export default {
  components : {
    CodeMirror,
    ApplyCode,
    Modal,
    Pagination,
    SpinnerLoading
  },
  data() {
    return {
      codeId: Number(this.$route.path.split('/').reverse()[0]),
      codeDetail: {},
      writerImage: '',
      writerName: '',
      htmlOptions : {
        tabSize: 4,
        mode: 'xml',
        theme: 'default',
        lineNumbers: true,
        line: true,
        readOnly: true
      },
      cssOptions : {
        tabSize: 4,
        mode: 'css',
        theme: 'default',
        lineNumbers: true,
        line: true,
        readOnly: true
      },
      jsOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'default',
        lineNumbers: true,
        line: true,
        readOnly: true
      },
      theme: 0,
      codeData : {
        htmlText : '',
        cssText : '',
        jsText : ''    
      },
      htmlShowCode: false,
      cssShowCode: false,
      jsShowCode: false,
      comment: '',
      editComment: '',
      allComments: [],
      comments: [],
      commentCount: 0,
      editCommentStatus: false,
      editCodeNumber: 0,
      checkMobileSize: false,
      likeCode: false,
      likeCount: 0,
      nowPage: 1,
      showDeleteCodeModal: false,
      loading: false
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      isLogin: state => state.user.isLogin,
      userInfo : state => state.user.userInfo
    }),
    checkMyCode() {
      return this.isLogin && this.userInfo['access-Token'].id === this.codeDetail.writerid
    }
  },
  created() {
    this.loading = true;
    this.getCodeInfo();
    this.getCodeComments();
  },
  mounted() {
    this.theme = this.mode === 'dark' ? 1 : 0;
    this.checkMobileSize = window.innerWidth <= 600;
    window.addEventListener('resize', () => this.checkMobileSize = window.innerWidth <= 600);
  },
  methods: {
    async getCodeInfo() {
      const codeInfo = await fetchCodeInfo(this.codeId);
      this.codeDetail = codeInfo.data.code;
      this.likeCode = codeInfo.data.flag;
      this.codeDetail['created_at'] = this.codeDetail['created_at'].slice(0, 10);
      this.codeData.htmlText = this.codeDetail.html;
      this.codeData.cssText = this.codeDetail.css;
      this.codeData.jsText = this.codeDetail.javascript;
      this.likeCount = this.codeDetail.likes;
      const writerData = await fetchMyInfo(this.codeDetail.writerid);
      if (writerData.data.img === null) {
        this.writerImage = 'https://user-images.githubusercontent.com/52685250/73902320-c72d6c00-48d8-11ea-82b4-eb9bfebfe9fb.png';
      } else {
        this.writerImage = writerData.data.img;
      }
      this.writerName = writerData.data.username;
      this.loading = false;
      setTimeout(() => this.changeColor(this.mode), 500)
    },
    async getCodeComments() {
      const commentsInfo = await fetchCodeComments(this.codeId);
      const commentCount = await fetchCodeCommentsCount(this.codeId);
      this.allComments = commentsInfo.data;
      this.commentCount = commentCount.data;
      this.setNowPage(1);
    },
    setNowPage(pageNm) {
      this.nowPage = pageNm;
      this.comments = this.allComments.slice(12 * (this.nowPage - 1), 12 * this.nowPage);
      this.loading = false;
    },
    async submitComment() {
      if (!this.comment.length) {
        alert('댓글 내용을 1자 이상 작성해주세요.');
        return
      }
      const paramsData = {
        codeid: this.codeId,
        content: this.comment,
        userid: this.userInfo['access-Token'].id,
        writername : this.userInfo['access-Token'].username
      }
      const { data } = await addCodeComment(paramsData);
      this.comments = data;
      this.commentCount = data.length;
      this.comment = '';
    },
    async deleteComment(commentId) {
      if (confirm('댓글을 삭제하시겠습니까?')) {
        const { data } = await deleteCodeComment(commentId, this.codeId);
        this.comments = data;
        this.commentCount = data.length;
      }
    },
    changeEditMode(commentId, content) {
      this.editCommentStatus = true;
      this.editCodeNumber = commentId;
      this.editComment = content;
    },
    async submitEditComment(commentId) {
      const { data } = await updateCodeComment({
        codeid: this.codeId,
        content: this.editComment,
        id: commentId
      })
      this.comments = data;
      this.editCancel();
    },
    editCancel() {
      this.editComment = '';
      this.editCodeNumber = 0;
      this.editCommentStatus = false;
    },
    async toggleLikeCode() {
      if (!this.isLogin) {
        alert('이 코드에 좋아요를 주고 싶으시면 로그인을 먼저 해주세요.');
        this.$router.push('/login');
        return
      }
      if (!this.likeCode) {
        this.likeCount += 1;
        await postLikeCode(this.codeId);
      } else {
        this.likeCount -= 1;
        await deleteLikeCode(this.codeId);
      }
      this.likeCode = !this.likeCode;
    },
    langImgUrl(lang) {
      return require(`../../assets/images/mainpage/${lang.toLowerCase()}.png`)
    },
    toggleCodeMirror(idx) {
      if (idx === 0) {
        this.htmlShowCode = !this.htmlShowCode
      } else if (idx === 1) {
        this.cssShowCode = !this.cssShowCode
      } else {
        this.jsShowCode = !this.jsShowCode
      }
    },
    editCodePage(codeId) {
      this.$router.push(`/code/edit/${codeId}`)
    },
    toggleDeleteModal() {
      this.showDeleteCodeModal = !this.showDeleteCodeModal
    },
    async deleteCode() {
      try {
        await deleteCode(this.codeId);
        setTimeout(() => this.$router.push('/code'), 0);
      } catch {
        alert('코드 삭제 과정에서 오류가 발생했습니다. 관리자에게 문의하세요.');
      }
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('.border-tag').forEach(elem => {
          elem.style.borderColor = '#333';
        })
        document.querySelectorAll('.border-bottom-tag').forEach(elem => {
          elem.style.borderColor = '#333';
        })
        if (this.isLogin || this.editCommentStatus) {
          document.querySelector('textarea').style.backgroundColor = '#eee';
          document.querySelector('textarea').style.color = '#333';
        }
      } else {
        document.querySelectorAll('.border-tag').forEach(elem => {
          elem.style.borderColor = 'silver';
        })
        document.querySelectorAll('.border-bottom-tag').forEach(elem => {
          elem.style.borderColor = 'silver';
        })
        if (this.isLogin || this.editCommentStatus) {
          document.querySelector('textarea').style.backgroundColor = '#252830';
          document.querySelector('textarea').style.color = 'white';
        }
      }
    }
  },
  watch: {
    mode() {
      if (!this.loading) {
        this.changeColor(this.mode);
      }
    },
    theme() {
      if (this.theme) {
        this.htmlOptions.theme="base16-dark"
        this.jsOptions.theme="base16-dark"
        this.cssOptions.theme="base16-dark"
      } else {
        this.htmlOptions.theme="default"
        this.jsOptions.theme="default"
        this.cssOptions.theme="default"
      }
    }
  }
}
</script>

<style scoped>
.code-title {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  font-size: calc(1.2rem + .5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
}

.code-title > span:first-child {
  padding-bottom: 5px;
  margin: 0 5px 20px;
  border-bottom: 1px solid silver;
}

.code-title > .btn-group {
  display: flex;
  padding-bottom: 5px;
  margin-bottom: 20px;
}

.code-title > .btn-group > .edit-btn,
.code-title > .btn-group > .delete-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  text-align: center;
  padding: 6px 8px;
  border-radius: 8px;
  margin-right: 5px;
}

.code-title > .btn-group > .edit-btn {
  color: black;
  background-color: #03a9f4;
}

.code-title > .btn-group > .delete-btn {
  color: white;
  background-color: #8003f4;
}

.code-short-info {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-bottom: 20px;
  font-size: calc(0.7rem + 0.3vw);
}

.writer-info {
  margin: 0 8px 8px 0;
}

.mini-info > div[class$='-info'] {
  display: inline-block;
  background-color: #666;
  padding: 3px 6px;
  border-radius: 4px;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  margin: 0 8px 8px 0;
  color: #fff;
  transition: all .15s;
}

.code-title > .btn-group > div:hover,
.mini-info > .like-info:hover {
  cursor: pointer;
}

.writer-info img {
  vertical-align: middle;
  width: 35px;
  border-radius: 50%;
}

.language-btn {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  font-size: calc(0.7rem + 0.3vw);
}

.language-btn .lang-btn {
  display: flex;
  align-items: center;
  color: black;
  border-radius: 15px;
  padding: 5px 15px;
  margin-right: 20px;
  font-weight: 600;
}

.toggle > label:hover,
.comment-btn > i:hover,
.language-btn .lang-btn:hover {
  cursor: pointer;
}

.lang-btn > img + span {
  margin-left: 8px;
}

.lang-btn:first-child {
  background-color: rgb(223, 183, 165);
}

.lang-btn:nth-child(2) {
  background-color: rgb(179, 204, 228);
}

.lang-btn:nth-child(3) {
  background-color: rgb(218, 213, 170);
}

.lang-name,
.result-tag,
.comment-tag,
.description-tag {
  display: inline-block;
  padding: 10px 14px;
  border: 1px solid silver;
  font-weight: 600;
}

.result-tag,
.comment-tag,
.description-tag {
  border-bottom: transparent;
}

.code-section > div[class$='-section'] {
  margin-bottom: 30px;
}

.code-mirror-section {
  border: 1px solid silver;
}

.result-section,
.description {
  border: 1px solid silver;
  margin-bottom: 30px;
}

.description {
  padding: 10px;
}

.comment-section {
  border: 1px solid silver;
  padding: 10px;
}

.login-message {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin: 10px 0 20px;
}

.login-message > i {
  font-size: 50px;
}

.login-message > i,
.login-message > span {
  margin: 0 5px;
}

.comment-section > form,
.edit-comment-form {
  display: flex;
  padding-bottom: 30px;
  margin-bottom: 10px;
}

.comment-section > form {
  border-bottom: 1px solid white;
}

.comment-section > form > textarea,
.edit-comment-form > textarea {
  flex-grow: 1;
  height: 40px;
  color: white;
  background-color: #252830;
  padding: 10px 5px;
  border: transparent;
  border-radius: 15px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
  margin-right: 20px;
}

.submit-comment-btn,
.edit-cancel-btn {
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  color: black;
  text-align: center;
  border-radius: 8px;
}

.submit-comment-btn {
  background-color: #03a9f4;
  margin-right: 10px;
}

.edit-cancel-btn {
  background-color: #8003f4;
}

.comment-item {
  padding-bottom: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid white;
}

.comment-info,
.comment-info > .right {
  display: flex;
  flex-wrap: wrap;
}

.comment-info {
  justify-content: space-between;
  font-family: 'Gothic A1';
}

.comment-btn {
  margin-right: 8px;
}

.comment-btn > i {
  padding: 0 10px;
}

.comment-btn > i:first-child {
  color: goldenrod;
}

.comment-btn > i:last-child {
  color: crimson;
}

.comment-contents {
  margin-top: 8px;
  padding: 8px 0;
}

.no-comments {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  padding-bottom: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid white;
}

.no-comments > i {
  font-size: 5rem;
  margin: 0 10px;
}

.no-comments > div {
  line-height: 1.4;
}

.itembox {
  background-color : #eee;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: 'Noto Sans KR';
}

.modal-header .modal-title {
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

.message > p.warning-message {
  font-weight: 600;
  color: #ff4500;
}

.delete-btn-wrapper {
  text-align: right;
}

.delete-btn > span {
  font-size: calc(0.7rem + 0.3vw);
  font-weight: 600;
  color: white;
  border-radius: 8px;
  padding: 4px 6px;
  background-color: #ff4500;
}

.delete-btn > span:hover {
  cursor: pointer;
}

.spinner-section {
  position: relative;
}

@media (max-width: 600px) {
  .language-btn .lang-btn {
    display: inline-block;
    flex-grow: 1;
    text-align: center;
    margin: 0 10px;
  }
}
</style>