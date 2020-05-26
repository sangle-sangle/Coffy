export function validatePassword(password) {
  var check_eng = /[a-z]/g; // 영어 소문자
  var check_num = /[0-9]/g; // 숫자
  if (password.match(check_eng) !== null && password.match(check_num) !== null) {
    return password.match(check_eng).length >= 4 && password.match(check_num).length >= 4
  }
}