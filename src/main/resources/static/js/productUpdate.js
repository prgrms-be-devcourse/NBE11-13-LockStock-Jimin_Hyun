let selectedFile = null;

$(document).ready(() => {
    loadProductDetail();
    updateProduct();
    fileChanged();
});

let updateProduct = () => {
    // 1. ID를 updateSubmitBtn으로 수정
    $('#updateSubmitBtn').on('click', (event) => {
        event.preventDefault();

        let hId = $('#hiddenId').val();
        // 2. 폼 ID를 updateForm으로 수정
        let formData = new FormData($('#updateForm')[0]);

        if (selectedFile) {
            formData.append('file', selectedFile);
            deleteExistingFile();
        }

        $.ajax({
            type: 'PUT',
            url: '/api/admin/products/' + hId,
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert('상품 정보가 수정되었습니다!');
                window.location.href = '/products/detail/' + hId;
            },
            error: function(error) {
                console.error('오류 발생:', error);
                alert('상품 수정 중 오류가 발생하였습니다.');
            }
        });
    });
}

let fileChanged = () => {
    $('#file').on('change', function(e) {
        const file = e.target.files[0];
        if (file && file.type.startsWith('image/')) {
            selectedFile = file;
            $('#hiddenFileFlag').val(true); // 파일 변경됨
            updateFileList();
        } else {
            alert("이미지 파일만 선택 가능합니다.");
            $('#file').val('');
        }
    });
}

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
            $('#hiddenFileFlag').val(true); // 파일을 삭제했으므로 서버에서 파일 삭제 로직 실행
            updateFileList();
        });
    }
}

let loadProductDetail = () => {
    let hId = $('#hiddenId').val();
    $.ajax({
        type: 'GET',
        url: '/api/products/detail/' + hId,
        success: (response) => {
            $('#name').val(response.name);
            $('#price').val(response.price);
            $('#contents').val(response.contents);
            $('#stockQuantity').val(response.stockQuantity);

            // 기존 파일이 있을 경우
            if (response.thumbnailPath) {
                // [추가] 경로를 hidden input에 저장 (서버로 전송하기 위함)
                $('#originalThumbnailPath').val(response.thumbnailPath);

                let fileName = response.thumbnailPath.split('/').pop();
                $('#fileList').append(`
                    <li>
                        현재 파일: ${fileName} <button type="button" class="remove-btn">X</button>
                    </li>
                `);

                $('.remove-btn').on('click', function () {
                    selectedFile = null;
                    $('#hiddenFileFlag').val(true); // 삭제 의사 표시
                    $('#file').val('');
                    $('#fileList').empty().append('<li>파일이 제거되었습니다.</li>');
                });
            } else {
                $('#fileList').append('<li>첨부된 이미지가 없습니다.</li>');
            }
        },
        error: (error) => {
            alert('데이터를 불러오는데 실패했습니다.');
        }
    });
}

let deleteExistingFile = (path) => {
    // 삭제할 파일 경로를 DTO 구조에 맞게 전송
    $.ajax({
        type: 'DELETE',
        url: '/api/admin/products/file', // 파일 삭제 전용 API
        contentType: 'application/json',
        data: JSON.stringify({ thumbnailPath: path }),
        success: () => {
            console.log('기존 파일 서버에서 삭제됨');
        },
        error: (err) => {
            console.error('파일 삭제 실패:', err);
        }
    });
};