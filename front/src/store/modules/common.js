const state = {
  mode: 'dark'
}

const mutations = {
  // 공통 부분에서 색 바뀌는 건 아래에 작성
  // 각 페이지마다 그 페이지에서만 사용하는 태그가 있는 경우 해당 컴포넌트에서 watch를 이용해서 작성
  toggleMode(state) {
    state.mode = state.mode === 'dark' ? 'white' : 'dark';
    document.querySelector('#search').style.backgroundColor = state.mode === 'white' ? '#eee' : '#252830'; // header 검색창
    if (state.mode === 'white') {
      document.querySelector('html').style.color = 'black';
      document.querySelectorAll('a').forEach(tag => {
        tag.style.color = 'black'
      });
      document.querySelectorAll('button').forEach(button => {
        button.style.color = 'black'
      });
      document.querySelector('.sidebar-wrapper').style.backgroundColor = '#eee';
      document.querySelector('.header-wrapper').style.backgroundColor = '#ddd';
      document.querySelector('.mobile-menu').style.backgroundColor = '#ddd';
      document.querySelector('#page-view').style.backgroundColor = '#ddd';
    } else {
      document.querySelector('html').style.color = '#eee';
      document.querySelectorAll('a').forEach(tag => {
        tag.style.color = '#eee'
      });
      document.querySelector('.sidebar-wrapper').style.backgroundColor = '#2c303a';
      document.querySelector('.header-wrapper').style.backgroundColor = '#1E1E22';
      document.querySelector('.mobile-menu').style.backgroundColor = '#1E1E22';
      document.querySelector('#page-view').style.backgroundColor = '#1E1E22';
    }
  }
}

export default {
  state,
  mutations
}