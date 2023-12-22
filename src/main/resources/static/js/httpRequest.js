// function sendHttpRequest(){
//     const token = getToken();
//
//     const request = new XMLHttpRequest();
//     request.open('GET', '/', true)
//     request.setRequestHeader('Authorization', 'Bearer ' + token)
//
//     request.onreadystatechange = function (){
//         if(request.readyState === 4 && request.status === 200){
//             let response = JSON.parse(request.responseText);
//         }
//     };
//     request.send();
// }