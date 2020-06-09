<template>
  <div class="signup-wrapper">
    <div class="signup-box">
      <div class="signup-title">
        Sign Up
      </div>
      <div class="essential-message">
        (<i class="fas fa-star" style="color: crimson;"></i>표는 필수 입력 사항입니다.)
      </div>
      <form class="signup-form" @submit.prevent="submitForm">
        <div class="username-form">
          <label for="username">Username<i class="fas fa-star"></i></label>
          <input type="text" id="username" v-model="userName">
          <div class="log-message" v-if="!userName.length && clickSignupBtn">
            1자 이상 작성해주세요.
          </div>
        </div>
        <div class="email-form">
          <label for="email">E-mail<i class="fas fa-star"></i></label>
          <input type="text" id="email" v-model="email">
          <div class="log-message" v-if="!isEmailValid && clickSignupBtn">
            이메일 양식으로 작성해주세요.
          </div>
        </div>
        <div class="password-form">
          <label for="password">비밀번호<i class="fas fa-star"></i></label>
          <input type="password" id="password" v-model="password">
          <div class="guideline">
            영어 소문자 4자 이상 + 숫자 4자 이상 조합해서 8자 이상으로 작성해주세요.
          </div>
          <div class="log-message" v-if="!isPasswordValid && clickSignupBtn">
            비밀번호 양식을 지켜서 작성해주세요.
          </div>
        </div>
        <div class="re-password-form">
          <label for="re-password">비밀번호 재확인<i class="fas fa-star"></i></label>
          <input type="password" id="re-password" v-model="rePassword">
          <div class="guideline">
            비밀번호를 한 번 더 작성해주세요.
          </div>
          <div class="log-message" v-if="!isRePasswordValid && clickSignupBtn">
            비밀번호가 일치하지 않거나 비밀번호 양식에 어긋납니다.
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
        <button class="signup-btn" type="submit">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { validateEmail } from '@/utils/validation/emailValidation.js'
import { validatePassword } from '@/utils/validation/passwordValidation.js'
import { registerUser } from '@/api/user.js'
import axios from 'axios'

export default {
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
      clickSignupBtn: false,
      noProfileImgUrl: 'https://user-images.githubusercontent.com/52685250/73902320-c72d6c00-48d8-11ea-82b4-eb9bfebfe9fb.png'
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode
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
  mounted() {
    this.changeColor(this.mode);
    this.mobileSize = window.innerWidth <= 600;
    window.addEventListener('resize', () => this.mobileSize = window.innerWidth <= 600);
  },
  methods: {
    async submitForm() {
      this.clickSignupBtn = true
      if (this.userName.length && this.isEmailValid && this.isPasswordValid && this.isRePasswordValid) {
        try {
          let signUpData = {
            username: this.userName,
            email: this.email,
            password: this.password,
          };
          signUpData['img'] = this.imgUrl || this.noProfileImgUrl
          const checkExistData = await registerUser(signUpData);
          if (!checkExistData.data.signup) {
            alert(checkExistData.data.message);
            return
          }
          alert('회원가입 성공!');
          await this.$store.dispatch('LOGIN', {
            email: this.email,
            password: this.password
          });
          this.initForm();
          this.$router.push('/');
        } catch (error) {
          console.log(error)
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
    initForm() {
      this.userName = '';
      this.email = '';
      this.password = '';
      this.rePassword = '';
      this.githubid = '';
      this.profileImg = null;
      this.fileName = '';
      this.imgUrl = '';
      this.checkGithubAuth = false;
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
.signup-wrapper {
  height: 100%;
}

.signup-box {
  margin: 0 auto;
  padding: 3rem 0 1rem;
  width: 90%;
  max-width: 500px;
}

.signup-title {
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

.signup-btn {
  font-size: 18px;
  font-weight: 600;
  display: block;
  width: 100%;
  margin: 2rem auto;
  text-align: center;
  background-color: #03a9f4;
  font-size: 17px;
  padding: 8px;
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