$(document).ready(function () {
    $('#btn_login').on('click', function () {
        var $email = $('#email');
        var $password = $('#password');

        if ($email.val().length === 0) {
            $email.addClass('invalid').removeClass('valid');
            return;
        } else {
            $email.addClass('valid').removeClass('invalid')
        }

        if ($password.val().length === 0) {
            $password.addClass('invalid').removeClass('valid');
            return;
        } else {
            $password.addClass('valid').removeClass('invalid')
        }

        $.ajax({
            method: 'POST',
            url: '/api/user/login',
            data: {
                email: $email.val(),
                password: $password.val()
            }
        }).done(function (data) {
            console.info(data);
            location.href = '/map/search';
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            var errors = response.errors;
            if (errors) {
                alert(errors[0].defaultMessage);
            } else {
                alert('이메일 혹은 패스워드를 확인해주세요.');
            }
        });
    });
});