$(document).ready(function () {
    $("#attempt").submit(function (event) {
        event.preventDefault();
        
        let token = "";

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/profile',
            headers: {
                "Authorization": "Basic " + btoa($('#email').val() + ":" + $('#password').val())
            },
            traditional: true,
            success: function (response, status, request) {
                token = request.getResponseHeader('X-Auth-Token');
                sendBack(token);
            },
            error: function (response) {
                console.log(response);
            }
        });

    });
});


function sendBack(data){
         
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/profile',
        headers: {
            "Authorization": "Bearer " + data
        },
        traditional: true,
        success: function (response, status, request) {
            window.location.href = "http://localhost:8080/profile";
        },
        error: function (response) {
            console.log(response);
        }
    });
}