<template>
  <div class="account-wrapper">
    <div class="account-box">
      <div class="account-main">
        <div class="account-main-header">
          <ul class="account-tab-menu">
            <li :class="currentTab === index ? mode === 'white'?'active-tab-white':'active-tab':''" class="account-tab-item" v-for="(tab, index) in tabs" :key="tab" @click="changeTab(index)">
              {{tab}}
            </li>
          </ul>
          <div class="account-title" v-if="currentTab == 0">
            User Infomation
          </div>
          <div class="account-title" v-if="currentTab == 1">
            Password Change
          </div>
        </div>
        <div class="essential-message">
          (<i class="fas fa-star" style="color: crimson;"></i>표는 필수 입력 사항입니다.)
        </div>
        <div class="account-tab-items">
          <!-- 유저 정보 수정 탭 -->
          <div class="tab-content" v-if="currentTab == 0">
            <form class="account-form" @submit.prevent="submitUserInfoForm">
              <div class="username-form">
                <label for="username">Username<i class="fas fa-star"></i></label>
                <input type="text" id="username" v-model="userName">
                <div class="log-message" v-if="!userName.length && clickButton">
                  1자 이상 작성해주세요.
                </div>
              </div>
              <div class="email-form">
                <label for="email">E-mail<i class="fas fa-star"></i></label>
                <input type="text" id="email" v-model="email" readonly>
                <div class="log-message" v-if="!isEmailValid && clickButton">
                  이메일 양식으로 작성해주세요.
                </div>
              </div>
              <div class="profile-img-form">
                <label for="profile-image" class="form-title">프로필 이미지<small>(150[px]x150[px] 권장)</small></label>
                <div class="profile-input-box">
                  <div class="img-center">
                    <div class="profile-image-preview">
                      <div v-show="profileImg" class="preview-image"></div>
                      <div v-show="!fileName" class="no-image">
                        <img :src=noProfileImgUrl alt="no-image">
                      </div>
                    </div>
                  </div>
                  <div class="img-file-route-mobile" v-if="profileImg && mobileSize">{{ fileName }}</div>
                  <form class="file-input">
                    <div class="img-file-route" v-if="profileImg && !mobileSize">{{ fileName }}</div>
                    <label for="profile-image" v-if="!fileName">업로드</label>
                    <input type="file" name="profile-image" id="profile-image" ref="file" accept="image/png, image/jpg, image/jpeg" @change="profileImgFileUpload" hidden>
                    <div class="file-reset" @click="imageReset" v-if="fileName">reset</div>
                  </form>
                </div>
                <div class="no-img-alert" v-if="!fileName">
                  프로필 이미지를 넣지 않는 경우 기본 이미지(위 사진)로 등록됩니다.
                </div>
              </div>
              <button class="account-btn" type="submit">정보수정</button>
            </form>
            <button class="sign-out-btn" @click="signOutModal = true">탈퇴 하기</button>
            <Modal v-if="signOutModal">
              <div class="modal-wrapper">
                <div class="button-wrapper">
                  <button @click="signOutModal = false">CLOSE</button>
                </div>
                <div>
                  <div class="password-confirm-form">
                    <div class="password-form">
                      탈퇴하기 위해 비밀번호를 확인합니다.
                      <label for="password"></label>
                      <input id="password" v-model="passwordConfirm" type="password" placeholder="Password">
                    </div>
                    <button class="password-confirm-button" @click="signOut">확인</button>
                  </div>
                </div>
              </div>
            </Modal>
          </div>
          <!-- 비밀번호 수정 탭 -->
          <div class="tab-content" v-if="currentTab == 1">
            <form class="password-form" @submit.prevent="submitPasswordForm">
              <div class="password-form">
                <label for="password">비밀번호<i class="fas fa-star"></i></label>
                <input type="password" id="password" v-model="password">
                <div class="guideline">
                  영어 소문자 4자 이상 + 숫자 4자 이상 조합해서 8자 이상으로 작성해주세요.
                </div>
                <div class="log-message" v-if="!isPasswordValid && clickButton">
                  비밀번호 양식을 지켜서 작성해주세요.
                </div>
              </div>
              <div class="re-password-form">
                <label for="re-password">비밀번호 재확인<i class="fas fa-star"></i></label>
                <input type="password" id="re-password" v-model="rePassword">
                <div class="guideline">
                  비밀번호를 한 번 더 작성해주세요.
                </div>
                <div class="log-message" v-if="!isRePasswordValid && clickButton">
                  비밀번호가 일치하지 않거나 비밀번호 양식에 어긋납니다.
                </div>
              </div>
              <button class="account-btn" type="submit">비밀번호 수정</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { validateEmail } from '@/utils/validation/emailValidation.js'
import { validatePassword } from '@/utils/validation/passwordValidation.js'
import { updateMyInfo, changePassword, deleteMyInfo } from '@/api/user.js'
import Modal from '@/components/common/Modal.vue';
import axios from 'axios'

