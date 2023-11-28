function showEditForm(commentId) {
    // 수정하기 버튼을 눌렀을 때 해당 commentId의 edit-form을 보여줌
    if(!document.getElementById(`edit-form-${commentId}`).classList.contains('d-none')){
        document.getElementById(`edit-form-${commentId}`).classList.add('d-none');
    }else {
        document.getElementById(`edit-form-${commentId}`).classList.remove('d-none');
    }
}
function hideEditForm(commentId) {
    // 취소 버튼을 눌렀을 때 해당 commentId의 edit-form을 숨김
    document.getElementById(`edit-form-${commentId}`).classList.add('d-none');
}
// //수정하기
// document.addEventListener('DOMContentLoaded', function (){
//     //.update-comment-btn 클래스를 가진 버튼들을 editButtons에 할당
//     const editButtons = document.querySelectorAll('.update-comment-btn');
//     editButtons.forEach(function (button){
//         button.addEventListener('click', function (){
//             const editForm = button.nextElementSibling;
//             editForm.classList.toggle('d-none');
//             }
//         )
//     })
// })
//
//
//
//
// //대댓글
// document.addEventListener('DOMContentLoaded', function () {
//     const replyButtons = document.querySelectorAll('.reply-btn');
//     replyButtons.forEach(function (button) {
//         button.addEventListener('click', function () {
//             const replyForm = button.nextElementSibling;
//             replyForm.classList.toggle('d-none');
//         });
//     });
//
//     // "답글 등록" 버튼 클릭 시 백엔드로 전송하도록 핸들러를 추가합니다.
//     const submitReplyButtons = document.querySelectorAll('.submit-reply');
//     submitReplyButtons.forEach(function (button) {
//         button.addEventListener('click', function () {
//             const replyForm = button.closest('.reply-form');
//             const replyCommentInput = replyForm.querySelector('#replyComment');
//             const replyComment = replyCommentInput.value;
//
//             // 여기에서 AJAX 또는 기타 방법으로 백엔드로 답글 전송
//
//             // 답글 작성 폼 감추기
//             replyForm.classList.add('d-none');
//         });
//     });
// });