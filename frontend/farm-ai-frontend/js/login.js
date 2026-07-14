document.getElementById("loginForm").addEventListener("submit", async function (e) {

    e.preventDefault();

    const user = {

        email: document.getElementById("email").value,
        password: document.getElementById("password").value

    };

    try {

        const response = await fetch(`${API_BASE_URL}/api/user/login`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(user)

        });

        const token = await response.text();

        if (response.ok && token !== "Fail") {

            localStorage.setItem("token", token);

        const msg=document.getElementById("loginMessage");

    msg.className="message success";
    msg.innerText="Login Successful";

    setTimeout(()=>{
    window.location.href="dashboard.html";
},1000);
            window.location.href = "dashboard.html";

        } 
        else {

    msg.className="message error";
    msg.innerText="Invalid Email or Password";

}

    } catch (error) {

        console.error(error);

        alert("Server not responding.");

    }

});