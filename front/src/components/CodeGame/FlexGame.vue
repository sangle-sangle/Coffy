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
        <i class="far fa-next"/>
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
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue';
import { getGame } from '@/api/code'

export default {
  components: {
    Modal
  },
    data() {
        return {
          game : { 
                      id : 1,
                      category : 1,
                      title : 'justify-content',
                      base : {'display': 'flex', 'justify-content': 'space-between'},
                      problem : {'':'flex', 'justify-content':''},
                      description : "justify-content 의 종류에 대하여 알아보아요 ~",
                      hint : "justify-content 에는 center, flex-end, space-around, space-between, space-even 등이 있어요",
                      item_cnt : 2,
                    // },
                    // {
                    //     id : 2,
                    //     category : 1,
                    //     title: 'flex-direction',
                    //     base: {'display': 'flex', 'flex-direction': 'column-reverse'},
                    //     problem: {'': 'flex', 'flex-direction': ''},
                    //     description: 'flex-direction 사용법을 익혀보아요',
                    //     hint: "'flex-direction'은 가로 방향으로 배치하는 'row', 세로 방향으로 배치하는 'column'이 있고 이를 거꾸로 배치하고 싶으면 '-reverse'를 붙이면 돼요",
                    //     item_cnt : 2,
                    // },
                    // {
                    //     id : 3,
                    //     category : 1,
                    //     title: 'flex 속성을 이용한 정가운데 배치',
                    //     base: {'display': 'flex', 'justify-content': 'center', 'align-items': 'center'},
                    //     problem: {'': 'flex', 'justify-content': '', 'align-items': ''},
                    //     description: 'flex 속성을 이용해서 정가운데에 요소를 배치하는 방법을 익혀보아요',
                    //     hint: "'align-items' 속성은 수직축 방향으로 아이템들을 정렬하는 속성이에요. 'justify-content'를 기준으로 수직 방향이라고 생각하면 돼요.",
                    //     item_cnt : 3,
                    // },
                    // {
                    //     id : 4,
                    //     category : 1,
                    //     title: 'flex 속성 정리',
                    //     base: {'display': 'flex', 'flex-direction': 'row-reverse', 'justify-content': 'space-between', 'align-items': 'flex-end'},
                    //     problem: {'display': '', '': 'row-reverse', 'justify-content': 'space-between', 'align-items': ''},
                    //     description: 'flex 속성을 이용해서 요소들을 맨 아래에 거꾸로 배치해보아요',
                    //     hint: "No.1 ~ No.3 까지 익혔던 속성들을 이용하면 충분히 해결할 수 있어요~",
                    //     item_cnt : 3,
                    // },
                    // {
                    //     id : 5,
                    //     category : 1,   
                    //     title: 'flex 속성 정리',
                    //     base: {'display': 'flex', 'flex-direction': 'column', 'justify-content': 'space-between', 'align-items': 'center'},
                    //     problem: {'display': '', 'flex-direction': '', 'justify-content': '', 'align-items': ''},
                    //     description: 'flex 속성을 이용해서 요소들을 정가운데에 세로로 배치해보아요',
                    //     hint: "flex-direction 값에 따라서 justify-content와 align-items의 정렬 방향이 달라짐을 잊지마세요!",
                    //     item_cnt : 3,
                    },
          basecolor : ['basered','basegreen','baseblue'],
          color :['red','green','blue'],
          answer: [],
          result: false,
        }
    },
  mounted() {
    this.baseSetting();
    this.problemSetting();
    this.getGamedata()
  },
  methods: {
    async getGamedata(){
      this.game = await getGame(this.$route.params.id)
      console.log(this.game)
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
    answer() {
      let idx = 0;
      let result = true;
      let userGround = document.querySelector('#user-ground');
      let baseGround = document.querySelector('#base-ground');
      for (let [key, value] of Object.entries(this.game.problem)) {
        if (!(key)) {
          userGround.style[this.answer[idx]] = value
          if (baseGround.style[this.answer[idx]] !== value){
            result = false
          }
        }
        if (!(value)) {
          userGround.style[key] = this.answer[idx]
          if (this.answer[idx] !== baseGround.style[key]){
            result = false
          }
        }
        if (key && value) {
          userGround.style[key] = value
        }
        idx ++
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
  padding : 1rem;
  width : 30%;
  background-color : #eee;
  margin-bottom : 2rem;
  color : #333;
}

.css {
  display: flex;
  margin : 0.5rem;
}

.itembox {
  width: 700px;
  height: 700px;
  background-color: #eee;
}

#base-ground {
  width: 700px;
  height: 700px;
  position: relative;
}

.game-title {
  font-size : 2rem;
}

.game-description {
  font-size : 1.5rem;
}

.game-hint {
  font-size : 1rem;
}

#user-ground {
  width : 700px;
  height : 700px;
  position :relative;
  top: -700px;
}

.collecticon {
  font-size:100px;
}

.park {
  width : 100px;
  height : 100px;
}

.carbox {
  display :flex;
  align-items: center;
  justify-content: center;
  width : 100px;
  height : 100px;
}

.car {
  width : 50px;
  height : 50px;
}

.basered {
 background-color : rgb(252, 115, 115)
}

.basegreen {
 background-color : rgb(147, 252, 115)

}

.baseblue {
 background-color : rgb(132, 130, 248)

}

.car.red {
  background-color : red;
}

.car.green {
  background-color : green;
}

.car.blue {
  background-color : blue;
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