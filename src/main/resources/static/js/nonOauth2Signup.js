const signup = document.getElementById('signup-btn');

if (signup) {
    signup.addEventListener("click", ev => {

        fetch('/users',
            {
                method: 'POST',
                headers:{
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: document.getElementById("signupEmail").value,
                    name: document.getElementById("signupName").value,
                    nickName: document.getElementById("signupNickname").value,
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