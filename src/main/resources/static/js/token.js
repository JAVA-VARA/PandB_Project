const token = searchParam('token')
//url에 "token"이라는 쿼리 매개변수의 값을 가져와 token 변수에 할당

//token 값이 있다면
if(token){
    //localStorage에 "access_token"의 키로 token 값을 저장한다.
    localStorage.setItem("access_token", token)

    // 쿠키에도 "access_token"의 키로 token 값을 저장
    document.cookie = "access_token=" + token + ";path=/";


}

//searchParam 함수 정의
//key 라는 매개 변수를 받아서
function searchParam(key){
    //key값을 담고 있는 객체를 생성한다.
    return new URLSearchParams(location.search).get(key);

}

function getToken(){
    return localStorage.getItem("access_token");
}