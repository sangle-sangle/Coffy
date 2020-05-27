<template>
  <div class="clan-form-wrapper">
    <div class="clan-form-box">
      <div class="clan-form-title">
        클랜 생성
      </div>
      <div class="clan-form-description">
        클랜을 생성해서 함께 코드를 공유할 수 있습니다.
      </div>
      <div class="essential-message">
        (<i class="fas fa-star" style="color: crimson;"></i>표는 필수 입력 사항입니다.)
      </div>
      <div class="clan-form">
        <div class="clan-name">
          <label for="name" class="form-title">클랜명<i class="fas fa-star"></i></label>
          <input type="text" id="name" v-model="name">
          <div class="alert-message" v-if="addClanBtnClick && !name">
            클랜명을 작성해주세요.
          </div>
        </div>
        <div class="clan-private">
          <label class="form-title">클랜 공개 / 비공개 여부<i class="fas fa-star"></i></label>
          <div class="private-check-box">
            <label for="open"><input type="radio" id="open" :value="0" v-model="privateCheck">공개</label>
            <label for="private"><input type="radio" id="private" :value="1" v-model="privateCheck">비공개</label>
          </div>
          <div class="alert-message" v-if="addClanBtnClick && !privateCheck">
            공개 또는 비공개 선택해주세요.
          </div>
        </div>
        <div class="clan-master">
          <label class="form-title">클랜장 닉네임(자동 입력)</label>
          <input type="text" id="master-name" v-model="leaderName" readonly>
        </div>
        <div class="clan-description">
          <label for="description" class="form-title">클랜 설명</label>
          <textarea name="description" id="description" v-model="description"></textarea>
        </div>
        <div class="clan-mark">
          <label for="mark-image" class="form-title">클랜 마크<small>(150[px]x150[px] 권장)</small></label>
          <div class="mark-input-box">
            <div class="img-center">
              <div class="mark-image-preview">
                <div v-if="clanMark" class="preview-mark"></div>
                <div v-else class="no-mark">
                  <img src="../../assets/images/clan/clan_mark_no_image.png" alt="no-mark">
                </div>
              </div>
            </div>
            <div class="mark-file-route-mobile" v-if="clanMark && mobileSize">{{ fileName }}</div>
            <div class="file-input">
              <div class="mark-file-route" v-if="clanMark && !mobileSize">{{ fileName }}</div>
              <label for="mark-image" v-if="!clanMark">업로드</label>
              <input type="file" name="mark-image" id="mark-image" ref="file" accept="image/png, image/jpg, image/jpeg" @change="markImgFileUpload" hidden>
              <div class="file-reset" @click="imageReset" v-if="clanMark">reset</div>
            </div>
          </div>
          <div class="no-img-alert" v-if="!clanMark">
            클랜 마크를 넣지 않는 경우 기본 이미지(위 사진)로 등록됩니다.
          </div>
        </div>
      </div>
      <div class="add-clan-btn" @click="addClan">
        클랜 생성
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
// import { addClan } from '@/api/clan.js'

