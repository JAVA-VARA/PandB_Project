// const oAuth2SignupButton = document.getElementById('Oauth2-signup-btn');

const oAuth2SignupForm = document.getElementById('additionalInfoForm');



if(oAuth2SignupForm){
    oAuth2SignupForm.addEventListener("submit", event =>{
        event.preventDefault();

        const accessToken = localStorage.getItem('access_token');



        fetch('/submit-additional-info',
            {method: 'PUT',
                headers:{
                    Authorization: 'Bearer ' + accessToken,
                    "Content-Type": "application/json",
                },
                body:JSON.stringify({
                    name: document.getElementById("name").value,
                    nickname:document.getElementById("nickname").value,
                    hp:document.getElementById("hp").value,
                    babyDue:document.getElementById("babyDue").value
                })
            })
            .then(response=>{
                if(response.status === 200 || response.status === 201){
                    alert('회원 가입이 완료되었습니다.');
                    location.replace("/homePage")
                }else{
                    alert('회원 가입이 오류입니다. 다시 시도해주세요');
                }

            })

    })
}
