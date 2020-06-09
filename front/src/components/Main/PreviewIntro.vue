<template>
  <div class="preview-intro-wrapper">
    <div class="preview-intro">
      <div style="font-size:calc( 1rem + 1vw )">실시간 미리보기</div>
      <small style="font-size:calc(0.8rem + 0.1vw)">현재까지 작성된 코드의 결과물을 실시간으로 미리볼 수 있습니다.</small>
    </div>
    <div class="preview-image-section">
      <div class="code-img">
        <img src="../../assets/images/mainpage/preview_html.jpg" alt="code-img-1" data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" data-aos-delay="700">
        <img src="../../assets/images/mainpage/preview_css.jpg" alt="code-img-2" data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" data-aos-delay="1400">
        <img src="../../assets/images/mainpage/preview_js.jpg" alt="code-img-3" data-aos="fade-down" data-aos-duration="1000" data-aos-once="true" data-aos-delay="2100">
        <img src="../../assets/images/mainpage/preview.jpg" alt="preview-img">
      </div>
      <div class="preview-img" data-aos="fade-right" data-aos-duration="1000" data-aos-once="true" data-aos-delay="2800">
        <img src="../../assets/images/mainpage/preview.jpg" alt="preview-img">
      </div>
    </div>
    <swiper class="mobile-preview-image-swiper" :options="swiperOptions" ref="mySwiper">
      <swiper-slide v-for="(language, idx) in languages" :key="idx">
        <MobilePreviewImg :language="language" :idx="idx"></MobilePreviewImg>
      </swiper-slide>
      <div class="swiper-pagination" slot="pagination"></div>
      <div class="swiper-button-next" slot="navigation"></div>
      <div class="swiper-button-prev" slot="navigation"></div>
    </swiper>
    <div class="mobile-preview-image-swiper mobile-preview-img">
      <img src="../../assets/images/mainpage/preview.jpg" alt="preview-img">
    </div>
  </div>
</template>

<script>
import MobilePreviewImg from '@/components/Main/MobilePreviewImg.vue';

export default {
  name: 'PreviewIntro',
  components: {
    MobilePreviewImg
  },
  data() {
    return {
      languages: ['html', 'css', 'js'],
      swiperOptions: {
        slidesPerView: 1,
        spaceBetween: 3,
        loopFillGroupWithBlank: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
        breakpoints: {
          0: {
            slidesPerView: 1,
            spaceBetween: 0,
          },
          650: {
            slidesPerView: 2,
            spaceBetween: 0,
          },
        }
      },
    }
  },
}
</script>

<style scoped>
.preview-intro {
  text-align: center;
}

.preview-intro > div {
  display: inline-block;
  font-size: 2rem;
  font-family: 'Noto Sans KR';
  font-weight: 600;
  padding-bottom: 6px;
  margin-bottom: 8px;
  border-bottom: 1px solid silver;
}

.preview-intro > small {
  display: block;
  width: 90%;
  margin: 0 auto 20px;
}

.preview-image-section {
  position: relative;
  display: flex;
  justify-content: center;
  margin: 0 auto;
  width: 90%;
}

.code-img,
.preview-img {
  flex-grow: 1;
}

.code-img > img:first-child {
  position: absolute;
  top: 0;
  left: 0;
  width: 38%;
  max-width: 490px;
  max-height: 370px;
}

.code-img > img:nth-child(2) {
  position: absolute;
  top: 10%;
  left: 6%;
  width: 38%;
  max-width: 490px;
  max-height: 370px;
}

.code-img > img:nth-child(3) {
  position: absolute;
  top: 20%;
  left: 12%;
  width: 38%;
  max-width: 490px;
  max-height: 370px;
}

.code-img > img:nth-child(n+4) {
  display: block;
  margin: 0 auto;
  width: 600px;
  visibility: hidden;
}

.preview-img > img {
  display: block;
  margin: 0 auto;
  width: 600px;
  text-align: center;
}

.mobile-preview-image-swiper {
  display: none;
}

.swiper-pagination {
  bottom: 0;
}

.swiper-pagination-bullet {
  width: 20px;
  height: 20px;
  text-align: center;
  line-height: 20px;
  font-size: 12px;
  color:#000;
  opacity: 1;
  margin: 0 20px;
  background: rgba(0,0,0,0.2);
}

.swiper-pagination-bullet-active {
  color:#fff;
  background: #007aff;
}

.swiper-slide {
  text-align: center;
  font-size: 18px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-align-items: center;
  align-items: center;
}

@media (max-width: 900px) {
  .preview-image-section {
    display: none;
  }

  .mobile-preview-image-swiper {
    display: block;
    width: 80%;
    margin-bottom: 20px;
  }

  .mobile-preview-img {
    margin: 0 auto;
    max-width: 600px;
  }
}
</style>