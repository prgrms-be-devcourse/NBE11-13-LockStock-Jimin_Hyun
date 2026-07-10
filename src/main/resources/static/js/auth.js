// 세션 체크를 위한 공통 함수
function checkLoginSession() {
    $.ajax({
        type: 'GET',
        url: '/api/check-session', // 서버에서 세션을 확인하는 API
        success: (response) => {
            // 세션이 존재하면 아무 일도 안 함
            console.log("로그인된 사용자:", response.userName);
        },
        error: (error) => {
            // 401 에러(세션 만료/없음)가 발생하면 로그인 페이지로 이동
            if (error.status === 401) {
                alert("로그인이 필요한 페이지입니다.");
                window.location.href = "/members/login"; //리다이렉트는 오히려 프론드가 결정하는게 좋다.
            }
        }
    });
}

// 페이지 로드 시 바로 체크
window.addEventListener("pageshow", () => { //뒤로가기 할때도 세션 인증
    // 로그인/회원가입 페이지는 체크 예외 처리 필요 (이게 없으면 무한 루프)
    const currentPath = window.location.pathname;
    if (currentPath !== '/members/login' && currentPath !== '/members/join') {
        checkLoginSession();
    }

    // 로그인/회원가입 페이지일 때만 실행
    if (currentPath === '/members/login' || currentPath === '/members/join') {
        $.ajax({
            type: 'GET',
            url: '/api/check-session',
            success: (response) => {
                // 세션이 존재하면(로그인 되어 있으면) 즉시 리다이렉트
                alert("이미 로그인된 상태입니다. 상품 페이지로 이동합니다.");
                window.location.href = "/products/";
            },
            error: (err) => {
                // 401 에러(로그인 안 됨)면 그대로 둠 (정상)
            }
        });
    }
});