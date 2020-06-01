<template>
  <div>
    <div class="ctr80">
      <div class="toggle">
        <div @click="formChange">폼 변경</div>
        <input type="radio" id="theme1" value="0" v-model.number="theme">
        <label for="theme1">Main</label>
        <br>
        <input type="radio" id="theme2" value="1" v-model.number="theme">
        <label for="theme2">dark </label>
      </div> 
      <div class="title">
        <div>
          <label for="title">제목 </label>
          <input v-model="title" type="text" name="title" id="title">
        </div>
        <button @click="submitCode">작성</button>
      </div>
      <div>
        <span>코드 설명</span>
        <textarea v-model="description" class="description" />
      </div>
      <div id="codecreateform">
        <div id="inputbox" class="flex">
          <div id="htmlcol" class="col">
            <div class="coltitle"> 
              <span>HTML</span>
            </div>
            <i class="fas fa-expand expandicon" @click="expand($event,0)"></i>
            <CodeMirror 
              :value="codeData.htmlText"
              :options="htmlOptions"
              @input="updateCode('htmlText',$event)"
            />
          </div>
          <div id="csscol" class="col border">
            <div class="coltitle"> 
              <span>CSS</span>
            </div>
            <i class="fas fa-expand expandicon" @click="expand($event,1)"></i>
            <CodeMirror 
              :value="codeData.cssText"
              :options="cssOptions"
              @input="updateCode('cssText',$event)"
            />
          </div>
          <div :hidden="expandCheck[0] && expandCheck[1]" id="jscol" class="col">
            <div class="coltitle"> 
              <span>JS</span>
            </div>
            <i class="fas fa-expand expandicon" @click="expand($event, 2)"></i>
            <CodeMirror 
              :value="codeData.jsText"
              :options="jsOptions"
              @input="updateCode('jsText',$event)"
            />
          </div>
        </div>
        <div id="applyform" class="rowapply">
          <ApplyCode class='itembox' :code="afterData" />
        </div>
      </div>
        <div style="height:500px;">
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ApplyCode from '@/components/Code/ApplyCode.vue'
import { addCode } from '@/api/code.js'
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
      afterData : {},
      expandCheck : [false,false,false]
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted(){
    this.theme = this.$store.state.common.mode === 'dark' ? 1: 0
    this.changeColor(this.mode);
  },
  watch:{
    theme() {
      let title = document.querySelector("#title")
      let textarea = document.querySelector('.description')
      if (this.theme) {
        title.style.backgroundColor = '#252830'
        title.style.color = 'white'
        textarea.style.backgroundColor = '#252830'
        textarea.style.color = 'white'
        this.htmlOptions.theme="base16-dark"
        this.jsOptions.theme="base16-dark"
        this.cssOptions.theme="base16-dark"
      } else {
        title.style.backgroundColor = '#eee'
        title.style.color = 'black'
        textarea.style.backgroundColor = '#eee'
        textarea.style.color = 'black'
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
    }
  },
  methods : {
    async submitCode(){
      const data = {
        title : this.title,
        description : this.description,
        html : this.codeData.htmlText,
        css : this.codeData.cssText,
        js : this.codeData.jsText
      }
      const response = await addCode(data)
      console.log(response)
    },
    updateCode(type, value){
      this.codeData[type] = value
    },
    formChange(){
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
    apply() {
      this.afterData = {
        htmlText : this.codeData.htmlText,
        cssText : this.codeData.cssText,
        jsText : this.codeData.jsText
      }
    },
    changeColor(mode) {
      if (mode === 'white') { // 화이트 모드일 때
        console.log('white')
      } else { // 다크 모드일 때
        console.log('dark')
      }
    }
  }
}
</script>

<style scoped>
.flex { 
  display:flex
}

.expandicon {
  float:right;
  margin-right:2px;
}
.colinput {
  width : 60%;
}

.title {
  display : flex;
  justify-content: space-between;
  margin-bottom : 1rem;
}

#title {
  border : 1px orange solid
}

.coltitle {
  text-align:center;
}
.border {
  border : 1px solid #eee;
}
.col {
  width : 33%;
  position: relative;
  -webkit-transition: width 1s;
  transition: width 1s;
}

.description {
  width : 100%;
  height : 100px;
}

.windowsize {
  width : 1000px;
  height : 500px;
}

.col:focus-within {
  width : 100%;
}

.ctr80 {
  width : 90%;
  margin : 0 auto;  
}

.codetitle {
  display : flex;
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
  text-align : center;
}

.colapply {
  width : 40%;
}

.itembox{
  background-color : #eee;
  min-width : 400px;
  min-height: 400px;
  border:1px black solid;
  width: 100%;
}

.toggle {
  display: flex;
  justify-content: flex-end;
  margin-bottom : 2rem;
}
</style>