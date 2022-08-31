$(document).ready(function () {
    $("#register").submit(function (event) {
        event.preventDefault();

        $.ajax({
            type:'POST',
            url: 'http://localhost:8080/register',
            data: JSON.stringify({ first_name: $('#first_name').val(), last_name: $('#last_name').val(), email: $('#email').val(), password: $('#password').val() }),
            contentType: "application/json; charset=utf-8",
            traditional: true,
            success: function (response) {
                window.location = "http://localhost:8080/login"
            }
        });

    });

});