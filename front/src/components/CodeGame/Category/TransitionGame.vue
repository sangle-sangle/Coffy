<template>
  <div v-if="!$store.state.user.isLogin">
    <Unauth text="로그인"/>
  </div>
  <div v-else-if="!beforesolved">
    <Unauth text="이전 문제를 푼"/>
  </div>
  <div v-else>
    <Modal v-if="result">
      <div class="modal-wrapper">
        <div class="button-wrapper">
        </div>
        <div>
          <i v-if="result" class="collecticon fab fa-angellist"></i>
          참잘했어요
        </div>
        <button v-if="game.id!==2" @click="goNext(game.id)">다음 문제로</button>
        <button @click="toggleModal">닫기</button>
      </div>
    </Modal>
    <div>
      <div class="game-page-header">
        <div class="game-title-wrapper">
          <span class="game-title">{{game.title}}</span>
          <i v-if="solved" class="fas fa-check solve">Solved!</i>
          <i v-else class="fas fa-check unsolve">Unsolved!</i>
        </div>
        <div class="game-description-wrapper">
          <span class="game-description">{{game.description}}</span>
        </div>
        <div class="game-hint-wrapper">
          <span v-if="game.id===1" class="game-hint" @click="movetip(1)">설명 다시보기</span>
          <span v-else class="game-hint" @click="movetip(7)">힌트보러가기</span>        </div>
      </div>
      <div class="answer-board">
        <div class="css" v-for="(key,value,index) in game.problem" :key="value">
          <div v-if="value"><span> {{value}} : </span></div>
          <div v-else><input v-model="answer[index]" type="text"> : </div>
          <div v-if="key"> {{key}}</div>
          <div v-else><input v-model="answer[index]" type="text"></div>
        </div>
      </div>
    </div>
    <div>
    </div>
    <button class="runbutton" @click="run">transition</button>
    <div class="itembox">
      <div v-text="game.text" id="base-ground">
      </div>
      <div v-text="game.text" id="user-ground">
      </div>
    </div>
  <div v-if="game.id===1">
    <Modal id="tip1" v-if="tips===1">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            <strong>Transition 을 한번 배워봅시다 !</strong><br><br>
            Transition 은 변화하는 과정을 보여주게 되는 속성입니다<br>
            상태 변화가 일어날 때의 속성값을 정해줄 수 있습니다 <br><br>
            <strong>transition</strong> 트랜지션 속성을 한번에 설정할 수 있음 <br>
            <strong>transition-delay</strong> 트랜지션이 몇초 후에 실행될 지 설정함<br>
            <strong>transition-duration</strong> 트랜지션이 몇초 동안 실행될 지 설정함<br>
            <strong>transition-property</strong> 트랜지션이 일어날 속성을 지정함<br>
            <strong>transition-timing-function</strong> 트랜지션 함수를 지정 
          </div>
          <button @click="movetip(2)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip2" v-if="tips===2">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            요소들의 Style을 조작할 수 있는 CSS 화면입니다.<br>
            기본적인 width가 20%로 설정이 되어있으며<br>
            transition 을 클릭하면 width의 변화가 있습니다 <br>
            transition 요소를 이용하여 변화를 알아봅시다<br>
          </div>
          <button @click="movetip(1)">이전</button>
          <button @click="movetip(3)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip3" v-if="tips===3">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            위의 div는 transition 이 적용된 div이고 <br>
            아래의 div는 transition 이 적용이 안된 div입니다 <br>
            transition 을 클릭하고 시간에 따라 변화하는 상태를 본 후 <br>
            CSS의 속성 변경을 하여 transition을 다시 눌러보세요 <br>
              {{game.hint}}
          </div>
          <button @click="movetip(2)">이전</button>
          <button @click="movetip(4)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip4" v-if="tips===4">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
              다음 단계부턴 설명이 없이 진행되니 스스로 혼자 성장해 봅시다<br> 유후훗 구글을 잘 이용해보세요 ~ 후훗
          </div>
          <button @click="movetip(3)">이전</button>
          <button @click="movetip(5)">이젠 도움이 필요없어요</button>
        </div>
      </div>
    </Modal>
  </div>
    <Modal id="tip7" v-if="tips===7">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            위의 div는 transition 이 적용된 div이고 <br>
            아래의 div는 transition 이 적용이 안된 div입니다 <br>
            transition 을 클릭하고 시간에 따라 변화하는 상태를 본 후 <br>
            CSS의 속성 변경을 하여 transition을 다시 눌러보세요 <br>
            힌트를 드리자면
              {{game.hint}}
          </div>
          <button @click="movetip(8)">가뿐하네</button>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import Unauth from '@/components/common/Unauth.vue'
import Modal from '@/components/common/Modal.vue';
import { getGame, solvedProblem } from '@/api/game'

