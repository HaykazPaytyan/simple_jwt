$(function () {
    // Initialize form validation on the registration form.
    // It has the name attribute "registration"
    $("form[name='registration']").validate({
        // Specify validation rules
        rules: {
            // The key name on the left side is the name attribute
            // of an input field. Validation rules are defined
            // on the right side
            first_name: "required",
            last_name: "required",
            email: {
                required: true,
                // Specify that email should be validated
                // by the built-in "email" rule
                email: true
            },
            password: {
                required: true,
                minlength: 8
            },
            password_repeat: {
                required: true,
                minlength: 8
            }
        },
        // Specify validation error messages
        messages: {
            first_name: "* Please enter your firstname",
            last_name: "* Please enter your lastname",
            email: "* Please enter a valid email address",
            password: {
                required: "* Please provide a password",
                minlength: "* Your password must be at least 8 characters long"
            },
            password_repeat: {
                required: "* Please provide a password confirmation",
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











