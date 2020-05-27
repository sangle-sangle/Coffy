<template>
  <div class="modal">
    <div id="slot-modal">
      <slot v-if="this.showModal"></slot>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  props: {
    showModal: {
      type: Boolean,
      default: true
    }
  },
   computed: {
    ...mapState({
      mode: state => state.common.mode,
    })
  },
  mounted() {
    this.toggleModal();
    // this.$store.commit('toggleMode');
    this.changeColor(this.mode);
  },
  methods: {
    toggleModal() {
      document.querySelector('.modal').style.opacity = this.showModal ? 1 : 0;
      document.querySelector('.modal').style.pointerEvents = this.showModal ? 'auto' : 'none';
    },
    changeColor(mode) {
      if (mode === 'white') {
        document.querySelector('#slot-modal').style.backgroundColor = '#eee';
      } else {
        document.querySelector('#slot-modal').style.backgroundColor = '#2c303a';
      }
    },
  },
  watch: {
    showModal() {
      this.toggleModal()
    },
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
  pointer-events: none;
  opacity: 0;
  transition: all .2s;
  z-index: 4;
  display: flex;
  justify-content: center;
  align-items: center;
}

#slot-modal {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.8);
  max-width: 800px;
  margin: 25px;
}
</style>