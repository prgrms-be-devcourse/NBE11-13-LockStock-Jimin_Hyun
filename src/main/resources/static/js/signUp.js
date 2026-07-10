$(document).ready(() => {
    // 회원가입 버튼 클릭 이벤트
    $('#signup').click(() => {
        let formData = {
            userId: $('#reg_id').val(),
            password: $('#reg_pw').val(),
            userName: $('#reg_name').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/members/join',
            data: JSON.stringify(formData),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (response) => {
                alert("회원가입 성공! 로그인 페이지로 이동합니다.");
                window.location.href = response.url;
            },
            error: (error) => {
                console.error('오류 발생:', error);
                // 서버에서 보낸 에러 메시지가 있다면 출력
                alert(error.responseJSON ? error.responseJSON.message : "회원가입 실패");
            }
        });
    });
});