<template>
  <div class="img-wrapper">
    <img :src="imgUrl(language)" :alt="`img-${idx}`">
  </div>
</template>

<script>
export default {
  name: 'MobilePreviewImg',
  props: {
    language: {
      type: String
    },
    idx: {
      type: Number
    }
  },
  methods: {
    imgUrl(lang) {
      return require(`../../assets/images/mainpage/preview_${lang}.jpg`)
    }
  }
}
</script>