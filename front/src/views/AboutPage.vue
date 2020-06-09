<template>
  <div class="about">
    <div class="about-title" data-aos="zoom-in">☕Coffy Introduction</div>
    <ServiceIntro data-aos="fade-right" data-aos-offset="500" data-aos-once="true"></ServiceIntro>
    <TeamIntro data-aos="fade-right" data-aos-offset="500" data-aos-once="true"></TeamIntro>
  </div>
</template>

<script>
import ServiceIntro from '@/components/About/ServiceIntro.vue';
import TeamIntro from '@/components/About/TeamIntro.vue';

export default {
  components: {
    ServiceIntro,
    TeamIntro,
  },
  methods: {
    goPage(url) {
      this.$router.push(url);
    }
  }
}
</script>

<style scoped>
.about {
  padding: 15px;
}

.about-title {
  display: inline-block;
  font-size: calc(1.2rem + .5vw);
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 6px;
  margin-bottom: 20px;
  border-bottom: 1px solid #aaa;
}
</style>