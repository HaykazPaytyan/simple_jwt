$(document).ready(function () {
    $("#attempt").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/auth',
            headers: {
                "Authorization": "Basic " + btoa($('#email').val() + ":" + $('#password').val())
            },
            success: function (response, status, request) {
                window.location = "http://localhost:8080/profile";
            },
            error: function (response) {
                console.log(JSON.stringify(response) + "Fuck");
            }
        });

    });
});










