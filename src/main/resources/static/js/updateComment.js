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
