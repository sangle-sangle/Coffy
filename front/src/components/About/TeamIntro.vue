<template>
  <div>
    <div class="service-intro" id="team-intro">✔️사용하시다가 궁금한 점이 있으시면 저희에게 문의하세요.</div>
    <div class="team-intro">
      <div class="team-card">
        <div class="layer"></div>
        <div class="content">
          <p>우리의 머지맨</p>
          <div class="image">
            <img src="../../assets/images/about/member1.jpg" alt="member1">
          </div>
          <div class="details">
            <h2>김인성<br><span>Project Manager</span></h2>
          </div>
          <div class="sns-icon">
            <i class="fab fa-github" @click="goGithub('jr31112')"></i><i class="far fa-envelope"></i>
          </div>
        </div>
      </div>
      <div class="team-card">
        <div class="layer"></div>
        <div class="content">
          <p>Pro Code Gamer</p>
          <div class="image">
            <img src="../../assets/images/about/member2.jpg" alt="member2">
          </div>
          <div class="details">
            <h2>이승열<br><span>Frontend Developer</span></h2>
          </div>
          <div class="sns-icon">
            <i class="fab fa-github" @click="goGithub('Ssong10')"></i><i class="far fa-envelope"></i>
          </div>
        </div>
      </div>
      <div class="team-card">
        <div class="layer"></div>
        <div class="content">
          <p>CSS 한 땀 한 땀...</p>
          <div class="image">
            <img src="../../assets/images/about/member3.jpg" alt="member3">
          </div>
          <div class="details">
            <h2>심규현<br><span>Frontend Developer</span></h2>
          </div>
          <div class="sns-icon">
            <i class="fab fa-github" @click="goGithub('wally-wally')"></i><a :href="`mailto:${emails[2]}`"><i class="far fa-envelope"></i></a>
          </div>
        </div>
      </div>
      <div class="team-card">
        <div class="layer"></div>
        <div class="content">
          <p>Spring 그 자체</p>
          <div class="image">
            <img src="../../assets/images/about/member4.jpg" alt="member4">
          </div>
          <div class="details">
            <h2>김아현<br><span>Backend Developer</span></h2>
          </div>
          <div class="sns-icon">
            <i class="fab fa-github" @click="goGithub('AhyeonKim')"></i><i class="far fa-envelope"></i>
          </div>
        </div>
      </div>
      <div class="team-card">
        <div class="layer"></div>
        <div class="content">
          <p>데이터 그 자체</p>
          <div class="image">
            <img src="../../assets/images/about/member5.jpg" alt="member5">
          </div>
          <div class="details">
            <h2>이병주<br><span>Backend Developer</span></h2>
          </div>
          <div class="sns-icon">
            <i class="fab fa-github" @click="goGithub('byeongjulee222')"></i><i class="far fa-envelope"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      emails: [
        'jr435@naver.com',
        'leest1403@naver.com',
        'wallys0213@gmail.com',
        'kimah0396@gmail.com',
        'juya1503@gmail.com'
      ]
    }
  },
  methods: {
    goGithub(githubId) {
      window.open(`https://github.com/${githubId}`)
    }
  }
}
</script>

<style scoped>
.service-intro {
  font-size: calc(0.9rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  margin-bottom: 30px;
}

.team-intro {
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(18%, auto));
  grid-gap: 20px;
  margin-bottom: 150px;
}

.team-intro .team-card {
  position: relative;
  width: 100%;
  background: #444;
  padding: 20px;
  box-sizing: border-box;
  text-align: center;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}

.team-intro .team-card .layer {
  position: absolute;
  top: calc(100% - 2px);
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(#03a9f4, #e91ee3);
  z-index: 1;
  transition: 0.5s;
}

.team-intro .team-card:hover .layer {
  top: 0;
}

.team-intro .team-card .content {
  position: relative;
  z-index: 2;
}

.team-intro .team-card .content p {
  font-size: 18px;
  font-style: italic;
  line-height: 24px;
  color: #fff;
  margin: 6px 0;
}

.team-intro .team-card .content .image {
  width: 100px;
  height: 100px;
  margin: 0 auto;
  border-radius: 50%;
  overflow: hidden;
  border: 4px solid #fff;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  transition: border .2s;
}

.team-intro .team-card .content .image:hover {
  border: 4px solid rgb(248, 241, 198);
}

.team-intro .team-card .content .details h2 {
  font-size: 18px;
  color: #fff;
}

.team-intro .team-card .content .details h2 span {
  font-size: 14px;
  color: #03a9f4;
  transition: 0.5s;
}

.team-intro .team-card:hover .content .details h2 span {
  color: #fff;
}

.sns-icon {
  margin-top: 8px;
}

.sns-icon {
  font-size: 20px;
}

.sns-icon i {
  padding: 0 10px;
}

.sns-icon i:hover {
  cursor: pointer;
  font-weight: 600;
}

@media (max-width: 1300px) {
  .team-intro {
    grid-template-columns: repeat(auto-fill, minmax(33.3333%, auto));
  }
}

@media (max-width: 600px) {
  .team-intro {
    grid-template-columns: 1fr;
  }

  .team-intro .team-card {
    width: 100%;
    margin: 0 auto;
  }
}
</style>