export default {
  components: {
    Modal,
    Unauth,
  },
  data() {
    return {
      beforesolved : true,
      tips : 1,
      solved : false,
      game: {},
      before : [],
      basecolor: ['basered', 'basegreen', 'baseblue'],
      color: ['red', 'green', 'blue'],
      answer: [],
      result: false,
    }
  },
  mounted(){
    this.getGamedata()
  },
  methods: {
    movetip(id){
      this.tips = id
    },
    getGamedata() {
      getGame(5,this.$route.params.id).then(response => {
        this.game = response.data.game
        this.solved = response.data.count
        this.game.base = JSON.parse(response.data.game.base.split(`'`).join(`"`))
        this.game.problem = JSON.parse(response.data.game.problem.split(`'`).join(`"`))
        this.game.after = JSON.parse(response.data.game.after.split(`'`).join(`"`))
      }).then(()=>{
        this.baseSetting()
        this.problemSetting()
        this.beforesolved = this.game.id-1 <= this.$store.state.user.solved[4]
      })
    },
    baseSetting() {
      this.answer = new Array(Object.keys(this.game.base).length)
      let baseGround = document.querySelector('#base-ground');
      if (this.game.id !== 2){
        baseGround.style['background-color'] = 'rgb(147, 252, 115)'
      }
      for (let i in this.game.base) {
        baseGround.style[i] = this.game.base[i]
      }
    },
    problemSetting() {
      let userGround = document.querySelector('#user-ground');
      if (this.game.id !== 2){
        userGround.style['background-color'] = 'rgb(132, 130, 248)'
      }
      for (let i in this.game.problem) {
        userGround.style[i] = this.game.problem[i]
      }
    },
    toggleModal() {
      this.result = !this.result
    },
    goNext(id){
      this.$router.push(`/game/transition/${id+1}/`)
    },
    run(){
      document.querySelector('button.runbutton').disabled = true
      let idx = 0;
      let result = true;
      let userGround = document.querySelector('#user-ground');
      let baseGround = document.querySelector('#base-ground');
      for (let [key, value] of Object.entries(this.game.problem)) {
        if (!(key)) {
          userGround.style[this.answer[idx]] = value
          if (baseGround.style[this.answer[idx]] !== value) {
            result = false
          }
        }
        if (!(value)) {
          userGround.style[key] = this.answer[idx]
          if (this.answer[idx] !== this.game.base[key]) {
            result = false
          }
        }
        if (key && value) {
          userGround.style[key] = value
        }
        idx++
      }
      this.before = []
      for (let i in this.game.after){
        this.before.push(baseGround.style[i])
        baseGround.style[i] = this.game.after[i]
        userGround.style[i] = this.game.after[i]
      }
      setTimeout(()=> {
        document.querySelector('button.runbutton').disabled = false
        let idx = 0
        for (let i in this.game.after){
          baseGround.style[i] = this.before[idx]
          userGround.style[i] = this.before[idx]
          idx ++
        }
        if (result) {
          this.result = true
          if (!(this.solved)) {
            solvedProblem({category_id:5,game_id:this.game.id}).then(
              response=>{
                console.log(response)
              this.$store.commit('gamesolve')
              })
            }
        } else {
          this.result = false
        }
      },2000)
    },
  },
  watch: {
    '$route'() {
      window.location.reload()
   },
    tips() {
      let answerboard = document.querySelector('.answer-board');
      let itembox = document.querySelector('.itembox');
      let modal = document.querySelector('#slot-modal');
      let runbutton = document.querySelector('button.runbutton');
      if (this.tips === 1){
        answerboard.style['z-index'] = 0
        itembox.style['position'] = 'sticky'
        modal.style['transform'] = 'translate(200px,-300px)'
      } else if (this.tips===2){
        itembox.style['position'] = 'sticky'
        itembox.style['z-index'] = 0
        answerboard.style['position'] = 'sticky'
        answerboard.style['z-index'] = 5
        modal.style['transform'] = 'translate(0px,0px)'
      } else if (this.tips === 3 || this.tips === 7){
        runbutton.style['z-index'] = 5
        runbutton.style['position'] = 'sticky'
        answerboard.style['z-index'] = 0
        itembox.style['position'] = 'sticky'
        itembox.style['z-index'] = 5
        modal.style['transform'] = 'translate(200px,-300px)'
      } else if (this.tips ===4 ) {
        modal.style['transform'] = 'translate(200px,-300px)'
      }
    },
  }
}
</script>

<style scoped>
.answer-board {
  padding: 1rem;
  width: 30%;
  background-color: #eee;
  margin-bottom: 2rem;
  margin: 20px auto;
  color: #333;
}

.css {
  display: flex;
  margin: 0.5rem;
}

.itembox {
  width: 700px;
  height: 500px;
  background-color: #eee;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

#base-ground {
  margin : 0 auto;
  height : 100px;
}

#user-ground {
  margin : 0 auto;
  height : 100px;
}

.game-title {
  font-size: 2rem;
}

.game-description {
  font-size: 1.5rem;
}

.game-hint {
  font-size: 1rem;
}

.collecticon {
  font-size: 100px;
}

.park {
  width: 100px;
  height: 100px;
}
.runbutton {
  font-size : 2rem;
}

.modal-wrapper .button-wrapper {
  text-align: right;
  padding-bottom: 8px;
}

.modal-wrapper img {
  width: 300px;
  height: 300px;
  vertical-align: top;
}

.game-page-header {
  text-align: center;
}

.game-title-wrapper {
  font-family: 'Gothic A1';
  font-weight: 600;
  display: flex;
  justify-content: center;
  align-items: center;
}

.game-title-wrapper .fa-check {
  border-radius: 10px;
  margin-left: 10px;
  padding: 5px;
}

.game-title-wrapper .solve {
  background-color: #57c6fa;
}

.game-title-wrapper .unsolve {
  background-color: #8003f4;
}

.game-description-wrapper,
.game-hint-wrapper {
  margin: 5px 0;
}

.game-description {
  font-size: 19px;
}

.game-hint {
  background-color: #e91ee3;
  padding: 3px;
  border-radius: 5px;
  font-weight: 600;
}

.game-hint:hover {
  cursor: pointer;
}
</style>