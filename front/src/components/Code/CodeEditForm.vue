<template>
  <div>
    <div class="code-form-header">
      <div class="code-form-title">
        코드 {{ pageTitle }}
      </div>
      <div class="code-btn-group">
        <div class="code-btn change-layout" @click="formChange">레이아웃 변경</div>
        <input type="radio" id="theme1" value="0" v-model.number="theme" hidden>
        <label class="code-btn dark-theme" for="theme1" v-if="theme === 1"><i class="fas fa-exchange-alt"></i> Dark Theme</label>
        <input type="radio" id="theme2" value="1" v-model.number="theme" hidden>
        <label class="code-btn white-theme" for="theme2" v-if="theme === 0"><i class="fas fa-exchange-alt"></i> White Theme</label>
      </div>
    </div>
    <div class="title">
      <div class="title-wrapper">
        <label for="title" class="name-tag">제목</label>
        <input @input="typing" :value="title" type="text" name="title" id="title">
        <div class="letter-cnt">({{ titleLetterCnt }}/30)</div>
      </div>
      <div class="submit-btn" @click="submitCode" v-if="Object.keys(userInfo).length">{{ pageTitle }}</div>
    </div>
    <div class="title-alert">(제목은 30자 이내로 작성)</div>
    <label class="description name-tag" for="code-description">코드 설명</label>
    <textarea v-model="description" class="description-textarea" id="code-description" />
    <div id="codecreateform">
      <div id="inputbox" :class="!mobileSize ? 'flex' : ''">
        <div id="htmlcol" :class="!mobileSize ? 'col border' : 'border'">
          <div class="coltitle"> 
            <span><i class="fab fa-html5" style="color: orange;"></i> HTML</span>
          </div>
          <button class="copycon" @click="clickCopy(codeData.htmlText)">copy</button>
          <i class="fas fa-expand expandicon" @click="expand($event,0)" v-if="!mobileSize"></i>
          <CodeMirror 
            :value="codeData.htmlText"
            :options="htmlOptions"
            @input="updateCode('htmlText',$event)"
          />
        </div>
        <div id="csscol" :class="!mobileSize ? 'col border' : 'border'">
          <div class="coltitle"> 
            <span><i class="fab fa-css3-alt" style="color: skyblue;"></i> CSS</span>
          </div>
          <button class="copycon" @click="clickCopy(codeData.cssText)">copy</button>
          <i class="fas fa-expand expandicon" @click="expand($event,1)" v-if="!mobileSize"></i>
          <CodeMirror 
            :value="codeData.cssText"
            :options="cssOptions"
            @input="updateCode('cssText',$event)"
          />
        </div>
        <div :hidden="expandCheck[0] && expandCheck[1]" id="jscol" :class="!mobileSize ? 'col border' : 'border'">
          <div class="coltitle"> 
            <span><i class="fab fa-js" style="color: gold;"></i> JS</span>
          </div>
          <button class="copycon" @click="clickCopy(codeData.jsText)">copy</button>
          <i class="fas fa-expand expandicon" @click="expand($event, 2)" v-if="!mobileSize"></i>
          <CodeMirror 
            :value="codeData.jsText"
            :options="jsOptions"
            @input="updateCode('jsText',$event)"
          />
        </div>
      </div>
      <div id="applyform" class="rowapply">
        <ApplyCode class='itembox' :idTag="`beforeframe${editCodeId}`" :code="beforeData" :colLayout="colLayout" />  
        <ApplyCode class='itembox' :idTag="`frame${editCodeId}`" :code="afterData" :colLayout="colLayout" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ApplyCode from '@/components/Code/ApplyCode.vue'
