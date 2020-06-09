<template>
  <div>
    <div class="service-intro" id="service-intro">Coffy에서 즐길 수 있는 컨텐츠들을 소개합니다.</div>
    <div class="container">
      <div class="card">
        <div class="imgBx" data-text="Live Preview">
          <img src="../../assets/images/about/code.png" alt="code">
        </div>
        <div class="content">
          <div>
            <h2>Live Preview</h2>
            <p>HTML, CSS, Javascript 언어로 제작한 템플릿의 결과물을 실시간으로 미리 볼 수 있습니다.</p>
            <div class="link-tag" @click="goPage('/code')">Show More</div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="imgBx" data-text="Code Game">
          <img src="../../assets/images/about/joystick.png" alt="game">
        </div>
        <div class="content">
          <div>
            <h2>Code Game</h2>
            <p>간단한 게임을 통해서 CSS 관련 지식을 학습할 수 있습니다.</p>
            <div class="link-tag" @click="goPage('/game')">Show More</div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="imgBx" data-text="Clan">
          <img src="../../assets/images/about/people.png" alt="clan">
        </div>
        <div class="content">
          <div>
            <h2>Clan</h2>
            <p>마음이 맞는 사람들끼리 클랜을 구성해서 정보를 공유할 수 있습니다.</p>
            <div class="link-tag" @click="goPage('/clan')">Show More</div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="imgBx" data-text="Dashboard">
          <img src="../../assets/images/about/dashboard.png" alt="dashboard">
        </div>
        <div class="content">
          <div>
            <h2>Dashboard</h2>
            <p>내가 만들거나 스크랩한 템플릿을 한 곳에 모아서 볼 수 있습니다.</p>
            <div class="link-tag" @click="goPage('/mypage/dashboard')">Show More</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goPage(url) {
      this.$router.push(url);
    }
  }
}
</script>

<style scoped>
.service-intro {
  font-size: calc(0.7rem + 0.3vw);
  font-weight: 600;
  font-family: 'Gothic A1';
  margin-bottom: 10px;
}

.container {
  position: relative;
  display: flex;
  justify-content:space-evenly;
  flex-wrap: wrap;
  margin-bottom: 100px;
}

.container .card {
  position: relative;
  height: 250px;
  background: #fff;
  display: flex;
  width: 45%;
  margin: 30px 0;
}

.container .card .imgBx {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #444;
  z-index: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  transition: 0.5s ease-in-out;
}

.container .card:hover .imgBx {
  width: 100px;
  height: 100px;
  top: -50px;
  transition: 0.5s ease-in-out;
  background: #ff0057;
}

.container .card .imgBx:before {
  content: attr(data-text);
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  text-align: center;
  font-size: 3.5em;
  color: rgba(255, 255, 255, 0.2);
  font-weight: 700;
  transition: color .5s;
}

.container .card:hover .imgBx:before {
  color: transparent;
}

.container .card .imgBx img {
  max-width: 110px;
  transition: 0.5s ease-in-out;
}

.container .card:hover .imgBx img {
  max-width: 70px;
}

.container .card .content {
  position: absolute;
  top: 22%;
  width: 100%;
  padding: 20px;
  color: black;
}

.container .card .content h2 {
  display: inline-block;
  font-size: 28px;
  font-family: 'Gothic A1';
  color: black;
  padding-bottom: 4px;
  margin-bottom: 8px;
  border-bottom: 1px solid #555;
}

.container .card .content p {
  color: black;
  margin: 10px 0;
}

.container .card .content .link-tag {
  display: inline-block;
  margin-top: 10px;
  padding: 5px 10px;
  background:  #333;
  text-decoration: none;
  color: #fff;
  transition: all .2s;
}

.container .card .content .link-tag:hover {
  cursor: pointer;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.15);
}

@media (max-width: 960px) {
  .container {
    width: 100%;
    flex-direction: column;
    align-items: center;
  }

  .container .card {
    width: 500px;
  }
}

@media (max-width: 600px) {
  .container .card {
    width: 100%;
    flex-direction: column;
    height: auto;
  }

  .container .card .imgBx {
    position: relative;
  }

  .container .card .imgBx,
  .container .card:hover .imgBx {
    width: 100%;
    height: 200px;
    left: 0;
  }

  .container .card .imgBx img,
  .container .card:hover .imgBx img {
    max-width: 100px;
  }

  .container .card .content {
    position: relative;
    width: 100%;
  }

  .container .card .imgBx:before {
    font-size: 3em;
  }
}
</style>