export default {
  components: {
    Modal
  },
  data() {
    return {
      userName: '',
      email: '',
      password: '',
      rePassword: '',
      githubid: '',
      profileImg: null,
      fileName: '',
      imgUrl: '',
      mobileSize: false,
      checkGithubAuth: false,
      clickButton: false,
      noProfileImgUrl: 'https://user-images.githubusercontent.com/52685250/73902320-c72d6c00-48d8-11ea-82b4-eb9bfebfe9fb.png',
      currentTab: 0,
      tabs: ['회원정보 수정', '비밀번호 수정'],
      signOutModal: false,
      passwordConfirm: false,
      initial : {
        userName: '',
        email: '',
      },
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo['access-Token']
    }),
    isEmailValid() {
      return validateEmail(this.email)
    },
    isPasswordValid() {
      return validatePassword(this.password)
    },
    isRePasswordValid() {
      return validatePassword(this.rePassword) && this.password === this.rePassword
    },
  },
  created() {
    this.setUserInfo();
    this.$store.commit('enteredAccount');
  },
  mounted() {
    this.changeColor(this.mode);
    this.mobileSize = window.innerWidth <= 600;
    window.addEventListener('resize', () => this.mobileSize = window.innerWidth <= 600);
  },
  methods: {
    changeTab(index){
      this.currentTab = index
      this.clickButton = false
      if (index == 1)
        document.getElementsByClassName('account-wrapper')[0].style.height = '100vh'
      else
        document.getElementsByClassName('account-wrapper')[0].style.height = ''
    },
    async submitUserInfoForm() { // 회원정보 수정 로직 구현
      this.clickButton = true
      if ( this.userName.length && this.isEmailValid ) {
        try {
          let changeInfoData = {
            username: this.userName == this.initial.username?"":this.userName
          };
          // githubid는 github 연동이 된 경우(this.checkGithubAuth === true인 경우에만 등록)
          if (this.checkGithubAuth) {
            changeInfoData['githubid'] = this.githubid
          }
          // 프로필 이미지를 등록한 경우 imgur api와 연동된 url 주소도 같이 보내기
          changeInfoData['img'] = this.imgUrl || this.noProfileImgUrl
          const checkExistData = await updateMyInfo(changeInfoData);
          if (!checkExistData.data.signup) {
            alert(checkExistData.data.state);
            return
          }
          alert('회원정보 수정 성공!')
          this.setUserInfo();
          this.$router.push('/');
        } catch (error) {
          console.log(error)
        }
      }
    },
    async submitPasswordForm() { // 비밀번호 변경 로직 구현
      this.clickButton = true
      if (this.isPasswordValid && this.isRePasswordValid) {
        try {
          let passwordData = {
            password: this.password,
          };
          const checkExistData = await changePassword(passwordData);
          // 수정 필요
          if (!checkExistData.data.signup) {
            alert(checkExistData.data.message);
            return
          }
          alert('비밀번호 변경 성공!')
          // 회원가입 성공 후 자동으로 로그인
          this.setUserInfo();
          this.$router.push('/');
        } catch (error) {
          console.log(error)
        }
      }
    },
    async signOut() {
      if (!this.passwordConfirm) {
        alert('패스워드를 입력해주세요.')
        return
      }
      try {
        const response = await this.$store.dispatch('PASSWORDCONFIRM', {
          email: this.$store.state.user.userInfo['access-Token'].email,
          password: this.passwordConfirm
        })
        if (response.status === 204) {
          alert('비밀번호를 다시 입력해주세요.')
          return
        }
        this.passwordConfirm = ''
        
        const checkExistData = await deleteMyInfo(this.userInfo.id);
        // 수정 필요
        if (!checkExistData.data.count) {
          let flag = confirm('탈퇴가 완료 되었습니다.\n이용해주셔서 감사합니다.')
          if (flag){
            this.$store.commit('logout')
            this.$router.push('/');
          }
        }
      } catch (error) {
        if(error.status === 500) {
          alert('잘못된 접근입니다.')
        }
      }
    },
    getImgurProfileUrl() {
      let formData = new FormData()
      formData.append('image', this.profileImg)
      axios.post('https://api.imgur.com/3/image', formData, { headers: { Authorization: `Client-ID ${process.env.VUE_APP_IMGUR_CLIENT_ID}` } })
        .then(response => {
          let getImgData = response.data.data
          this.imgUrl = getImgData.link
        })
        .catch(error => {
          console.log(error)
        })
    },
    setUserInfo() {
      this.userName = this.userInfo.username;
      this.email = this.userInfo.email;
      this.password = '';
      this.rePassword = '';
      this.githubid = this.userInfo.githubid ? this.userInfo.githubid: '';
      this.profileImg = this.userInfo.img ? this.userInfo.img : '';
      this.fileName = '';
      this.imgUrl = '';
      this.checkGithubAuth = false;
      this.passwordConfirm = '';
      this.initial = this.userInfo;
    },
    profileImgFileUpload() {
      this.profileImg = this.$refs.file.files[0];
      this.fileName = this.profileImg.name;
    },
    uploadImage() {
      let reader = new FileReader()
      reader.onload = event => {
        let imgTag = document.createElement('img');
        imgTag.setAttribute('class', 'profile-image');
        imgTag.setAttribute('src', event.target.result);
        imgTag.setAttribute('style', 'width: 150px; height: 150px; vertical-align: top; margin: 0 auto;');
        document.querySelector('.preview-image').appendChild(imgTag);
      }
      reader.readAsDataURL(this.profileImg);
      this.getImgurProfileUrl();
    },
    imageReset() {
      let imgTag = document.querySelector('.profile-image');
      document.querySelector('.preview-image').removeChild(imgTag);
      this.$refs.file.value = '';
      this.profileImg = null;
      this.fileName = '';
      this.imgUrl = '';
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
      } else {
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        });
      }
    }
  },
  watch: {
    profileImg() {
      if (this.profileImg) {
        this.uploadImage();
      }
    },
    mode() {
      this.changeColor(this.mode)
    }
  }
}
</script>

