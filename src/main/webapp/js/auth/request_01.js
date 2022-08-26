$(document).ready(function () {
    $("#attempt").submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/register',
            async : true,
            headers: {
                "Authorization": "Basic " + btoa($('#email').val() + ":" + $('#password').val());
            },
            success: function (response) {
                console.log(response);
            },
            error: function (response) {
                console.log(JSON.stringify(response) + "Fuck");
            }
        });
    });
});







