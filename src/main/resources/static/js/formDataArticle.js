// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        const formData = new FormData();

        //수정 후
        const inputFile = document.getElementById('customFile');
        const files = inputFile.files;

        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }

        formData.append('title', document.getElementById('title').value)
        formData.append('content', document.getElementById('content').value)
        formData.append('category', document.getElementById('category').value)


        function success() {
            alert('등록 완료되었습니다.');
            window.history.back();
            // location.replace('/boardList');
        }

        function fail() {
            alert('등록 실패했습니다.');
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
        formData.append('title', document.getElementById('title').value)
        formData.append('content', document.getElementById('content').value)
        formData.append('category', document.getElementById('category').value)

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

        httpRequest('PUT', "/api/articles/" + boardNo, formData, success, fail);
    });
}


// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            window.history.back();
        }

        function fail() {
            alert('삭제 실패했습니다.');
            // location.replace('/boardList');
        }

        httpRequest('DELETE', `/api/articles/${id}`, null, success, fail);
    });
}

//댓글 달기 기능

const addCommentButton = document.getElementById("create-comment-btn");
if (addCommentButton) {
    addCommentButton.addEventListener('click', ev => {
        let id = document.getElementById('article-id').value;

        const formData = new FormData();
        formData.append('content', document.getElementById('comment').value)

        function success() {
            alert('댓글 등록 완료되었습니다.');
            location.replace("/articles/" + id);
        }

        function fail() {
            alert('댓글 등록 실패했습니다.');
            location.replace("/articles/" + id);
        }

        httpRequest('POST', `/api/articles/comment/${id}`, formData, success, fail)
    })
}

//댓글 수정
const modifyCommentButtons = document.getElementsByClassName('submit-updatedComment');
if (modifyCommentButtons) {
    for (const button of modifyCommentButtons) {


        button.addEventListener('click', event => {
            let boardNo = document.getElementById('article-id').value;

            // let commentId = document.getElementById('comment-id').value; //이게 무조건 1로 들어갑니다 => 동일한 id가 여러개라서 맨위에 것만 적용됨.
            let commentId = button.id;

            const formData = new FormData();
            formData.append('content', document.getElementById(`editComment-${commentId}`).value);

            function success() {
                alert('수정 완료되었습니다.');
                location.replace("/articles/" + boardNo);
            }

            function fail() {
                alert('수정 실패했습니다.');
                location.replace("/articles/" + boardNo);
            }

            httpRequest('PUT', `/api/articles/comment/${commentId}`, formData, success, fail)
        })
    }
}

//댓글 삭제
const deleteCommentButtons = document.getElementsByClassName('comment-delete-btn');
if(deleteCommentButtons){
    for(const deleteButton of deleteCommentButtons){

        deleteButton.addEventListener('click' , event=> {
            let boardNo = document.getElementById('article-id').value;
            let commentId = deleteButton.value;

            function success() {
                alert('삭제가 완료되었습니다.');
                location.replace("/articles/" + boardNo);
            }

            function fail() {
                alert('삭제 실패했습니다.');
                location.replace("/articles/" + boardNo);
            }

            httpRequest('DELETE', `/api/articles/comment/${commentId}`, null, success, fail);

        })

    }
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