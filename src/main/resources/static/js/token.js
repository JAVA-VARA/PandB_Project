const token = searchParam('token')

if(token){
    localStorage.setItem("access_token", token)
}

function searchParam(key){
    return new URLSearchParams(loacation.search).get(key);
}