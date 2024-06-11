// const signup = document.getElementById('signup-btn');
const signupForm = document.getElementById('signupForm');

if (signupForm) {
    signupForm.addEventListener("submit", ev => {
        ev.preventDefault();

        const passwordInput = document.getElementById("signupPassword").value;
        const passwordConfirmInput = document.getElementById("signupPasswordConfirm").value;
        const passwordError = document.getElementById("passwordError");

        if(passwordInput !== passwordConfirmInput) {
            passwordError.textContent = "비밀번호가 일치하지 않습니다.";
            return;
        }

        fetch('/users',
            {
                method: 'POST',
                headers:{
                    "Content-Type": "application/json",
                },
                body:
                    JSON.stringify({
                    email: document.getElementById("signupEmail").value,
                    name: document.getElementById("signupName").value,
                    nickname: document.getElementById("signupNickname").value,
                    hp: document.getElementById("signupHp").value,
                    babyDue: document.getElementById("signupBabyDue").value,
                    password: document.getElementById("signupPassword").value
                })
            })
            .then(response => {
                if(response.status ===200 || response.status === 201){
                    alert('회원 가입이 완료되었습니다.');
                    location.replace("/login")
                }else {
                    alert('회원 가입이 오류입니다. 다시 시도해주세요');
                }
            })
    });
}
