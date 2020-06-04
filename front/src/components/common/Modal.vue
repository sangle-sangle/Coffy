<template>
  <div class="modal" :id="`${this.id}-modal`">
    <div id="slot-modal">
      <slot></slot>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  props: {
    id : {
      type : String,
    }
  },
   computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    if (!this.$route.path.split('/').includes('game')) {
      document.querySelector('#slot-modal').style.transform = 'translate(0, 0)'
    }
    this.changeColor(this.mode);
  },
  methods: {
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('#slot-modal').style.backgroundColor = '#eee';
      } else {
        document.querySelector('#slot-modal').style.backgroundColor = '#2c303a';
      }
    },
  },
  watch: {
    mode() {
      this.changeColor(this.mode)
    }
  }
}
</script>

<style scoped>
.modal {
  background-color: rgba(0, 0, 0, 0.8);
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  pointer-events: auto;
  opacity: 1;
  transition: all .2s;
  z-index: 4;
  display: flex;
  justify-content: center;
  align-items: center;
}

#slot-modal {
  transform : translate(200px,-300px);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.8);
  max-width: 800px;
  margin: 25px;
}
</style>