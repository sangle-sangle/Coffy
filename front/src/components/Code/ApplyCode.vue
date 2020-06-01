<template>
  <iframe frameborder="0" class="code-apply" />
</template>

<script>
export default {
  name: 'applycode',
  props: {
    code: Object
  },
  computed: {
    vhtml() {
      return this.code.htmlText + '<style>' + this.code.cssText + '</style>' + '\n<script>' + this.code.jsText + '<' +
        '/script>'
    }
  },
  methods: {
    applyCode() {
      let doc = document.getElementsByClassName('code-apply')[0].contentDocument
      doc.open()
      doc.write(this.vhtml)
      doc.close()
    }
  },
  watch: {
    code: {
      deep: true,
      handler: 'applyCode'
    }
  },
  mounted() {
    this.applyCode()
  },
}
</script>

<style>
</style>