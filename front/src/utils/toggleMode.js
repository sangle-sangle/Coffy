function toggleMode(mode) {
  if (mode === 'white') {
    document.querySelector('html').style.color = 'black';
    document.querySelectorAll('a').forEach(tag => {
      tag.style.color = 'black'
    });
    document.querySelectorAll('button').forEach(button => {
      button.style.color = 'black'
    });
    document.querySelector('#search').style.backgroundColor = '#eee';
    document.querySelector('.sidebar-wrapper').style.backgroundColor = '#eee';
    document.querySelector('.header-wrapper').style.backgroundColor = '#ddd';
    document.querySelector('.mobile-menu').style.backgroundColor = '#ddd';
    document.querySelector('#page-view').style.backgroundColor = '#ddd';
    document.querySelector('.footer-wrapper').style.backgroundColor = '#eee';
  } else {
    document.querySelector('html').style.color = '#eee';
    document.querySelectorAll('a').forEach(tag => {
      tag.style.color = '#eee'
    });
    document.querySelectorAll('button').forEach(button => {
      button.style.color = 'white'
    });
    document.querySelector('#search').style.backgroundColor = '#252830';
    document.querySelector('.sidebar-wrapper').style.backgroundColor = '#2c303a';
    document.querySelector('.header-wrapper').style.backgroundColor = '#1E1E22';
    document.querySelector('.mobile-menu').style.backgroundColor = '#1E1E22';
    document.querySelector('#page-view').style.backgroundColor = '#1E1E22';
    document.querySelector('.footer-wrapper').style.backgroundColor = '#000';
  }
}

export { toggleMode }