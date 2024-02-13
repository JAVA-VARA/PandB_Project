// //댓글 달기 기능
// const addCommentButton = document.getElementById("create-comment-btn");
// if (addCommentButton) {
//     addCommentButton.addEventListener('click', ev => {
//         let id = document.getElementById('article-id').value;
//
//         // const formData = new FormData();
//         // formData.append('content', document.getElementById('comment').value)
//
//         // body =  JSON.stringify({
//         //     content: document.getElementById('comment').value
//         // })
//
//         fetch(`/api/articles/comment/${id}`,
//             {
//                 method: 'POST',
//                 headers: {
//                     "Content-Type": "application/json",
//                 },
//                 body:
//                     JSON.stringify({
//                         content: document.getElementById('comment').value
//
//                     })
//             })
//             .then(response => {
//                 if (response.status === 200 || response.status === 201) {
//                     alert('댓글이 등록 되었습니다.');
//                     location.replace("/articles/" + id);
//                 } else {
//                     alert('댓글 등록 실패했습니다.');
//                     location.replace("/articles/" + id);
//                 }
//             })
//     })
// }
//
// //댓글 수정
// const modifyCommentButtons = document.getElementsByClassName('submit-updatedComment');
// if (modifyCommentButtons) {
//     for (const button of modifyCommentButtons) {
//
//
//         button.addEventListener('click', event => {
//             let boardNo = document.getElementById('article-id').value;
//
//             // let commentId = document.getElementById('comment-id').value; //이게 무조건 1로 들어갑니다 => 동일한 id가 여러개라서 맨위에 것만 적용됨.
//             let commentId = button.id;
//
//             const formData = new FormData();
//             formData.append('content', document.getElementById(`editComment-${commentId}`).value);
//
//             function success() {
//                 alert('수정 완료되었습니다.');
//                 location.replace("/articles/" + boardNo);
//             }
//
//             function fail() {
//                 alert('수정 실패했습니다.');
//                 location.replace("/articles/" + boardNo);
//             }
//
//             httpRequest('PUT', `/api/articles/comment/${commentId}`, formData, success, fail)
//         })
//     }
// }
//
// //댓글 삭제
// const deleteCommentButtons = document.getElementsByClassName('comment-delete-btn');
// if (deleteCommentButtons) {
//     for (const deleteButton of deleteCommentButtons) {
//
//         deleteButton.addEventListener('click', event => {
//             let boardNo = document.getElementById('article-id').value;
//             let commentId = deleteButton.value;
//
//             function success() {
//                 alert('삭제가 완료되었습니다.');
//                 location.replace("/articles/" + boardNo);
//             }
//
//             function fail() {
//                 alert('삭제 실패했습니다.');
//                 location.replace("/articles/" + boardNo);
//             }
//
//             httpRequest('DELETE', `/api/articles/comment/${commentId}`, null, success, fail);
//
//         })
//
//     }
// }
//
//
// // HTTP 요청을 보내는 함수
// function httpRequest2(method, url, body, success, fail) {
//     fetch(url, {
//         method: method,
//         headers: {
//             Authorization: 'Bearer ' + localStorage.getItem('access_token'),
//             "Content-Type": "application/json",
//         },
//         body: body,
//     }).then(response => {
//         if (response.status === 200 || response.status === 201) {
//             return success();
//         }
//
//         const refresh_token = getCookie('refresh_token');
//         if (response.status === 401 && refresh_token) {
//             fetch('/api/token', {
//                 method: 'POST',
//                 headers: {
//                     Authorization: 'Bearer ' + localStorage.getItem('access_token'),
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify({
//                     refreshToken: getCookie('refresh_token'),
//                 }),
//             })
//                 .then(res => {
//                     if (res.ok) {
//                         return res.json();
//                     }
//                 })
//                 .then(result => {
//                     localStorage.setItem('access_token', result.accessToken);
//                     httpRequest(method, url, body, success, fail);
//                 })
//                 .catch(error => fail());
//         } else {
//             return fail();
//         }
//     });
// }