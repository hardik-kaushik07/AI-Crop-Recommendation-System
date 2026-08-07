// ==========================================
// Authentication
// ==========================================

const token = localStorage.getItem("token");

if (!token) {

    window.location.href = "login.html";

}



// ==========================================
// Sidebar Buttons
// ==========================================

const dashboardBtn = document.getElementById("dashboardBtn");

const chatBtn = document.getElementById("chatBtn");

const analysisBtn = document.getElementById("analysisBtn");

const historyBtn = document.getElementById("historyBtn");

const logoutBtn = document.getElementById("logoutBtn");



// ==========================================
// Logout Modal
// ==========================================

const logoutModal = document.getElementById("logoutModal");

const confirmLogout = document.getElementById("confirmLogout");

const cancelLogout = document.getElementById("cancelLogout");



// ==========================================
// Navigation
// ==========================================

dashboardBtn.addEventListener("click", () => {

    window.location.href = "dashboard.html";

});

chatBtn.addEventListener("click", () => {

    window.location.href = "chat.html";

});

analysisBtn.addEventListener("click", () => {

    window.location.href = "analysis.html";

});

historyBtn.addEventListener("click", () => {

    window.location.href = "history.html";

});



// ==========================================
// Logout Modal
// ==========================================

logoutBtn.addEventListener("click", () => {

    logoutModal.style.display = "flex";

});

cancelLogout.addEventListener("click", () => {

    logoutModal.style.display = "none";

});

confirmLogout.addEventListener("click", () => {

    localStorage.removeItem("token");

    window.location.href = "login.html";

});

window.addEventListener("click", (event) => {

    if (event.target === logoutModal) {

        logoutModal.style.display = "none";

    }

});



// ==========================================
// Dashboard Statistics
// ==========================================

loadDashboardStats();

async function loadDashboardStats() {

    try {

        const response = await fetch(

            API_BASE_URL + "/api/dashboard/stats",

            {

                headers: {

                    "Authorization": "Bearer " + token

                }

            }

        );

        if (!response.ok) {

            throw new Error("Unable to load dashboard statistics.");

        }

        const stats = await response.json();

        animateCounter("chatCount", stats.chatCount);

        animateCounter("analysisCount", stats.analysisCount);

        animateCounter("pdfCount", stats.pdfCount);

        animateCounter("imageCount", stats.imageCount);

    }

    catch (error) {

        console.error(error);

    }

}



// ==========================================
// Counter Animation
// ==========================================

function animateCounter(id, target) {

    const element = document.getElementById(id);

    if (!element) {

        return;

    }

    target = Number(target) || 0;

    if (target === 0) {

        element.innerText = "0";

        return;

    }

    let current = 0;

    const increment = Math.max(1, Math.ceil(target / 40));

    const timer = setInterval(() => {

        current += increment;

        if (current >= target) {

            current = target;

            clearInterval(timer);

        }

        element.innerText = current;

    }, 20);

}