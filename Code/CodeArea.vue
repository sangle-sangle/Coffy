<template>
  <div class="codecol">
    <!-- <textarea class="codearea" :style="styles[this.thema]" :value="value" @keydown.tab.prevent="tab($event)" @input="updateCode($event.target.value)" name="text" rows="20">
    </textarea> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      styles : [
        {
          'font-size' : '20px',
          'color' : 'black',
          'background-color' : 'white',
        },  
        {
          'font-size' : '1.5rem',
          'color' : 'white',
          'background-color' : 'rgb(24,53,15)',
        },
      ],
    }
  },
  props : {
    thema : Number,
    value : String,
  },
  mounted() {
    
  },
  methods : {
    tab(event) {
      console.log(event.target.selectionStart)
      let text = this.value,
      originalSelectionStart = event.target.selectionStart,
      textStart = text.slice(0, originalSelectionStart),
      textEnd =  text.slice(originalSelectionStart);
      this.value = `${textStart}  ${textEnd}`
      event.target.value = this.value // required to make the cursor stay in place.
      event.target.selectionEnd = event.target.selectionStart = originalSelectionStart + 2
    },
    updateCode(value){
      this.$emit('input', value)
    }
  },
}
</script>

<style>

.codecol {
  margin : 5px;
}
.codetitle {
  display:block;
}
.codearea{
  white-space:pre;
  width:100%; 
}
</style>