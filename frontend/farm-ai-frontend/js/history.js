const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}



const historyContainer = document.getElementById("historyContainer");
const loader = document.getElementById("loader");
const messageBox = document.getElementById("messageBox");

const detailsModal = document.getElementById("detailsModal");
const modalBody = document.getElementById("modalBody");
const closeDetails = document.getElementById("closeDetails");

const deleteModal = document.getElementById("deleteModal");
const deleteAllModal = document.getElementById("deleteAllModal");

const confirmDelete = document.getElementById("confirmDelete");
const cancelDelete = document.getElementById("cancelDelete");

const confirmDeleteAll = document.getElementById("confirmDeleteAll");
const cancelDeleteAll = document.getElementById("cancelDeleteAll");

const deleteAllBtn = document.getElementById("deleteAllBtn");



let selectedId = null;



loadHistory();

function showLoader() {

    loader.style.display = "flex";

}

function hideLoader() {

    loader.style.display = "none";

}



function showMessage(text, type) {

    messageBox.innerHTML = `
        <div class="message ${type}">
            ${text}
        </div>
    `;

    setTimeout(() => {

        messageBox.innerHTML = "";

    }, 2500);

}



async function loadHistory() {

    showLoader();

    historyContainer.innerHTML = "";

    try {

        const response = await fetch(

            API_BASE_URL + "/api/farm/history",

            {

                headers: {

                    Authorization: "Bearer " + token

                }

            }

        );

        if (!response.ok) {

            throw new Error("Unable to load history");

        }

        const history = await response.json();

        hideLoader();

        if (history.length === 0) {

            historyContainer.innerHTML = `

                <h2 class="empty">

                    No Recommendation History Found

                </h2>

            `;

            return;

        }

        history.forEach(item => {

            const card = document.createElement("div");

            card.className = "history-card";

            card.innerHTML = `

                <h2>${item.recommendation.crop}</h2>

                <p><b>Location :</b> ${item.location}</p>

                <p><b>Season :</b> ${item.season}</p>

                <p><b>Temperature :</b> ${item.temperature} °C</p>

                <p><b>Humidity :</b> ${item.humidity}%</p>

                <p><b>Weather :</b> ${item.weather}</p>

                <div class="button-group">

                    <button
                        class="view-btn"
                        onclick="viewHistory(${item.id})">

                        View Details

                    </button>

                    <button
                        class="delete-btn"
                        onclick="openDeleteModal(${item.id})">

                        Delete

                    </button>

                </div>

            `;

            historyContainer.appendChild(card);

        });

    }

    catch (error) {

        hideLoader();

        historyContainer.innerHTML = `

            <h2 class="empty">

                Unable to Load History

            </h2>

        `;

        console.log(error);

    }

}



async function viewHistory(id) {

    try {

        showLoader();

        const response = await fetch(

            API_BASE_URL + "/api/farm/history/" + id,

            {

                headers: {

                    Authorization: "Bearer " + token

                }

            }

        );

        if (!response.ok) {

            throw new Error("Unable to load details");

        }

        const item = await response.json();

        hideLoader();

        modalBody.innerHTML = `

            <h1 class="modal-title">

                ${item.recommendation.crop}

            </h1>

            <div class="modal-section">

                <h3>Reason</h3>

                <p>${item.recommendation.reason}</p>

            </div>

            <div class="modal-section">

                <h3>Recommended Fertilizer</h3>

                <p>${item.recommendation.fertilizer}</p>

            </div>

            <div class="modal-section">

                <h3>Pesticide</h3>

                <p>${item.recommendation.pesticide}</p>

            </div>

            <div class="modal-section">

                <h3>Irrigation</h3>

                <p>${item.recommendation.irrigation}</p>

            </div>

            <div class="modal-section">

                <h3>Disease Risk</h3>

                <p>${item.recommendation.diseaseRisk}</p>

            </div>

            <div class="modal-section">

                <h3>Harvest Time</h3>

                <p>${item.recommendation.harvestTime}</p>

            </div>

            <div class="modal-section">

                <h3>Expected Yield</h3>

                <p>${item.recommendation.expectedYield}</p>

            </div>

            <div class="modal-section">

                <h3>Weather Information</h3>

                <p><b>Temperature :</b> ${item.recommendation.temperature} °C</p>

                <p><b>Humidity :</b> ${item.recommendation.humidity}%</p>

                <p><b>Weather :</b> ${item.recommendation.weatherCondition}</p>

            </div>

        `;

        detailsModal.style.display = "block";

    }

    catch (error) {

        hideLoader();

        console.log(error);

        showMessage("Unable to load recommendation.", "error");

    }

}



function openDeleteModal(id) {

    selectedId = id;

    deleteModal.style.display = "flex";

}



confirmDelete.addEventListener("click", async () => {

    try {

        showLoader();

        const response = await fetch(

            API_BASE_URL + "/api/farm/delete/" + selectedId,

            {

                method: "DELETE",

                headers: {

                    Authorization: "Bearer " + token

                }

            }

        );

        hideLoader();

        if (!response.ok) {

            throw new Error("Delete Failed");

        }

        deleteModal.style.display = "none";

        showMessage("Recommendation deleted successfully.", "success");

        loadHistory();

    }

    catch (error) {

        hideLoader();

        showMessage("Unable to delete recommendation.", "error");

        console.log(error);

    }

});



cancelDelete.addEventListener("click", () => {

    deleteModal.style.display = "none";

});



deleteAllBtn.addEventListener("click", () => {

    deleteAllModal.style.display = "flex";

});



confirmDeleteAll.addEventListener("click", async () => {

    try {

        showLoader();

        const response = await fetch(

            API_BASE_URL + "/api/farm/delete",

            {

                method: "DELETE",

                headers: {

                    Authorization: "Bearer " + token

                }

            }

        );

        hideLoader();

        if (!response.ok) {

            throw new Error("Delete All Failed");

        }

        deleteAllModal.style.display = "none";

        showMessage("Complete history deleted successfully.", "success");

        loadHistory();

    }

    catch (error) {

        hideLoader();

        showMessage("Unable to delete history.", "error");

        console.log(error);

    }

});



cancelDeleteAll.addEventListener("click", () => {

    deleteAllModal.style.display = "none";

});



closeDetails.addEventListener("click", () => {

    detailsModal.style.display = "none";

});



window.addEventListener("click", function (event) {

    if (event.target === detailsModal) {

        detailsModal.style.display = "none";

    }

    if (event.target === deleteModal) {

        deleteModal.style.display = "none";

    }

    if (event.target === deleteAllModal) {

        deleteAllModal.style.display = "none";

    }

});