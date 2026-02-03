const usernameInput = document.getElementById('txtUsername');

usernameInput.addEventListener('blur', async function() {
    const username = this.value.trim();
    if (!username) return;

    try {
        const response = await fetch(`/api/user/check-username?username=${encodeURIComponent(username)}`);
        const exists = await response.json();

        if (exists) {
            this.setCustomValidity('Username is already taken');
            this.reportValidity();
        } else {
            this.setCustomValidity('');
        }
    } catch (error) {
        console.error('Error checking username:', error);
    }
});

usernameInput.addEventListener('input', function() {
    this.setCustomValidity('');
});

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const password = document.getElementById('txtPassword');
    const confirmPassword = document.getElementById('txtConfirmPassword');

    function validatePasswords() {
        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity('Passwords do not match');
        } else {
            confirmPassword.setCustomValidity('');
        }
    }

    password.addEventListener('input', validatePasswords);
    confirmPassword.addEventListener('input', validatePasswords);

    form.addEventListener('submit', function(event) {
        validatePasswords();

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
    });
});