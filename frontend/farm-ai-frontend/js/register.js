document.getElementById("registerForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const msg = document.getElementById("registerMessage");

    msg.innerText = "";

    const user = {

        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value

    };

    try {

        const response = await fetch(`${API_BASE_URL}/api/user/register`, {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(user)

        });

        if (response.ok) {

            msg.className = "message success";

            msg.innerText = "Registration Successful ✔";

            setTimeout(() => {

                window.location.href = "login.html";

            }, 1500);

        } else {

            const message = await response.text();

            msg.className = "message error";

            msg.innerText = message;

        }

    } catch (error) {

        console.error(error);

        msg.className = "message error";

        msg.innerText = "Server is not running.";

    }

});