<template>
  <div>
    <div class="ctr80">
      <div class="toggle">
        <div @click="formchange">폼 변경</div>
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
        <button @click="submitcode">작성</button>
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
            <codemirror 
              :value="codedata.htmltext"
              :options="htmloptions"
              @input="updateCode('htmltext',$event)"
            />
            <!-- <codearea title="html" :theme="theme" v-model="codedata.htmltext" name="text"></codearea> -->
          </div>
          <div id="csscol" class="col border">
            <div class="coltitle"> 
              <span>CSS</span>
            </div>
            <i class="fas fa-expand expandicon" @click="expand($event,1)"></i>
            <codemirror 
              :value="codedata.csstext"
              :options="cssoptions"
              @input="updateCode('csstext',$event)"
            />
          </div>
          <div :hidden="expandcheck[0] && expandcheck[1]" id="jscol" class="col">
            <div class="coltitle"> 
              <span>JS</span>
            </div>
            <i class="fas fa-expand expandicon" @click="expand($event,2)"></i>
            <codemirror 
              :value="codedata.jstext"
              :options="jsoptions"
              @input="updateCode('jstext',$event)"
            />
            <!-- <codearea title="JS" :theme="theme" v-model="codedata.jstext" name="text"></codearea> -->
          </div>
        </div>
        <div class="apply" @click="apply">적용하기 </div>
        <div id="applyform" class="rowapply">
          <div class='itembox'></div>
          <applycode class='itembox' :code="afterdata" />
        </div>
      </div>
      <!-- footer 적용 되면 지울거  -->
      <div style="height:500px;">
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import applycode from './applycode.vue'
import { addCode } from '@/api/code.js'
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/xml/xml.js'

export default {
  components : {
    codemirror,
    applycode,
  },
  data() {
    return {
      form : 0,
      title : '가운데 가운데',
      description : '센트럴 팍',
      htmloptions : {
        tabSize: 4,
        mode: 'xml',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      cssoptions : {
        tabSize: 4,
        mode: 'css',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      jsoptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'default',
        lineNumbers: true,
        line: true,
      },
      theme: 0,
      codedata : {
        htmltext : `<div class="maindiv"> 
  <div class="center-div">
    <div class="text">
      중앙정렬
    </div>
  </div>
</div>`,
        csstext : `.maindiv {
  width : 100%;
  height : 100%;
  display:flex;
  align-items:center;
  justify-content:center;
}

.center-div {
  width : 100px;
  height : 100px;
  background-color : black;
}
.text {
  text-align:center;
  color : white;
  line-height : 100px;
}`,
        jstext : `console.log('asdf')
let a = document.querySelector(.text)
console.log(a)`    
      },
      afterdata : '',
      expandcheck : [false,false,false]
    }
  },
  computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted(){
    this.theme = this.$store.state.common.mode === 'dark' ? 1: 0
    this.$store.commit('toggleMode', 0);
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
        this.htmloptions.theme="base16-dark"
        this.jsoptions.theme="base16-dark"
        this.cssoptions.theme="base16-dark"
      } else {
        title.style.backgroundColor = '#eee'
        title.style.color = 'black'
        textarea.style.backgroundColor = '#eee'
        textarea.style.color = 'black'
        this.htmloptions.theme="default"
        this.jsoptions.theme="default"
        this.cssoptions.theme="default"
      }
    },
    mode() {
      this.changeColor(this.mode)
    }
  },
  methods : {
    async submitcode(){
      const data = {
        title : this.title,
        description : this.description,
        html : this.codedata.htmltext,
        css : this.codedata.csstext,
        js : this.codedata.jstext
      }
      const response = await addCode(data)
      console.log(response)
    },
    updateCode(type, value){
      this.codedata[type] = value
    },
    formchange(){
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
      if (!this.expandcheck[index]){
        this.expandcheck[index] = true
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
        this.expandcheck[index] = false
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
      this.afterdata = {
        htmltext : this.codedata.htmltext,
        csstext : this.codedata.csstext,
        jstext : this.codedata.jstext
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
}

.toggle {
  display: flex;
  justify-content: flex-end;
  margin-bottom : 2rem;
}
</style>