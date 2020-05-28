<template>
  <div>
    <button @click="toggleModal">문제 보기</button>
    <Modal :showModal="showModal">
      <div class="modal-wrapper">
        <div class="button-wrapper">
          <button @click="toggleModal">CLOSE</button>
        </div>
        <img src="../../assets/images/codegame/04.jpg" alt="game-04">
      </div>
    </Modal>
    <div>
      <div>
        <div class="css" v-for="(key,value,index) in problem" :key="index">
          <div v-if="value"><span> {{value}} : </span></div>
          <div v-else><input v-model="answer[index]" type="text"> : </div>
          <div v-if="key"> {{key}}</div>
          <div v-else><input v-model="answer[index]" type="text"></div>
        </div>
      </div>
    </div>
    <div>
    </div>
    <i v-if="result" class="collecticon fab fa-angellist"></i>
    <div class="itembox">
      <div id="base-ground">
        <div class="park"></div>
        <div class="park"></div>
        <div class="park"></div>
      </div>
      <div id="user-ground">
        <div class="carbox">
          <div class="car red"></div>
        </div>
        <div class="carbox">
          <div class="car green"></div>
        </div>
        <div class="carbox">
          <div class="car blue"></div>
        </div>
      </div>
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
      base: {'display': 'flex', 'flex-direction': 'row-reverse', 'justify-content': 'space-between', 'align-items': 'flex-end'},
      problem: {'display': '', '': 'row-reverse', 'justify-content': '', 'align-items': ''},
      answer: [null, null, null, null],
      result: false,
      showModal: false
    }
  },
  mounted() {
    this.baseSetting();
    this.problemSetting();
  },
  methods: {
    baseSetting() {
      let baseGround = document.querySelector('#base-ground');
      for (let i in this.base) {
        baseGround.style[i] = this.base[i]
      }
    },
    problemSetting() {
      let userGround = document.querySelector('#user-ground');
      for (let i in this.problem) {
        userGround.style[i] = this.problem[i]
      }
    },
    toggleModal() {
      this.showModal = !this.showModal
    }
  },
  watch: {
    answer() {
      let idx = 0;
      let result = true;
      let userGround = document.querySelector('#user-ground');
      let baseGround = document.querySelector('#base-ground');
      for (let [key, value] of Object.entries(this.problem)) {
        if (!(key)) {
          userGround.style[this.answer[idx]] = value
          if (baseGround.style[this.answer[idx]] !== value){
            result = false
          }
          idx ++
        }
        if (!(value)) {
          userGround.style[key] = this.answer[idx]
          if (this.answer[idx] !== baseGround.style[key]){
            console.log(this.answer[idx],baseGround.style[key])
            result = false
          }
          idx ++
        }
        if (key && value) {
          userGround.style[key] = value
        }
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
.css {
  display: flex;
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
  background-color : gray;
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