import { addCode, updateCode, fetchCodeInfo } from '@/api/code.js'
import { codemirror as CodeMirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/xml/xml.js'

export default {
  components : {
    CodeMirror,
    ApplyCode,
  },
  data() {
    return {
      editCodeId: 0,
      form : 0,
      title : '',
      description : '',
      htmlOptions : {
        tabSize: 4,
        mode: 'xml',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      cssOptions : {
        tabSize: 4,
        mode: 'css',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      jsOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      theme: 0,
      codeData : {
        htmlText : '',
        cssText : '',
        jsText : ''    
      },
      beforeData: {},
      afterData : {},
      expandCheck : [false,false,false],
      colLayout: false,
      mobileSize: false,
      titleLetterCnt: 0
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
      userInfo: state => state.user.userInfo
    }),
    pageTitle() {
      return this.$route.name === 'CodeForm' ? '작성' : '수정'
    }
  },
  created() {
    if (this.$route.name === 'CodeEdit') {
      this.editCodeId = Number(this.$route.path.split('/').reverse()[0]);
      this.getCodeInfo(this.editCodeId);
    }
  },
  mounted(){
    this.checkWindowWidth();
    this.theme = this.$store.state.common.mode === 'dark' ? 1: 0
    this.changeColor(this.mode);
    window.addEventListener('resize', () => {
      if (this.$route.name.includes('Code')) {
        this.checkWindowWidth()
      }
    }, { passive: true });
  },
  watch:{
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
    },
    mode() {
      this.changeColor(this.mode)
    },
    codeData:{
      deep: true,
      immediate: true,
      handler: 'apply'
    },
    title() {
      if (this.title.length > 30) {
        alert('30자 이상 초과되었습니다. 자동으로 30자 까지 입력됩니다.');
        this.title = this.title.slice(0, 30);
      }
      this.titleLetterCnt = this.title.length
    }
  },
  methods : {
    async getCodeInfo(id) {
      const codeInfo = await fetchCodeInfo(id);
      this.codeDetail = codeInfo.data.code;
      this.title = this.codeDetail.title;
      this.description = this.codeDetail.description;
      this.codeData.htmlText = this.codeDetail.html;
      this.codeData.cssText = this.codeDetail.css;
      this.codeData.jsText = this.codeDetail.javascript;
      this.beforeData = JSON.parse(JSON.stringify(this.codeData));
    },
    async submitCode(){
      if (!this.title) {
        alert('제목을 작성해주세요.');
        return
      } else if (!this.description) {
        alert('코드 설명을 작성해주세요.');
        return
      } else if (!this.codeData.htmlText && !this.codeData.cssText && !this.codeData.jsText) {
        alert('HTML, CSS, JS 중 한 언어 이상 사용해서 코드를 작성해주세요.');
        return
      }
      let data = {
        title : this.title,
        description : this.description,
        html : this.codeData.htmlText,
        css : this.codeData.cssText,
        javascript : this.codeData.jsText,
        writerid: this.userInfo['access-Token'].id
      }
      if (this.$route.name === 'CodeForm') {
        try {
          await addCode(data);
          alert('작성한 코드가 등록되었습니다.');
          this.$router.push('/code');
        } catch {
          alert('코드 등록 과정에서 오류가 발생했습니다. 관리자에게 문의하세요.');
        }
      } else {
         try {
          data['id'] = this.editCodeId;
          await updateCode(data);
          alert('코드가 수정되었습니다.')
          this.$router.push(`/code/detail/${this.editCodeId}`);
        } catch {
          alert('코드 수정 과정에서 오류가 발생했습니다. 관리자에게 문의하세요.');
        }
      }
    },
    updateCode(type, value){
      this.codeData[type] = value
    },
    formChange(){
      this.colLayout = !this.colLayout;
      let input = document.querySelector('#inputbox')
      let apply = document.querySelector('#applyform')
      let box = document.querySelector('#codecreateform')
      if (this.form) {
        this.form = 0
        for (let i=0;i<3;i++){
          let node = input.childNodes[i]
          node.childNodes[1].style.visibility = ''
          node.className= 'col border'
        }
        input.className = "flex"
        apply.className = "rowapply"
        box.className = ""
      } else {
        this.form = 1
        for (let i=0;i<3;i++){
          let node = input.childNodes[i]
          node.childNodes[1].style.visibility = 'hidden'
          node.className= 'border'
        }
        input.className = "colinput"
        apply.className = "colapply"
        box.className = "flex"
      }
    },
    typing(e) {
      this.title = e.target.value
    },
    expand(item,index) {
      if (!this.expandCheck[index]){
        this.expandCheck[index] = true
        for (let i=0;i<3;i++){
          let node = item.target.parentElement.parentElement.childNodes[i]
          if (i == index){
            node.childNodes[1].className = 'fas fa-compress expandicon'
            node.style.width = '100%'
            node.childNodes[2].childNodes[1].style.height = '1000px'
          } else {
            node.style.visibility = 'hidden'
            node.style.width = '0%'
          }
        }
      } else {
        this.expandCheck[index] = false
        for (let i=0;i<3;i++){
          let node = item.target.parentElement.parentElement.childNodes[i]
          if (i == index){
            node.childNodes[1].className = 'fas fa-expand expandicon'
            node.style.width = null
            node.childNodes[2].childNodes[1].style.height = null
          } else {
            node.style.visibility = ''
            node.style.width = null
          }
        }
      }
    },
    clickCopy(text){
      var tempElem = document.createElement('textarea');
      tempElem.value = text?text:'';  
      document.body.appendChild(tempElem);
      tempElem.select();
      document.execCommand("copy");
      document.body.removeChild(tempElem);
      alert('복사가 완료되었습니다.')
    },
    apply() {
      this.afterData = {
        htmlText : this.codeData.htmlText,
        cssText : this.codeData.cssText,
        jsText : this.codeData.jsText
      }
    },
    checkWindowWidth() {
      if (window.innerWidth < 600) {
        this.mobileSize = true;
        document.querySelector('#codecreateform').className = '';
      } else {
        this.mobileSize = false;
        document.querySelector('#codecreateform').className = this.colLayout ? 'flex' : '';
        document.querySelector('#inputbox').className = this.colLayout ? 'colinput' : 'flex';
        document.querySelector('#htmlcol').className = this.colLayout ? 'border' : 'col border';
        document.querySelector('#csscol').className = this.colLayout ? 'border' : 'col border';
        document.querySelector('#jscol').className = this.colLayout ? 'border' : 'col border';
      }
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        document.querySelector('input#title').style.borderColor = '#000';
        document.querySelector('input#title').style.backgroundColor = '#fff';
        document.querySelector('input#title').style.color = '#000';
        document.querySelector('textarea').style.backgroundColor = '#fff';
        document.querySelector('textarea').style.color = '#000';
        document.querySelectorAll('.border').forEach(elem => elem.style.borderColor = '#000');
      } else { // 다크 모드일 때
      document.querySelector('input#title').style.borderColor = '#767676';
        document.querySelector('input#title').style.backgroundColor = '#252830';
        document.querySelector('input#title').style.color = '#fff';
        document.querySelector('textarea').style.backgroundColor = '#252830';
        document.querySelector('textarea').style.color = '#fff';
        document.querySelectorAll('.border').forEach(elem => elem.style.borderColor = '#767676');
      }
    }
  }
}
</script>

