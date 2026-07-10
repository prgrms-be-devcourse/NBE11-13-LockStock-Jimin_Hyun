$(document).ready(() => {
    loadProducts();

    function loadProducts() {
        $.ajax({
            type: 'GET',
            url: '/api/products',
            success: (data) => {
                const grid = $('#productGrid');
                grid.empty();
                data.forEach(p => {
                    // 썸네일이 null이면 기본 이미지 사용
                    const imgSrc = p.thumbnailPath ? p.thumbnailPath : '/img/none.png';

                    const item = `
                        <div class="product-item">
                            <img src="${imgSrc}" onclick="location.href='/products/${p.id}'">
                            <h3>${p.name}</h3>
                            <p>${p.price.toLocaleString()}원</p>
                        </div>
                    `;
                    grid.append(item);
                });
            }
        });
    }
});