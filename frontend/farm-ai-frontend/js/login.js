const loginForm = document.getElementById("loginForm");

const loginMessage = document.getElementById("loginMessage");

const loginButton = document.querySelector(".login-btn");

const passwordInput = document.getElementById("password");

const togglePassword = document.getElementById("togglePassword");

// ==========================================
// Show / Hide Password
// ==========================================

togglePassword.addEventListener("click", () => {

    if (passwordInput.type === "password") {

        passwordInput.type = "text";

        togglePassword.textContent = "🙈";

    } else {

        passwordInput.type = "password";

        togglePassword.textContent = "👁";

    }

});

// ==========================================
// Login
// ==========================================

loginForm.addEventListener("submit", async function (e) {

    e.preventDefault();

    loginMessage.innerText = "";

    loginButton.disabled = true;

    loginButton.innerText = "Logging in...";

    const user = {

        email: document.getElementById("email").value.trim(),

        password: passwordInput.value

    };

    try {

        const response = await fetch(

            API_BASE_URL + "/api/user/login",

            {

                method: "POST",

                headers: {

                    "Content-Type": "application/json"

                },

                body: JSON.stringify(user)

            }

        );

        const token = await response.text();

        if (response.ok && token !== "Fail") {

            localStorage.setItem("token", token);

            loginMessage.style.color = "#2e7d32";

            loginMessage.innerText = "✅ Login Successful";

            setTimeout(() => {

                window.location.href = "dashboard.html";

            }, 800);

        }

        else {

            loginMessage.style.color = "#d32f2f";

            loginMessage.innerText = "❌ Invalid Email or Password";

        }

    }

    catch (error) {

        console.error(error);

        loginMessage.style.color = "#d32f2f";

        loginMessage.innerText = "❌ Unable to connect to server.";

    }

    finally {

        loginButton.disabled = false;

        loginButton.innerText = "Login";

    }

});