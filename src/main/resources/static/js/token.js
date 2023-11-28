const token = searchParam('token')
//url에 "token"이라는 쿼리 매개변수의 값을 가져와 token 변수에 할당

//token 값이 있다면
if(token){
    localStorage.setItem("access_token", token)
    //localStorage에 "access_token"의 키로 token 값을 저장한다.
}

//searchParam 함수 정의
//key 라는 매개 변수를 받아서
function searchParam(key){
    //key값을 담고 있는 객체를 생성한다.
    // return new URLSearchParams(loacation.search).get(key);
    return new URLSearchParams(location.search).get(key);

}