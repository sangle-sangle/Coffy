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
      <p id="base-ground" v-text="game.text">
      </p>
      <p id="user-ground" v-text="game.text">
      </p>
    </div>
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue';

export default {
  components: {
    Modal
  },
    data() {
        return {
            game : { 
                      id : 1,
                      category : 1,
                      title : 'text',
                      text : "This is the first time I've seen the word Pneumonoultramicroscopicsilicovolcanoconiosis. It's a long one",
                      base :  {'word-wrap': 'break-word', 'hyphens': 'auto'},
                      problem :  {'word-wrap': '', 'hyphens': 'auto'},
                      description : "text-wrap",
                      hint : "힌트는 없다",
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
  },
  methods: {
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
  background-color: #eee;
}

#base-ground {
  word-wrap: normal;
  color : black;
  font-size: 2rem;
  margin: 0 auto;
  max-width: 200px;
  border: solid 2px #ccc;
  padding: 12px;
}

#user-ground {
word-wrap: normal;
  color : black;
  font-size: 2rem;
  margin: 0 auto;
  max-width: 200px;
  border: solid 2px #ccc;
  padding: 12px;
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