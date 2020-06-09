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
          <i class="collecticon fab fa-angellist"></i><br>
          참잘했어요
        </div>
          <button v-if="game.id!==5" @click="goNext(game.id)">다음 문제로</button>
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
          <span class="game-descerription">{{game.description}}</span>
        </div>
        <div class="game-hint-wrapper">
          <span v-if="game.id===1" class="game-hint" @click="movetip(1)">설명 다시보기</span>
          <span v-else class="game-hint" @click="movetip(7)">힌트보러가기</span>
        </div>
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
    <div class="itembox">
      <div id="base-ground">
        <div v-for="i in this.game.item_cnt" :key="i" class="park" :class="basecolor[i-1]"></div>
      </div>
      <div id="user-ground">
        <div class="carbox" v-for="i in this.game.item_cnt" :key="i">
          <div class="car" :class="color[i-1]"></div>
        </div>
      </div>
    </div>
    <div v-if="game.id===1">
    <Modal id="tip1" v-if="tips===1">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            <strong>Flex 를 한번 배워봅시다 !</strong><br><br>
            Flex 는 크게 Container 와 Item 의 두가지 속성으로 나뉘게 됩니다<br>
             Container는 Items를 감싸는 부모 요소입니다<br>  
             이번 Flex 파트에서는 Container 의 속성값을 이용하여 Item 의 배치를 조정하는 방법을 배워보겠습니다 <br>     
          </div>
          <button @click="movetip(2)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip2" v-if="tips===2">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            Container의 Style을 조작할 수 있는 CSS 화면입니다.<br>
            기본적으로 display 를 flex 로 조절을 한 후<br>
            justify-content 요소를 이용하여 위치의 변화를 알아봅시다<br>
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
            CSS중에서 Flex를 이용하여 색이 비슷한 박스에 맞춰서 넣어줘봅시다 <br>
            힌트를 드리자면
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
              다음 단계부턴 설명이 없이 진행되니 스스로 혼자 성장해 봅시다 유후훗 구글을 잘 이용해보세요 ~ 후훗
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
            CSS중에서 Flex를 이용하여 색이 비슷한 박스에 맞춰서 넣어줘봅시다 <br>
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
import { getGame , solvedProblem } from '@/api/game'

export default {
  components: {
    Modal,
    Unauth
  },
  data() {
    return {
      beforesolved : true,
      tips : 1,
      solved : false,
      game: {},
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
    movetip(i){
      this.tips = i
    },
    getGamedata() {
      getGame(1,this.$route.params.id).then(response => {
        this.game = response.data.game
        this.solved  = response.data.count
        this.game.base = JSON.parse(response.data.game.base.split(`'`).join(`"`))
        this.game.problem = JSON.parse(response.data.game.problem.split(`'`).join(`"`))
      }).then(()=>{
        this.baseSetting()
        this.problemSetting()
        this.beforesolved = this.game.id-1 <= this.$store.state.user.solved[0]
      })
    },
    baseSetting() {
      this.answer = new Array(Object.keys(this.game.base).length)
      let baseGround = document.querySelector('#base-ground');
      for (let i in this.game.base) {
        baseGround.style[i] = this.game.base[i]
      }
    },
    problemSetting() {
      let userGround = document.querySelector('#user-ground');
      for (let i in this.game.problem) {
        userGround.style[i] = this.game.problem[i]
      }
    },
    toggleModal() {
      this.result = !this.result
    },
    goNext(id){
      this.$router.push(`/game/flex/${id+1}/`)
    }
  },
  watch: {
    '$route'() {
      window.location.reload()
   },
    tips() {
      let answerboard = document.querySelector('.answer-board');
      let itembox = document.querySelector('.itembox');
      let modal = document.querySelector('#slot-modal');
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
      } else if (this.tips === 3){
        answerboard.style['z-index'] = 0
        itembox.style['position'] = 'sticky'
        itembox.style['z-index'] = 5
        modal.style['transform'] = 'translate(200px,-300px)'
      } else if (this.tips ===4 ) {
        modal.style['transform'] = 'translate(200px,-300px)'
      }
    },
    answer() {
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
          if (this.answer[idx] !== baseGround.style[key]) {
            result = false
          }
        }
        if (key && value) {
          userGround.style[key] = value
        }
        idx++
      }
      if (result) {
        this.result = true
          if (!(this.solved)) {
            solvedProblem({category_id:1,game_id:this.game.id}).then(
              response=>{
                console.log(response)
            this.$store.commit('gamesolve')
              })
            this.solved = true
          }
      } else {
        this.result = false
      }
    }
  }
}
</script>

<style scoped>
.answer-board {
  padding: 1rem;
  width: 50%;
  background-color: #eee;
  margin-bottom: 2rem;
  margin: 20px auto;
  color: black;
}

.css {
  display: flex;
  margin: 0.5rem;
}

.itembox {
  width: 700px;
  height: 700px;
  background-color: #eee;
  display: block;
  margin: 0 auto;
}
.modal-select{
  opacity: 0;
}
#base-ground {
  width: 700px;
  height: 700px;
  position: relative;
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
.game-hint:hover{
  cursor: pointer;
}
#user-ground {
  width: 700px;
  height: 700px;
  position: relative;
  top: -700px;
}

.collecticon {
  font-size: 100px;
}

.park {
  width: 100px;
  height: 100px;
}

.carbox {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
}

.car {
  width: 50px;
  height: 50px;
}

.basered {
  background-color: rgb(252, 115, 115)
}

.basegreen {
  background-color: rgb(147, 252, 115)
}

.baseblue {
  background-color: rgb(132, 130, 248)
}

.car.red {
  background-color: red;
}

.car.green {
  background-color: green;
}

.car.blue {
  background-color: blue;
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