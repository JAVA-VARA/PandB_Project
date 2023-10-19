// 조회수 기능
const viewLink = document.getElementById('view-btn');

if (viewLink) {
    viewLink.addEventListener('click', event => {
        let id = document.getElementById('view-id').value;

        httpRequest('GET',`/api/articles/view/${id}`);
    });
}