<style scoped>
.account-wrapper {
  height: 100%;
}

.account-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.account-tab-menu{
  margin-bottom: 20px;
}

.account-tab-item {
  display: inline-block;
  font-size: 20px;
  font-weight: bolder;
  text-align: center;
  width: 50%;
}

.active-tab {
  background-color: #444857;
}

.active-tab-white {
  background-color: #0d0d0f42;
}

.account-title {
  font-size: 26px;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

.essential-message {
  font-size: 14px;
  text-align: right;
  margin-bottom: 30px;
}

form > div[class$='-form'] {
  margin-bottom: 40px;
}

label > i {
  color: crimson;
  font-size: 14px;
  margin-left: 4px;
}

input,
select {
  color: white;
  width: 100%;
  background-color: #252830;
  padding: 10px 5px;
  border: transparent;
  border-radius: 15px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
  margin-bottom: 10px;
  margin-left: -5px;
  font-size: 18px;
}

.guideline {
  font-size: 14px;
}

.github-guideline {
  color: yellow;
  font-weight: 600;
}

.log-message {
  color: rgb(243, 77, 110);
}

.btn-group {
  margin: 10px 0;
  text-align: center;
}

.btn-group > button {
  font-size: 15px;
  padding: 0 5px;
  margin: 0 5px;
}

.github-auth {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.github-auth input {
  margin-bottom: 0;
}

.github-auth .auth-btn {
  font-size: 14px;
  font-weight: 600;
  display: inline-block;
  text-align: center;
  width: 50px;
  padding: 6px;
  margin-left: 4px;
  border-radius: 5px;
  color: black;
}

.github-auth .auth-btn.no-valid-github {
  background: linear-gradient(-180deg,#34d058,#28a745 90%);
}

.github-auth .no-valid-github:hover {
  cursor: pointer;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.5);
}

.img-center {
  text-align: center;
  margin-bottom: 10px;
}

.profile-image-preview {
  display: inline-block;
  margin-right: 7px;
  background-image: url('../../assets/images/common/transparent_background.jpg');
}

.profile-input-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-family: 'Gothic A1';
}

.profile-input-box > div img {
  width: 150px;
  height: 150px;
  margin: 0 auto;
  vertical-align: top;
}

.file-input {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 10px;
  text-align: center;
}

.img-file-route-mobile,
.file-input .img-file-route {
  background-color: #585b66;
  padding: 8px;
  margin-right: 6px;
  border-radius: 8px;
  color: white;
}

.img-file-route-mobile {
  text-align: center;
  margin-right: 0;
  margin-bottom: 10px;
}

.file-input label {
  background-color: #03a9f4;
  padding: 8px;
  margin-bottom: 0;
  margin-right: 6px;
  border-radius: 8px;
  color: black;
}

.file-input .file-reset {
  display: inline-block;
  background-color: #03a9f4;
  padding: 8px;
  border-radius: 8px;
  color: black;
}

.file-input label:hover,
.file-input .file-reset:hover {
  cursor: pointer;
}

.no-img-alert {
  margin-top: 4px;
  font-size: 14px;
}

.account-btn {
  font-size: 18px;
  font-weight: 600;
  display: block;
  width: 100%;
  margin: 2rem auto;
  text-align: center;
  background-color: #8003f4;
  font-size: 17px;
  padding: 8px;
}

.sign-out-btn {
  font-size: 18px;
  font-weight: 600;
  display: block;
  width: 100%;
  margin: 2rem auto;
  text-align: center;
  background-color: #ff0050;
  font-size: 17px;
  padding: 8px;
}

  .modal-wrapper .button-wrapper {
    text-align: right;
    padding-bottom: 8px;
  }

@media (max-width: 600px) {
  .log-message {
    font-size: 14px;
  }

  .profile-input-box,
  .profile-input-box img {
    display: block;
  }

  .file-input label,
  .file-input .file-reset {
    flex-grow: 1;
  }
}
</style>