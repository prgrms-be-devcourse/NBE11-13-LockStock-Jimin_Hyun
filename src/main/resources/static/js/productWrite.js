let selectedFile = null;

$(document).ready(() => {
    writeProduct();
    fileChanged();
});

// 파일 선택 시 전역 변수에 저장 및 화면 업데이트
let fileChanged = () => {
    $('#file').on('change', function(e) {
        const file = e.target.files[0];
        if (file && file.type.startsWith('image/')) {
            selectedFile = file;
            updateFileList();
        } else {
            alert("이미지 파일만 선택 가능합니다.");
            $('#file').val('');
        }
    });
};

// 파일 목록 UI 업데이트
let updateFileList = () => {
    $('#fileList').empty();
    if (selectedFile) {
        $('#fileList').append(`
            <li>
                ${selectedFile.name} <button type="button" class="remove-btn">X</button>
            </li>
        `);
        $('.remove-btn').on('click', function () {
            selectedFile = null;
            $('#file').val('');
            $('#fileList').empty();
        });
    }
};

// 상품 등록 요청
let writeProduct = () => {
    $('#writeSubmitBtn').on('click', (event) => {
        event.preventDefault();

        // 폼 요소의 데이터를 FormData 객체로 생성
        let formData = new FormData($('#writeForm')[0]);

        // 파일이 선택된 경우에만 FormData에 추가
        if (selectedFile) {
            formData.append('file', selectedFile);
        }

        // Ajax 요청
        $.ajax({
            type: 'POST',
            url: '/api/admin/products/new', // 요청 경로
            data: formData,
            processData: false, // FormData 사용 시 필수
            contentType: false, // FormData 사용 시 필수
            success: (response) => {
                alert('상품이 성공적으로 등록되었습니다!');
                window.location.href = '/products/'; // 목록 페이지로 이동
            },
            error: (error) => {
                console.error('오류 발생:', error);
                alert('상품 등록 중 오류가 발생하였습니다.');
            }
        });
    });
};