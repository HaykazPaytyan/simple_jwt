$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/profile',
        success: function (response, status, request) {
            console.log(request);
        },
        error: function (response) {
            console.log(JSON.stringify(response) + "Fuck");
        }
    });
});











