$(document).ready(() => {
    $('#signin').click(() => {
        let formData = {
            userId: $('#user_id').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/members/login',
            data: JSON.stringify(formData),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (response) => {
                console.log('res :: ', response);
                if (response.successed) {
                    window.location.href = "/products/";
                } else {
                    alert(response.message);
                }
            },
            error: (error) => {
                console.error('오류 발생:', error);
                alert(error.responseJSON ? error.responseJSON.message : "로그인 실패");
            }
        });
    });
});