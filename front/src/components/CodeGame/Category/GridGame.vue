<template>
  <div v-if="!$store.state.user.isLogin">
    <Unauth text="로그인"/>
  </div>
  <div v-else-if="!beforesolved">
    <Unauth text="이전 문제를 푼"/>
  </div>
  <div v-else>
  <!-- <div> -->
    <Modal v-if="result">
      <div class="modal-wrapper">
        <div class="button-wrapper">
        </div>
        <div>
          <i class="collecticon fab fa-angellist"></i><br>
          참잘했어요
        </div>
          <button v-if="game.id!==3" @click="goNext(game.id)">다음 문제로</button>
          <button @click="toggleModal">닫기</button>
      </div>
    </Modal>
    <div>
      <div>
        <div>
          <span class="game-title">{{game.title}}</span>
          <i style="margin-left:1rem" v-if="solved" class="fas fa-check"></i>
        </div>
        <div>
          <span class="game-description">{{game.description}}</span>
        </div>
        <div>
          <span v-if="game.id===1" class="game-hint" @click="movetip(1)">설명 다시보기</span>
          <span v-else class="game-hint" @click="movetip(3)">힌트보러가기</span>
        </div>
      </div>
      <div class="answer-board">
        <span>.container {{this.game.text}} {</span>
            <div class="css" v-for="(key,value,index) in game.problem" :key="value">
            <div v-if="value"><span> {{value}} : </span></div>
            <div v-else><input v-model="answer[index]" type="text"> : </div>

            <div v-if="key"> {{key}}</div>
            <div v-else><input v-model="answer[index]" type="text"></div>
            </div>
        <span>}</span>

      </div>
    </div>
    <div>
    </div>
    <div class="itembox">
        <div id="base-ground" class="grid-container">
            <div class="grid-div item1">1</div>
            <div class="grid-div">2</div>
            <div class="grid-div">3</div>  
            <div class="grid-div">4</div>
            <div class="grid-div">5</div>
            <div class="grid-div">6</div>
            <div class="grid-div">7</div>
            <div class="grid-div">8</div>  
        </div>
        <div id="user-ground" class="grid-container">
            <div class="grid-div item1">1</div>
            <div class="grid-div">2</div>
            <div class="grid-div">3</div>  
            <div class="grid-div">4</div>
            <div class="grid-div">5</div>
            <div class="grid-div">6</div>
            <div class="grid-div">7</div>
            <div class="grid-div">8</div>  
        </div>
    </div>
    <div v-if="game.id===1">
    <Modal id="tip1" v-if="tips===1">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            <strong>Grid 를 한번 배워봅시다 !</strong><br><br>
            Container에 여러 개의 div 를 정렬해야할 때 깔끔하게 넣고 싶으신가요?<br>
            깔끔한 Grid 를 위하여 여러 가지 속성들을 배워봅시다
          </div>
          <button @click="movetip(2)">다음</button>
        </div>
      </div>
    </Modal>
    <Modal id="tip2" v-if="tips===2">
      <div class="modal-wrapper">
        <div>
          <div class="tiptext">
            Grid 조작할 수 있는 CSS 화면입니다.<br>
            Grid 속성을 이용하여 두 개의 Grid Container를 <br>똑같이 나타내도록 CSS설정을 해봅시다<br>
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
            아래의 두 컨테이너가 같은 형태로 나타나도록 CSS를 조작해 보세요 ~ <br>
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
      game: {
        id: 1,
        category: 3,
        item_cnt : 0,
        title: 'grid 시스템의 이해',
        text: "",
        base: {'display':'grid','grid-template-columns': '1fr 2fr 1fr','grid-gap': '10px'},
        problem: {'display':'grid','grid-template-columns': '','grid-gap': ''},
        description: "columns 사이의 값을 1:2:1 비율로 맞추고 grid 사이의 값은 10px로 맞춰주세요",
        hint: "grid-template-columns의 값으로는 자동으로 분배해주는 auto와, 비율에 맞게 px이나 fr 단위등을 사용 할 수 있어요",

        // id : 2,
        // category : 3,
        // title : 'column에 repeat 을 이용하여 나타내 봅시다',
        // item_cnt : 1,
        // text : '',
        // base: {'display':'grid','grid-template-columns': 'repeat(5,auto)','grid-gap': '10px'},
        // problem: {'display':'grid','grid-template-columns': '','grid-gap': '10px'},
        // description: "repeat을 사용해서 5등분의 그리드가 적용된 컨테이너를 만들어 봅시다",
        // hint: "repeat(num, value) 함수는 value 값을 만큼 반복하여 사용하게 해줍니다. 정확한 등분으로 나누려면 auto값을 이용하는게 좋아보이네요",

        // id : 3,
        // category : 3,
        // title : 'grid의 column과 row를 조절하여 위치와 크기를 바꿔봅시다',
        // item_cnt : 1,
        // text : ' .item1',
        // base : {'grid-column' : '2 / 4', 'grid-row-start': 1,'grid-row-end': '3'},
        // problem : {'':'2 / 4','grid-row-start' : 1,'grid-row-end': ''},
        // description: "grid container 안의 item1 요소의 column과 row를 조절해 봅시다",
        // hint: "grid-row-start 와 grid-row-end 를 grid-row 로 한번에 표시할 수 있습니다. end의 지점은 해당 칸의 전까지 들어가게 됩니다",

        // id : 4,
        // category : 3,
        // title : 'grid의 column과 row를 조절하여 위치와 크기를 바꿔봅시다',
        // item_cnt : 0,
        // text : '',
        // base : {'display':'grid', 'grid-template-columns': 'repeat(auto-fit, minmax(33%,auto)', 'width': '800px'},
        // problem :  {'display':'grid', 'grid-template-columns': '', 'width': '800px'},
        // description: "grid container 안의 item1 요소의 column과 row를 조절해 봅시다",
        // hint: "grid-row-start 와 grid-row-end 를 grid-row 로 한번에 표시할 수 있습니다. end의 지점은 해당 칸의 전까지 들어가게 됩니다",
      },
      basecolor: ['basered', 'basegreen', 'baseblue'],
      color: ['red', 'green', 'blue'],
      answer: [],
      result: false,
    }
  },
  mounted() {
    this.getGamedata();
    document.querySelector('#slot-modal').style['transform'] = 'translate(0px,-100px)'
  },
  methods: {
    movetip(i){
      this.tips = i
    },
    getGamedata() {
      getGame(3,this.$route.params.id).then(response => {
          this.game = response.data.game
        this.solved  = response.data.count
        this.game.base = JSON.parse(response.data.game.base.split(`'`).join(`"`))
        this.game.problem = JSON.parse(response.data.game.problem.split(`'`).join(`"`))
          console.log(this.game)
      }).then(()=>{
        this.baseSetting()
        this.problemSetting()
        this.beforesolved = this.game.id-1 <= this.$store.state.user.solved[0]
      })
    },
    baseSetting() {
    this.answer = new Array(Object.keys(this.game.base).length)
    let baseGround = document.querySelector('#base-ground')
    let base = document.querySelector(`#base-ground${this.game.text}`);
      if (this.game.item_cnt){
          baseGround.classList.add('basesetting')
      }
      for (let i in this.game.base) {
        base.style[i] = this.game.base[i]
      }
    },
    problemSetting() {
      let userGround = document.querySelector('#user-ground ');
      let user = document.querySelector(`#user-ground${this.game.text}`)
        if (this.game.item_cnt){
            userGround.classList.add('basesetting')
        }
      for (let i in this.game.problem) {
        user.style[i] = this.game.problem[i]
      }

    },
    goNext(id){
      this.$router.push(`/game/grid/${id+1}/`)
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
        itembox.style['z-index'] = 0
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
      let userGround = document.querySelector(`#user-ground${this.game.text}`);
      for (let [key, value] of Object.entries(this.game.problem)) {
        if (!(key)) {
            userGround.style[this.answer[idx]] = value
          if (this.game.base[this.answer[idx]] !== value) {
              console.log(this.game.base[this.answer[idx]],value)
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
      if (result) {
        this.result = true
        if (!(this.solved)) {
            this.$store.commit('gamesolve')
            solvedProblem({category_id:3,game_id:this.game.id}).then(
            response=>{
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
    width: 60%;
    background-color: #eee;
    margin-bottom: 2rem;
    color: #333;
  }

  .css {
    display: flex;
    margin: 0.5rem;
  }

.grid-container {
  color : black;
  background-color: #2196F3;
  padding: 10px;
}

.basesetting {
  display: grid;
  grid-template-columns: auto auto auto;
  grid-gap: 10px;
}

#base-ground {
    margin-bottom : 2rem;
}

.grid-div {
  color : black;
  background-color: rgba(255, 255, 255, 0.8);
  text-align: center;
  padding: 20px 0;
  font-size: 30px;
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