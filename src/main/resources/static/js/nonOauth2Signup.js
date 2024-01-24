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


// const emailInput = document.getElementById("signupEmail");
// const emailError = document.getElementById("emailError");
// const emailValue = emailInput.value.trim();

// if (!emailValue) {
//     emailError.textContent = "이메일을 입력하세요.";
//     return;
// }
//
// // 이메일 형식 검증
// const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
// if (!emailRegex.test(emailValue)) {
//     emailError.textContent = "올바른 이메일 형식이 아닙니다.";
//     return;
// }
//
// //이름
// const nameInput = document.getElementById("signupName")
// const nameError = document.getElementById("nameError");
// const nameValue = nameInput.value.trim();
//
// if (!nameValue) {
//     nameError.textContent = "이름을 입력하세요.";
//     return;
// }
//
// const nicknameInput = document.getElementById("signupNickname")
// const nicknameError = document.getElementById("nicknameError");
// const nicknameValue = nicknameInput.value.trim();
//
// if (!nicknameValue) {
//     nicknameError.textContent = "닉네임을 입력하세요.";
//     return;
// }