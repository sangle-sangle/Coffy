<template>
  <div>
    <Modal v-if="result">
      <div class="modal-wrapper">
        <div class="button-wrapper">
          <button @click="toggleModal">CLOSE</button>
        </div>
        <!-- <img src="../../assets/images/codegame/03.jpg" alt="game-01"> -->
        <div>
          참잘했어요
          <i class="collecticon fab fa-angellist"></i>
        </div>
      </div>
    </Modal>
    <div>
      <div>
        <div>
          <span class="game-title">{{game.title}}</span>
        </div>
        <div>
          <span class="game-description">{{game.description}}</span>
        </div>
        <div>
          <span class="game-hint">{{game.hint}}</span>
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
    <div>
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
    <Modal id="tip1" v-if="tips===1">
      <div class="modal-wrapper">
        <!-- <img src="../../assets/images/codegame/03.jpg" alt="game-01"> -->
        <div>
          <div class="tiptext">
            CSS 조작을 통하여 해당 요소의 Display 에 Flex를 달아주세요            
          </div>
          <button @click="nexttip">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip2" v-if="tips===2">
      <div class="modal-wrapper">
        <!-- <img src="../../assets/images/codegame/03.jpg" alt="game-01"> -->
        <div>
          <div class="tiptext">
            위의 CSS 조작을 하게 되면 아래에 있는 조그마한 박스가 큰 박스안에 위치하게 됩니다
            그렇게 만들어보세요
            쉽지않죠? 
            하하하
          </div>
          <button @click="nexttip">2다음 팁</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip3" v-if="tips===3">
      <div class="modal-wrapper">
        <!-- <img src="../../assets/images/codegame/03.jpg" alt="game-01"> -->
        <div>
          <div class="tiptext">
            3번째 팁ㅇ은바로asdfasdfkasdjfhaskdfjasldfkjasdflkjasdflkjasdflkj 
          </div>
          <button @click="nexttip">3다음 팁</button>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue';
import { getGame, solvedProblem } from '@/api/game'

export default {
  components: {
    Modal
  },
  data() {
    return {
      tips : 1,
      solved : false,
      game: {},
      basecolor: ['basered', 'basegreen', 'baseblue'],
      color: ['red', 'green', 'blue'],
      answer: [],
      result: false,
    }
  },
  created(){
    this.getGamedata()
  },
  methods: {
    nexttip(){
      this.tips ++
    },
    getGamedata() {
      getGame(1,this.$route.params.id).then(response => {
        this.game = response.data
        this.game.base = JSON.parse(response.data.base.split(`'`).join(`"`))
        this.game.problem = JSON.parse(response.data.problem.split(`'`).join(`"`))
      }).then(()=>{
        this.baseSetting()
        this.problemSetting()
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
    }
  },
  watch: {
    tips() {
      console.log(this.tips)
      let answerboard = document.querySelector('.answer-board');
      let itembox = document.querySelector('.itembox');
      let modal = document.querySelector('#slot-modal')

      if (this.tips===2){
        answerboard.style['position'] = 'sticky'
        answerboard.style['z-index'] = 5
        modal.style['transform'] = 'translate(0px,0px)'
      } else if (this.tips === 3){
        answerboard.style['z-index'] = 0
        itembox.style['position'] = 'sticky'
        itembox.style['z-index'] = 5
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
            let data = {
              category_id:1,
              game_id:this.game.id,
            }
            solvedProblem(data).then(response=> {
              console.log(response)
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
    width: 30%;
    background-color: #eee;
    margin-bottom: 2rem;
    color: #333;
  }

  .css {
    display: flex;
    margin: 0.5rem;
  }

  .itembox {
    width: 700px;
    height: 700px;
    background-color: #eee;
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
</style>