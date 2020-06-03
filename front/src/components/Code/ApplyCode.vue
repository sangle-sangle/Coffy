<template>
  <div class="wrapper">
    <iframe :id="idtag" frameborder="0" class="code-apply" />
  </div>
</template>

<script>
  export default {
    name: 'applycode',
    props: {
      code: Object,
      idtag: String,
      colLayout: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        windowWidth: 0
      }
    },
    computed: {
      vhtml() {
        return this.code.htmlText + '<style>' + this.code.cssText + '</style>' + '\n<script>' + this.code.jsText + '<' +
          '/script>'
      }
    },
    mounted() {
      this.applyCode();
      this.adjustPreviewHeight();
      window.addEventListener('resize', () => this.adjustPreviewHeight());
    },
    methods: {
      applyCode() {
        let doc = document.querySelector(`#${this.idtag}`).contentDocument
        doc.open()
        doc.write(this.vhtml)
        doc.close()
      },
      adjustPreviewHeight() {
        this.windowWidth = window.innerWidth
        if (this.$route.name !== 'CodeList' && !this.colLayout) {
          document.querySelector('.wrapper').style.height = '450px';
        } else if (this.colLayout && this.windowWidth < 600) {
          document.querySelector('.wrapper').style.height = '450px';
        } else if (this.colLayout && this.windowWidth >= 600) {
          document.querySelector('.wrapper').style.height = '100%';
        }
      },
    },
    watch: {
      code: {
        deep: true,
        handler: 'applyCode'
      },
      colLayout() {
        this.adjustPreviewHeight();
      }
    }
  }
</script>

<style>
.wrapper {
  height: 100%;
}
.code-apply{
  width: 100%;
  height: inherit;
}
</style>