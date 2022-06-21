$(document).ready(function () {
    $("#attempt").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/auth',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data:JSON.stringify({"email": $('#email').val(), "password": $('#password').val()}),
            traditional: true,
            success: function (response) {
              console.log(response);
            },
            error: function (response) {
                console.log(JSON.stringify(response));
            }
        });

    });
});






