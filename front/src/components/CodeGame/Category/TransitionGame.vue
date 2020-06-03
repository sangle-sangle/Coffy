<template>
  <div>
    <Modal :showModal="result">
      <div class="modal-wrapper">
        <div class="button-wrapper">
          <button @click="toggleModal">CLOSE</button>
        </div>
        <!-- <img src="../../assets/images/codegame/03.jpg" alt="game-01"> -->
        <div>
          참잘했어요
          <i v-if="result" class="collecticon fab fa-angellist"></i>
          <i class="far fa-next" />
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
    
    <button class="runbutton" @click="run">transition</button>
    <div class="itembox">
      <div id="base-ground">
      </div>
      <div id="user-ground">
      </div>
    </div>
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue';
import { getGame } from '@/api/game'

export default {
  components: {
    Modal
  },
  data() {
    return {
      game: {
        category : 3,
        title : 'width 기본',
        base : { 'width' : '30%' , 'transition': 'width 1s'},
        problem : { 'width' : '30%' , 'transition': ''},
        description : 'width 에 transition 을 적용해 봅시다',
        hint: 'width 가 변경이 될 때 1초에 걸쳐서 이동을 해봅시다.',
        item_cnt : 0,
        after : {'width' : '50%'},
      },
      before : [],
      basecolor: ['basered', 'basegreen', 'baseblue'],
      color: ['red', 'green', 'blue'],
      answer: [],
      result: false,
    }
  },
  mounted(){
    // this.getGamedata()
    this.baseSetting()
    this.problemSetting()
  },
  methods: {
    getGamedata() {
      getGame(this.$route.params.id).then(response => {
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
    },
    run(){
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
        let idx = 0
        for (let i in this.game.after){
          baseGround.style[i] = this.before[idx]
          userGround.style[i] = this.before[idx]
          idx ++
        }
        if (result) {
          this.result = true
        } else {
          this.result = false
        }
      },3000)
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

  #base-ground {
    margin : 0 auto;
    height : 100px;
    background-color :rgb(147, 252, 115)
  }

  #user-ground {
    margin : 0 auto;
    height : 100px;
    background-color :rgb(132, 130, 248)
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
</style>