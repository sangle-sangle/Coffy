<template>
  <div>
    <MainBanner></MainBanner>
    <PreviewIntro class="main-component"></PreviewIntro>
    <BestTemplates class="main-component"></BestTemplates>
    <ProvideLang class="main-component"></ProvideLang>
  </div>
</template>

<script>
import MainBanner from '@/components/Main/MainBanner.vue';
import PreviewIntro from '@/components/Main/PreviewIntro.vue';
import BestTemplates from '@/components/Main/BestTemplates.vue';
import ProvideLang from '@/components/Main/ProvideLang.vue';

export default {
  components: {
    MainBanner,
    PreviewIntro,
    BestTemplates,
    ProvideLang,
  }
}
</script>

<style scoped>
.main-component {
  padding: 30px 0;
}
</style>