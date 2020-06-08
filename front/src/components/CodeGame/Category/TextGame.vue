<template>
  <div v-if="!this.$store.state.user.isLogin">
    <Unauth text="로그인"/>
  </div>
  <!-- <div v-else-if="this.$store.state.user.isLogin && solved">
    <Unauth text="이전 문제를 푼"/>
  </div> -->
  <div v-else>
  <!-- <div> -->
    <Modal v-if="result">
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
    <div class="itembox">
      <p id="base-ground" v-text="game.text">
      </p>
      <p id="user-ground" v-text="game.text">
      </p>
    </div>
        <div v-if="game.id===1">
    <Modal id="tip1" v-if="tips===1">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            <strong>TEXT 를 한번 배워봅시다 !</strong><br><br>
            <img src="@/assets/images/codegame/cssisawesome.jpg" alt=""><br>
            CSS를 구성하시다가 이런식으로 TEXT가 생각처럼 나오지 않는 경우를 많이 경험하셨을 것 같은데요<br>
            더 이상은 div에서 탈출하지 않도록 TEXT 구성 요소들을 배워봅시다
          </div>
          <button @click="movetip(2)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip2" v-if="tips===2">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            TEXT 조작할 수 있는 CSS 화면입니다.<br>
            word-wrap 을 이용하여 단어의 길이와 상관없이<br>부모 요소의 width를 유지 시켜줍니다<br>
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
            CSS중에서 word-wrap 을 이용하여 아래 두개의 텍스트의 형태를 <br> 똑같은 방식으로 맞춰줍니다
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
            아래의 두 텍스트가 같은 형태로 나타나도록 CSS를 조작해 보세요 ~ <br>
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

export default {
  components: {
    Modal,
    Unauth
  },
  data() {
    return {
      tips : 1,
      game: {
        id: 1,
        category: 1,
        title: 'text',
        text: "This is the first time I've seen the word Pneumonoultramicroscopicsilicovolcanoconiosis. It's a long one",
        base: {
          'word-wrap': 'break-word',
          'hyphens': 'auto'
        },
        problem: {
          'word-wrap': '',
          'hyphens': 'auto'
        },
        description: "text-wrap",
        hint: "힌트는 없다",
      },
      basecolor: ['basered', 'basegreen', 'baseblue'],
      color: ['red', 'green', 'blue'],
      answer: [],
      result: false,
    }
  },
  mounted() {
    this.baseSetting();
    this.problemSetting();
    document.querySelector('#slot-modal').style['transform'] = 'translate(0px,-100px)'
  },
  methods: {
    movetip(i){
      this.tips = i
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
    background-color: #eee;
  }

  #base-ground {
    word-wrap: normal;
    color: black;
    font-size: 2rem;
    margin: 0 auto;
    max-width: 200px;
    border: solid 2px #ccc;
    padding: 12px;
  }

  #user-ground {
    word-wrap: normal;
    color: black;
    font-size: 2rem;
    margin: 0 auto;
    max-width: 200px;
    border: solid 2px #ccc;
    padding: 12px;
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