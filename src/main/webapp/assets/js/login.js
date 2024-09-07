//login
(function () {
    'use strict';

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.login-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                const email = document.getElementById("email");
                const emailError = document.getElementById("error-email");
                const password = document.getElementById("password");
                const passwordError = document.getElementById("error-password");


                // Check if the email is valid using a regular expression
                let emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;

                if (email.value.trim() === "") {
                    emailError.className = 'invalid-feedback';
                    emailError.innerHTML = "Email is required";
                } else if (emailRegex.test(email.value.trim())) {
                    emailError.className = 'valid-feedback';
                    emailError.innerHTML = "";
                } else {
                    emailError.className = 'invalid-feedback';
                    emailError.innerHTML = "Email is not valid";
                }

                // Check password length
                if (password.value.trim() === "") {
                    passwordError.className = "invalid-feedback";
                    passwordError.innerHTML = "Password is required";
                } else if (password.value.trim().length < 8) {
                    passwordError.className = "invalid-feedback";
                    passwordError.innerHTML = "Password must be at least 8 characters long";
                } else {
                    passwordError.className = "valid-feedback";
                    passwordError.innerHTML = "";
                }

                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
})();