<style scoped>
.code-form-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}

.code-form-header > .code-form-title {
  display: inline-block;
  font-size: calc(1.2rem + .5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 20px;
  border-bottom: 1px solid silver;
}

.code-btn-group {
  display: flex;
  padding-bottom: 5px;
  margin-bottom: 20px;
}

.code-btn-group > .code-btn,
.title > .submit-btn {
  font-size: calc(0.7rem + 0.3vw);
  font-family: 'Gothic A1';
  font-weight: 600;
  letter-spacing: -0.5px;
  text-align: center;
  padding: 10px;
  border-radius: 8px;
}

.code-btn.change-layout {
  background-color: #03a9f4;
  color: black;
  margin-right: 10px;
}

.code-btn.dark-theme {
  background-color: #555;
  color: white;
}

.code-btn.white-theme {
  background-color: #ddd;
  color: black;
}

.code-btn-group > .code-btn:hover,
.title > .submit-btn {
  cursor: pointer;
}

.name-tag {
  font-weight: 600;
  padding: 10px 14px;
}

.title {
  display : flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  margin-bottom : 0.5rem;
}

.title-wrapper {
  display: flex;
}

.title-wrapper > label {
  line-height: 1.8;
  border: 1px solid #767676;
  border-right: 1px solid transparent;
}

.title-wrapper > input {
  padding: 0 14px;
  max-width: 200px;
  font-size: calc(0.7rem + 0.3vw);
}

.letter-cnt {
  margin-left: 6px;
  line-height: 3;
}

.title > .submit-btn {
  background-color: #8003f4;
  color: black;
}

.title-alert {
  margin-bottom: 1rem;
}

.description.name-tag {
  display: inline-block;
  border: 1px solid #767676;
  border-bottom: transparent;
}

.description-textarea {
  width: 100%;
  height: 200px;
  padding: 10px 14px;
  line-height: 1.6;
  margin-bottom: 16px;
  font-size: calc(0.7rem + 0.3vw);
}

.flex { 
  display: flex;
  margin-bottom: 15px;
}

.copycon{
  float: right;
  margin-right:2px;
  position: relative;
}

.expandicon {
  float: right;
  margin-right:2px;
}
.colinput {
  width : 40%;
  min-width: 340px;
}

#title {
  border: 1px solid silver;
}

.coltitle {
  font-size: calc(1rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  text-align: center;
  padding: 10px 0;
}

.border {
  border: 1px solid #252830;
}

.col {
  width: 33.3%;
  position: relative;
  -webkit-transition: width 1s;
  transition: width 1s;
}

.windowsize {
  width: 1000px;
  height: 500px;
}

.col:focus-within {
  width: 100%;
}

.codetitle {
  display: flex;
  justify-content: space-evenly;
}

.apply {
  text-align: center;
  margin-top: 2rem;
  margin-bottom : 2rem;
}

.rowapply {
  display: flex;
  justify-content: space-evenly;
  text-align: center;
}

.colapply {
  width: 60%;
}

.itembox {
  background-color: #fff;
  border: 1px black solid;
  width: 100%;
}

.mobile-box > .border {
  margin-bottom: 15px;
}

@media (max-width: 600px) {
  .colapply {
    width: 100%;
  }
}
</style>