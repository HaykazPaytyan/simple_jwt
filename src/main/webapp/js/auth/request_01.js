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
                
            },
            error: function (response) {
                console.log(JSON.stringify(response) + "Fuck");
            }
        });

    });
});

// $.ajax({
//             type: 'POST',
//             url: 'http://localhost:8080/auth',
//             data: JSON.stringify({email: user_email,password: user_password}),
//             contentType: "application/json; charset=utf-8",
//             traditional: true,
//             success: function (response) {
//                 let jsonObject = JSON.parse(response);
//                 window.location.href = "http://localhost:8080/account/" + jsonObject.id;
//             }
//         });








