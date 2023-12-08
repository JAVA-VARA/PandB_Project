const updateButton = document.getElementById('userInfo-update-btn');

if(updateButton){
    updateButton.addEventListener("click", event =>{

        fetch('/update/users',
            {method: 'PUT',
                headers:{
                "Content-Type": "application/json",
                },
                body:JSON.stringify({
                    nickname:document.getElementById("nickname").value,
                    hp:document.getElementById("hp").value,
                    babyDue:document.getElementById("babyDue").value
                })
            })
            .then(()=>{
                alert('회원 정보가 변경되었습니다.');
                location.replace("/mypage/my-info")
            })

    })
}
