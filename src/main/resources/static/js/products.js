// 검색 조건을 기억하기 위한 전역 변수
let currentCondition = {};
const PAGE_SIZE = 20; // 한 번에 보여줄 상품 개수

$(document).ready(() => {
    // 페이지 진입 시 전체 목록 로드 (1페이지)
    loadProducts(1);

    // 로그아웃 버튼 클릭 이벤트
    $('#logoutBtn').on('click', () => {
        // GET 방식으로 로그아웃 API 호출
        // 서버에서 세션 무효화 후 로그인 페이지로 리다이렉트 처리하는 것이 일반적
        window.location.href = '/members/logout';
    });

    // 검색 버튼 클릭 시
    $('#searchBtn').on('click', () => {
        // 1. 현재 검색 조건을 객체로 추출하여 전역 변수에 저장
        currentCondition = getSearchCondition();
        // 2. 1페이지부터 검색 결과 출력
        loadProducts(1);
    });

    // 검색어 입력 중 Enter 키 이벤트
    $('#keyword').on('keydown', (e) => {
        if (e.key === 'Enter') $('#searchBtn').trigger('click');
    });
});

// 검색 조건 추출 (검색 버튼을 누를 때만 currentCondition에 저장됨)
let getSearchCondition = () => {
    const condition = {};
    const keyword = $('#keyword').val();
    if (keyword) condition.keyword = keyword;
    return condition;
}

// 상품 목록 로드 함수
let loadProducts = (page) => {
    $.ajax({
        type: 'GET',
        url: '/api/products',
        data: {
            page: page,
            size: PAGE_SIZE,
            ...currentCondition // 저장해둔 검색 조건을 항상 함께 보냄
        },
        success: (response) => {
            // response 구조가 { content: [], totalPages: 0 } 라고 가정
            renderProducts(response.content);
            renderPagination(page, response.totalPages);
        },
        error: (error) => {
            console.error('오류 발생:', error);
            alert('상품 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 상품 리스트 렌더링
let renderProducts = (products) => {
    const $grid = $('#productGrid');
    $grid.empty();

    if (!products || products.length === 0) {
        $grid.append('<p style="grid-column: 1/-1; text-align:center;">조회된 상품이 없습니다.</p>');
        return;
    }

    products.forEach((p) => {
        const isSoldOut = p.stockQuantity <= 0;
        const imgSrc = p.thumbnailPath ? `/images/${p.thumbnailPath}` : '/img/none.png';
        const stockClass = isSoldOut ? "sold-out-text" : "stock-text";
        const stockStatus = isSoldOut ? "품절" : `재고: ${p.stockQuantity}개`;

        $grid.append(`
            <div class="product-item ${isSoldOut ? 'sold-out' : ''}" 
                 data-id="${p.id}" 
                 data-version="${p.version}" 
                 data-member-id="${p.memberId}"
                 onclick="location.href='/products/detail?id=${p.id}'">
                 
                ${isSoldOut ? '<div class="overlay">품절</div>' : ''}
                <img src="${imgSrc}" alt="${p.name}">
                <h3>${p.name}</h3>
                <p class="price">${p.price.toLocaleString()}원</p>
                <p class="${stockClass}">${stockStatus}</p>
            </div>
        `);
    });
}

// 페이지네이션 렌더링
let renderPagination = (currentPage, totalPages) => {
    const $pagination = $('#pagination');
    $pagination.empty();

    for (let p = 1; p <= totalPages; p++) {
        const $btn = $(`<button class="btn page-btn">${p}</button>`);

        if (p === currentPage) {
            $btn.addClass('active').prop('disabled', true);
        }

        // 페이지 버튼을 누르면, 저장되어 있는 currentCondition을 가지고 해당 페이지 조회
        $btn.on('click', () => loadProducts(p));
        $pagination.append($btn);
    }
}
