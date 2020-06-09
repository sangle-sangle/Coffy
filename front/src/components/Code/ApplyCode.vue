<template>
  <div class="wrapper">
    <iframe :id="idTag" frameborder="0" class="code-apply" />
  </div>
</template>

<script>
  export default {
    name: 'applycode',
    props: {
      code: Object,
      idTag: String,
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
      window.addEventListener('resize', () => {
        if (this.$route.name.includes('Code')) {
          this.adjustPreviewHeight()
        }
      }, { passive: true });
    },
    methods: {
      applyCode() {
        const getGeneratedPageURL = ({ html, css, js }) => {
          const getBlobURL = (code, type) => {
            const blob = new Blob([code], { type })
            return URL.createObjectURL(blob)
          }

          const cssURL = getBlobURL(css, 'text/css')
          const jsURL = getBlobURL(js, 'text/javascript')
          const source = 
          `
            <html>
              <head>
                ${css && `<link rel="stylesheet" type="text/css" href="${cssURL}" />`}
              </head>
              <body>
                ${html || ''}
              </body>
              ${js && `<script src="${jsURL}"></s`+ `cript>`}
            </html>
          `

          return getBlobURL(source, 'text/html')
        }

        const url = getGeneratedPageURL({
          html: this.code.htmlText,
          css: this.code.cssText,
          js: this.code.jsText
        })

        const iframe = document.querySelector(`#${this.idTag}`)
        iframe.src = url

      },
      adjustPreviewHeight() {
        this.windowWidth = window.innerWidth
        if (['Main', 'DashBoard', 'Search'].includes(this.$route.name)) {
          document.querySelector('.wrapper').style.height = '100%'
        } else if (this.$route.name !== 'CodeList' && !this.colLayout) {
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
        handler() {
          if (this.$route.name.includes('Code')) {
            this.applyCode()
          }
        }
      },
      colLayout() {
        if (this.$route.name.includes('Code')) {
          this.adjustPreviewHeight();
        }
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