export default {
  data() {
    return {
      name: '',
      privateCheck: null,
      leaderName: '현재 로그인한 유저 닉네임',
      password: null,
      description: '',
      clanMark: '',
      fileName: '',
      mobileSize: false,
      addClanBtnClick: false
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    this.$store.commit('toggleMode', 0);
    this.changeColor(this.mode)
    this.mobileSize = window.innerWidth <= 600;
    window.addEventListener('resize', () => this.mobileSize = window.innerWidth <= 600);
  },
  methods: {
    async addClan() {
      // 필수 입력 사항 작성했는지 확인
      if (!this.name || !this.privateCheck) {
        this.addClanBtnClick = true
        return
      }
      // 클랜 생성 로직 await문으로 작성
      // await addClan()
    },
    markImgFileUpload() {
      this.clanMark = this.$refs.file.files[0];
      this.fileName = this.clanMark.name;
    },
    uploadImage() {
      let reader = new FileReader()
      reader.onload = event => {
        let imgTag = document.createElement('img');
        imgTag.setAttribute('class', 'clan-mark-image');
        imgTag.setAttribute('src', event.target.result);
        imgTag.setAttribute('style', 'width: 150px; height: 150px; vertical-align: top; margin: 0 auto;');
        document.querySelector('.preview-mark').appendChild(imgTag)
      }
      reader.readAsDataURL(this.clanMark);
    },
    imageReset() {
      let imgTag = document.querySelector('.clan-mark-image');
      document.querySelector('.preview-mark').removeChild(imgTag);
      this.$refs.file.value = '';
      this.clanMark = '';
      this.fileName = '';
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#eee'
          inputTag.style.color = 'black'
        });
        document.querySelector('textarea').style.backgroundColor = '#eee';
        document.querySelector('textarea').style.color = 'black';
      } else { // 다크 모드일 때
        document.querySelectorAll('input').forEach(inputTag => {
          inputTag.style.backgroundColor = '#252830'
          inputTag.style.color = 'white'
        })
        document.querySelector('textarea').style.backgroundColor = '#252830';
        document.querySelector('textarea').style.color = 'white';
      }
    }
  },
  watch: {
    clanMark() {
      if (this.clanMark) {
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
.clan-form-box {
  margin: 0 auto;
  padding: 3rem 0;
  width: 90%;
  max-width: 500px;
}

.clan-form-title {
  font-size: 26px;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  text-align: center;
  padding-bottom: 10px;
}

.clan-form-description {
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.essential-message {
  font-size: 14px;
  text-align: right;
  margin-bottom: 30px;
}

.clan-form > div {
  margin-bottom: 30px;
}

.clan-form i {
  color: crimson;
}

label {
  display: block;
  margin-bottom: 4px;
}

input[type="text"],
select,
textarea {
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

label[class="form-title"] {
  font-size: 17px;
  font-weight: 600;
  margin-bottom: 10px;
}

.private-check-box {
  display: flex;
  flex-wrap: wrap;
}

.private-check-box > label {
  margin-right: 10px;
}

.alert-message {
  font-size: 14px;
  font-weight: 600;
  color: crimson;
}

textarea {
  height: 150px;
  resize: none;
  margin-bottom: 0;
}

.img-center {
  text-align: center;
  margin-bottom: 10px;
}

.mark-image-preview {
  display: inline-block;
  margin-right: 7px;
  background-image: url('../../assets/images/common/transparent_background.jpg');
}

.mark-input-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-family: 'Gothic A1';
}

.mark-input-box > div img {
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

.mark-file-route-mobile,
.file-input .mark-file-route {
  background-color: #585b66;
  padding: 8px;
  margin-right: 6px;
  border-radius: 8px;
  color: white;
}

.mark-file-route-mobile {
  text-align: center;
  margin-right: 0;
  margin-bottom: 10px;
}

.file-input label {
  background: #47cf73;
  padding: 8px;
  margin-bottom: 0;
  margin-right: 6px;
  border-radius: 8px;
  color: black;
}

.file-input .file-reset {
  display: inline-block;
  background-color: #ffdd40;
  padding: 8px;
  border-radius: 8px;
  color: black;
}

@media (max-width: 600px) {
  .mark-input-box {
    display: block;
  }

  .mark-input-box img {
    display: block;
  }

  .file-input label,
  .file-input .file-reset {
    flex-grow: 1;
  }
}

.add-clan-btn {
  font-size: 17px;
  font-weight: 600;
  font-family: 'Gothic A1';
  background: linear-gradient(-180deg,#34d058,#28a745 90%);
  text-align: center;
  border-radius: 6px;
  padding: 12px;
  margin-top: 20px;
}

.file-input label:hover,
.file-input .file-reset:hover,
.add-clan-btn:hover {
  cursor: pointer;
}

.no-img-alert {
  margin-top: 4px;
  font-size: 14px;
}
</style>