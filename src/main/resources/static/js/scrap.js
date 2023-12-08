const scrapButton = document.getElementById('scrap-btn');

if(scrapButton){
    scrapButton.addEventListener("click", ev => {

        let id = document.getElementById('article-id').value

        fetch(`/api/articles/scrap/${id}`,
            {method: "GET",
                headers:{
                "Content-Type": "application/json"},
                body: null

            })
            .then(response =>{
                if(response.ok){
                    alert('게시글이 저장되었습니다. \n 저장된 게시글은 마이페이지 스크렙 화면에서 확인 가능합니다.');
                    location.replace(`/articles/${id}`)
                }else if(response.status === 500){
                    alert("이미 스크랩하신 글입니다.")
                }
            })
            .catch(error =>{
                alert("네트워크 오류 또는 서버 오류가 발생했습니다.")
            })
    })
}