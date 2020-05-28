<template>
  <div>
    <div>
        <div>
            <div class="css" v-for="(key,value) in problem" :key="key">
                <div v-if="value"><span> {{value}} : </span></div>
                <div v-else><input v-model="answer[0]" type="text"> : </div>
                <div v-if="key"> {{key}}</div>
                <div v-else><input v-model="answer[1]" type="text"></div>
            </div>
        </div>
    </div>
    <div>
    </div>
    <i v-if="result" class="collecticon fab fa-angellist"></i>
    <div class="itembox">
        <div id="base-ground">
            <div class="park">
            </div>
            <div class="park">
            </div>
        </div>
        <div id="user-ground">
            <div class="carbox">
                <div class="car">
                </div>
            </div>
            <div class="carbox">
                <div class="car">
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    name : 'codegame',
    data() {
        return {
            base : {'display':'flex','justify-content':'space-between'},
            problem : {'':'flex','justify-content':''},
            answer : [null,null],
            result : false
        }
    },
    mounted(){
        this.baseSetting()
        this.problemSetting()
    },
    methods : {
        baseSetting() {
            let baseground = document.querySelector('#base-ground')
            for (var i in this.base){
                baseground.style[i] = this.base[i]
            }
        },
        problemSetting() {
            let userground = document.querySelector('#user-ground')
            for (var i in this.problem){
                userground.style[i] = this.problem[i]
            }  
        }
    },
    watch : {     
        answer() {
            let idx = 0;
            let result = true;
            let userground = document.querySelector('#user-ground')
            let baseground = document.querySelector('#base-ground')
            for (let [key, value] of Object.entries(this.problem)) {
                if (!(key)) {
                    userground.style[this.answer[idx]] = value
                    if (baseground.style[this.answer[idx]] !== value){
                        result = false
                    }
                    idx ++
                } if (!(value)) {
                    userground.style[key] = this.answer[idx]
                    if (this.answer[idx] !== baseground.style[key]){
                        console.log(this.answer[idx],baseground.style[key])
                        result = false
                    }
                    idx ++
                } if (key && value) {
                    userground.style[key] = value
                }
            }
            if (result){
                this.result = true
            } else {
                this.result = false
            }
        }
    }
}
</script>

<style>
.css{
    display: flex
}

.itembox {
    width : 700px;
    height : 700px;
    background-color : #eee;
}
#base-ground {
    width : 700px;
    height : 700px;
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
    background-color : red;
}
</style>