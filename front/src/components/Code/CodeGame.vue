<template>
  <div>
    <div>
        <div>
            <div class="css" v-for="(key,value) in defaultdata" :key="key">
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
        <div id="background">
            <div class="park">
            </div>
            <div class="park">
            </div>
        </div>
        <div id="userground">
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
            problem : {'display':'flex','justify-content':'space-between'},
            defaultdata : {'':'flex','justify-content':''},
            answer : [null,null],
            result : false
        }
    },
    mounted(){
        this.problemsetting()
        this.itemsetting()
    },
    methods : {
        problemsetting() {
            let background = document.querySelector('#background')
            for (var i in this.problem){
                background.style[i] = this.problem[i]
            }
        },
        itemsetting() {
            let userground = document.querySelector('#userground')
            for (var i in this.defaultdata){
                userground.style[i] = this.defaultdata[i]
            }  
        }
    },
    watch : {     
        answer() {
            let idx = 0;
            let result = true;
            let userground = document.querySelector('#userground')
            let background = document.querySelector('#background')
            for (let [key, value] of Object.entries(this.defaultdata)) {
                if (!(key)) {
                    userground.style[this.answer[idx]] = value
                    if (background.style[this.answer[idx]] !== value){
                        result = false
                    }
                    idx ++
                } if (!(value)) {
                    userground.style[key] = this.answer[idx]
                    if (this.answer[idx] !== background.style[key]){
                        console.log(this.answer[idx],background.style[key])
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
#background {
    width : 700px;
    height : 700px;
    position: relative;
}

#userground {
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