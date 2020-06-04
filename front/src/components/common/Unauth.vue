<template>
  <div id="unauth">
        <div class="text">
        {{text}} 후에 이용해 주세요
        </div>
        <img class="donotenter" src="@/assets/images/common/donotenter.jpg" alt="">
  </div>
</template>

<script>
export default {
    props : {
        text: { type : String}
    }
}
</script>

<style scoped>
#unauth {
    display: flex;
    width : 100%;
    height : 800px;
    align-items : center;
    justify-content: center;
    flex-direction : column;
}
.donotenter {
    width : 25%;
}
.text {
    font-size: 1.5rem;
    display : block;
    margin-bottom: 2rem;
}
</style>