//registration validation
(function () {
    'use strict';

    // Fetch all the forms
    const forms = document.querySelectorAll('.registration-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                const firstName = document.getElementById("firstName");
                const firstNameNameError = document.getElementById("fName-error");
                const lastName = document.getElementById("lastName");
                const lastNameError = document.getElementById("lName-error");
                const phoneNumber = document.getElementById("phoneNumber");
                const phoneError = document.getElementById("phone-error");
                const email = document.getElementById("email");
                const emailError = document.getElementById("email-error");
                const password = document.getElementById("password");
                const passwordError = document.getElementById("password-error");
                const address = document.getElementById("address");
                const addressError = document.getElementById("address-error");

                // Check if first name is empty
                if (firstName.value.trim() === "") {
                    firstNameNameError.className = 'invalid-feedback';
                    firstNameNameError.innerHTML = "First Name is required";
                } else {
                    firstNameNameError.className = 'valid-feedback';
                    firstNameNameError.innerHTML = "Looks good";
                }

                // Check if last name is empty
                if (lastName.value.trim() === "") {
                    lastNameError.className = 'invalid-feedback';
                    lastNameError.innerHTML = "Last Name is required";
                } else {
                    lastNameError.className = 'valid-feedback';
                    lastNameError.innerHTML = "Looks good";
                }

                // Check if first name is empty
                if (address.value.trim() === "") {
                    addressError.className = 'invalid-feedback';
                    addressError.innerHTML = "Address is required";
                } else {
                    addressError.className = 'valid-feedback';
                    addressError.innerHTML = "Looks good";
                }

                // Check phone number length
                if (phoneNumber.value.trim() === "") {
                    phoneError.className = "invalid-feedback";
                    phoneError.innerHTML = "Phone number is required";
                } else if (phoneNumber.value.trim().length < 10) {
                    phoneError.className = "invalid-feedback";
                    phoneError.innerHTML = "Phone number must be at least 10 characters long";
                } else {
                    phoneError.className = "valid-feedback";
                    phoneError.innerHTML = "Looks good";
                }

                // Check if the email is valid using a regular expression
                let emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;

                if (email.value.trim() === "") {
                    emailError.className = 'invalid-feedback';
                    emailError.innerHTML = "Email is required";
                } else if (emailRegex.test(email.value.trim())) {
                    emailError.className = 'valid-feedback';
                    emailError.innerHTML = "Email is valid";
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
                    passwordError.innerHTML = "Password is valid";
                }

                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
})();