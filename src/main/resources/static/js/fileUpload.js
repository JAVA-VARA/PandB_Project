const inputElement = document.getElementById("customFile");

inputElement.addEventListener("change", handleFiles, false);

function handleFiles(files) {
    const fileLabel = document.getElementById('fileLabel');
    const fileList = document.getElementById('fileList');
    const fileListTitle = document.getElementById('fileListTitle');

    // 선택된 파일의 이름을 표시
    fileLabel.textContent = files[0].name + "외 " + files.length +" 개";

    // 선택된 파일의 목록을 표시 (다중 파일 선택인 경우)
    fileList.innerHTML = '';
    for (const file of files) {
        const listItem = document.createElement('div');
        listItem.textContent = file.name;
        fileList.appendChild(listItem);
    }

    if(files.length <= 0){
        fileListTitle.style.display = 'none';
    }else{
        fileListTitle.style.display = 'block';
    }


}



