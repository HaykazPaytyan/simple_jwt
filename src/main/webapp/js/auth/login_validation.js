$(function () {
    // Initialize form validation on the login form.
    // It has the name attribute "login"
    $("form[name='login']").validate({
        // Specify validation rules
        rules: {
            email: {
                required: true,
                // Specify that email should be validated
                // by the built-in "email" rule
                email: true
            },
            password: {
                required: true,
                minlength: 8
            }
        },
        // Specify validation error messages
        messages: {
            email: "* Please enter a valid email address",
            password: {
                required: "* Please provide a password",
                minlength: "* Your password must be at least 8 characters long"
            }
        },
        // Make sure the form is submitted to the destination defined
        // in the "action" attribute of the form when valid
        submitHandler: function (form) {
            form.submit();
        }
    });
});











