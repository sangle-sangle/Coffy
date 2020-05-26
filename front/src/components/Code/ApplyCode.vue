<template>
  <div v-html="this.vhtml">
  </div>    
</template>

<script>
export default {
    name : 'applycode',
    props : {
        code : Object
    },
    computed :{
        vhtml() {
         return this.code.htmlText + '<style>' + this.code.cssText + '</style>' + '\n<script>' + this.code.jsText + '<' + '/script>'
        }
    },
    // watch: {
    //     code () {
    //       console.log(this.code)
    //     }
    // }
}

</script>

<style>
</style>