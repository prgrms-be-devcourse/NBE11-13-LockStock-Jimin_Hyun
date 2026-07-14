$(document).ready(() => {
    const productId = $('.product-detail-container').data('id');

    // 1. 상품 상세 정보 로드
    loadProductDetail(productId);

    // 2. 수량 증감 버튼 이벤트
    $('#plusBtn').on('click', () => {
        const $q = $('#quantity');
        $q.val(parseInt($q.val()) + 1);
    });

    $('#minusBtn').on('click', () => {
        const $q = $('#quantity');
        if (parseInt($q.val()) > 1) {
            $q.val(parseInt($q.val()) - 1);
        }
    });

    // 3. 장바구니 담기
    $('#addCartBtn').on('click', () => {
        const quantity = $('#quantity').val();

        $.ajax({
            type: 'POST',
            url: '/api/cart',
            contentType: 'application/json',
            data: JSON.stringify({
                productId: productId,
                quantity: parseInt(quantity)
            }),
            success: () => {
                if(confirm('장바구니에 담겼습니다. 장바구니로 이동할까요?')) {
                    location.href = '/cart';
                }
            },
            error: (err) => {
                alert('장바구니 담기에 실패했습니다.');
            }
        });
    });
});

// 상세 데이터 로드 함수
function loadProductDetail(id) {
    $.ajax({
        type: 'GET',
        url: `/api/products/detail/${id}`,
        success: (response) => {
            // 데이터 바인딩
            const imgPath = response.thumbnailPath ? `/images/${response.thumbnailPath}` : '/img/none.png';
            $('#thumbnail').attr('src', imgPath);
            $('#name').text(response.name);
            $('#price').text(`가격: ${response.price.toLocaleString()}원`);
            $('#stock').text(`재고: ${response.stockQuantity}개`);
            $('#contents').text(response.contents);

            // 수정 버튼 노출 여부 결정 (서버에서 현재 로그인 유저 ID를 비교하여 response.memberId와 대조)
            // 주의: 만약 서버에서 세션 정보가 넘어오지 않는다면,
            // 별도의 /api/my-session 같은 API를 만들어 로그인 ID를 가져와야 합니다.
            checkOwnership(response.memberId);
        },
        error: () => {
            alert('상품 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 로그인 유저와 상품 등록자 비교 함수
function checkOwnership(productMemberId) {
    // 세션 정보 확인 API 호출
    $.ajax({
        type: 'GET',
        url: '/api/check-session', // 현재 로그인한 멤버 정보를 가져오는 API (구현 필요)
        success: (currentUser) => {
            if (currentUser.userId === productMemberId) {
                $('#editBtn').show();
            }
        }
    });
}