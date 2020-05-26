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
         return this.code.htmltext + '<style>' + this.code.csstext + '</style>' + '\n<script>' + this.code.jstext + '<' + '/script>'
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