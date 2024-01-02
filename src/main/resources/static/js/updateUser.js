// const updateButton = document.getElementById('userInfo-update-btn');

const userUpdateForm = document.getElementById("user-update-form");

if(userUpdateForm){
    userUpdateForm.addEventListener("submit", event =>{
        event.preventDefault();

        const passwordInput = document.getElementById("password").value;
        const passwordConfirmInput = document.getElementById("confirmPassword").value;
        const mypagePasswordError = document.getElementById("mypagePasswordError");
        if(passwordInput !== passwordConfirmInput) {
            mypagePasswordError.textContent = "비밀번호가 일치하지 않습니다.";
            return;
        }

        fetch('/update/users',
            {method: 'PUT',
                headers:{
                    "Content-Type": "application/json",
                },
                body:JSON.stringify({
                    nickname:document.getElementById("nickname").value,
                    hp:document.getElementById("hp").value,
                    password: document.getElementById("password").value,
                    babyDue:document.getElementById("babyDue").value
                })
            })
            .then(()=>{
                alert('회원 정보가 변경되었습니다.');
                location.replace("/mypage/my-info")
            })

    })
}