// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        const formData = new FormData();
        formData.append('title' , document.getElementById('title').value)
        formData.append('content' , document.getElementById('content').value)
        formData.append('category' , document.getElementById('category').value)


        const inputFile = document.getElementById('customFile');
        formData.append('files', inputFile.files[0]);

        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('POST', '/api/articles', formData, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let boardNo = document.getElementById('article-id').value;
        const formData = new FormData();
        formData.append('title' , document.getElementById('title').value)
        formData.append('content' , document.getElementById('content').value)
        formData.append('category' , document.getElementById('category').value)

        const inputFile = document.getElementById('customFile');
        formData.append('files', inputFile.files[0]);

        function success() {
            alert('수정 완료되었습니다.');
            location.replace("/articles/" + boardNo);
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace("/articles/" + boardNo);
        }

        httpRequest('PUT',"/api/articles/" + boardNo, formData, success, fail);
    });
}


// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('DELETE',`/api/articles/${id}`, null, success, fail);
    });
}



// 쿠키를 가져오는 함수
function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        let dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            // 'Content-Type': 'multipart/form-data',
            // 'Content-Type':o 'application/jsn',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }

        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    // 'Content-Type': 'multipart/form-data',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => {
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}