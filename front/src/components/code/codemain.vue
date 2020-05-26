<template>
  <div class="ctr80">
    <div class="toggle">
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
    <div id="inputbox" class="flex">
      <div id="htmlcol" class="col">
        <div class="coltitle"> 
          <span>HTML</span>
        </div>
        <i style="float:right;margin-right:2px;" class="fas fa-expand" @click="expand($event,0)"></i>
        <CodeMirror 
          :value="codeData.htmlText"
          :options="htmlOptions"
          @input="updateCode('htmlText',$event)"
        />
        <!-- <CodeArea title="html" :theme="theme" v-model="codeData.htmlText" name="text"></CodeArea> -->
      </div>
      <div id="csscol" class="col">
        <div class="coltitle"> 
          <span>CSS</span>
        </div>
        <i style="float:right;margin-right:2px;" class="fas fa-expand" @click="expand($event,1)"></i>
        <CodeMirror 
          :value="codeData.cssText"
          :options="cssOptions"
          @input="updateCode('cssText',$event)"
        />
        <CodeArea title="CSS" :theme="theme" v-model="codeData.cssText" name="text"></CodeArea>
      </div>
      <div :hidden="expandCheck[0] && expandCheck[1]" id="jscol" class="col">
        <div class="coltitle"> 
          <span>JS</span>
        </div>
        <i style="float:right;margin-right:2px;" class="fas fa-expand" @click="expand($event,2)"></i>
        <CodeMirror 
          :value="codeData.jsText"
          :options="jsOptions"
          @input="updateCode('jsText',$event)"
        />
        <!-- <CodeArea title="JS" :theme="theme" v-model="codeData.jsText" name="text"></CodeArea> -->
      </div>
    </div>
    <div class="apply" @click="apply">적용하기 </div>
    <div class="applyform">
      <div class='itembox'></div>
      <div class='itembox' v-html="afterData" ></div>
    </div>
    <div style="height:500px;">
    </div>
  </div>
</template>

<script>
import CodeArea from './CodeArea'
import { addCode } from '@/api/code.js'
import { CodeMirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/xml/xml.js'

export default {
  components : {
    CodeArea,
    CodeMirror
  },
  data() {
    return {
      title : '가운데 가운데',
      description : '센트럴 팍',
      htmlOptions : {
        tabSize: 4,
        mode: 'xml',
        theme: 'base16-light',
        lineNumbers: true,
        line: true,
      },
      cssOptions : {
        tabSize: 4,
        mode: 'css',
        theme: 'base16-light',
        lineNumbers: true,
        line: true,
      },
      jsOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'base16-light',
        lineNumbers: true,
        line: true,
      },
      theme: 0,
      codeData : {
        htmlText : `<div class="maindiv"> 
    <div class="center-div">
      <div class="text">
        중앙정렬
      </div>
    </div>
  </div>`,
        cssText : `.maindiv {
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
        jsText : `console.log('asdf')
let a = document.querySelector(.text)
console.log(a)`    
      },
      afterData : '',
      expandCheck : [false,false,false]
    }
  },
  watch:{
    theme : function () {
      if (this.theme) {
        this.htmlOptions.theme="base16-dark"
        this.jsOptions.theme="base16-dark"
        this.cssOptions.theme="base16-dark"
      } else {
        this.htmlOptions.theme="base16-light"
        this.jsOptions.theme="base16-light"
        this.cssOptions.theme="base16-light"
      }
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
      console.log(data)
      const response = await addCode(data)
      console.log(response)
    },
    updateCode(type, value){
      this.codeData[type] = value
    },
    expand(item,index) {
      if (!this.expandCheck[index]){
        this.expandCheck[index] = true
        for (let i=0;i<3;i++){
          let node = item.target.parentElement.parentElement.childNodes[i]
          if (i == index){
            node.childNodes[1].className = 'fas fa-compress'
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
            node.childNodes[1].className = 'fas fa-expand'
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
      this.afterData = this.codeData.htmlText + '<style>' + this.codeData.cssText + '</style>' + '\n<script>' + this.codeData.jsText + '<' + '/script>'
    }
  }
}
</script>

<style>
.flex { 
  display:flex;
}

.title {
  display : flex;
  justify-content: space-between;
  margin-bottom : 1rem;
}


.coltitle {
  text-align:center;
}

.col {
  width : 33%;
  position: relative;
  border: 1px solid #eee;
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

.applyform {
  display: flex;
  justify-content: space-evenly;
  text-align : center;
}

.itembox{
  width : 400px;
  height: 400px;
  border:1px black solid;
}

.toggle {
  display: flex;
  justify-content: flex-end;
  margin-bottom : 2rem;

}
</style>