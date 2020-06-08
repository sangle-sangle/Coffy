<template>
  <div>
    <MainBanner data-aos="fade-down" data-aos-duration="1000" data-aos-once="true"></MainBanner>
    <PreviewIntro data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" class="main-component"></PreviewIntro>
    <BestTemplates data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" class="main-component"></BestTemplates>
    <ProvideLang data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" class="main-component"></ProvideLang>
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
  },
}
</script>

<style scoped>
.main-component {
  padding: 30px 0;
}
</style>