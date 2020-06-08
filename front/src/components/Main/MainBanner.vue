<template>
  <div class="main-banner-wrapper">
    <div class="main-banner-img"></div>
    <div class="service-intro-message">
      <h1>Coffy</h1>
      <small>Frontend Code Sharing Service</small>
      <p>Write your own nice frontend code.</p>
    </div>
  </div>
</template>

<style scoped>
.main-banner-wrapper {
  position: relative;
}

.main-banner-img {
  background-image: url('../../assets/images/mainpage/main.jpg');
  background-size: cover;
  background-position: center;
  height: 440px;
  overflow: hidden;
  filter: brightness(65%);
}

.service-intro-message {
  position: absolute;
  top: 220px;
  left: 5%;
  transform: translateY(-50%);
  z-index: 2;
  color: #eee;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

h1 {
  display: inline-block;
  font-size: 4rem;
  padding-bottom: 6px;
  margin-bottom: 6px;
  border-bottom: 2px solid silver;
}

small {
  display: block;
  margin-bottom: 36px;
}

p {
  font-size: 1.6rem;
}

@media (max-width: 600px) {
  .main-banner-img {
    height: 300px;
  }

  .service-intro-message {
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    text-align: center;
  }

  h1 {
    font-size: 3.5rem;
  }

  small {
    margin-bottom: 24px;
  }

  p {
    font-size: 1.2rem;
    width: 90%;
    margin: 0 auto;
  }
}
</style>