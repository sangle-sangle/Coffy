const state = {
  mode: 'dark'
}

const mutations = {
  toggleMode(state) {
    state.mode = state.mode === 'dark' ? 'white' : 'dark'
    if (state.mode === 'white') {
      document.querySelector('html').style.color = 'black';
      document.querySelectorAll('a').forEach(tag => {
        tag.style.color = 'black'
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