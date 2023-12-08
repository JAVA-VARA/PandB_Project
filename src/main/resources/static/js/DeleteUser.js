const deleteUserButton = document.getElementById('user-delete-btn')

if(deleteUserButton){
    deleteUserButton.addEventListener('click', ev => {

        const confirmButton = confirm("정말로 탈퇴하시겠습니까?");

        if(confirmButton){
            fetch('/delete/users',
                {
                    method: 'DELETE',
                    body: null
                })
                .then(r => {
                    alert("회원 탈퇴가 정상적으로 진행되었습니다.");
                        location.replace("/homePage")
                })
        }else {
            alert("회원 탈퇴가 취소되었습니다.");
        }
    })
}