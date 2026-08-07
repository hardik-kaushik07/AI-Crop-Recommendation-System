// ======================================================
// CropWise AI
// chat.js
// Part 1
// ======================================================


// ======================================================
// Authentication
// ======================================================

const token = localStorage.getItem("token");

if (!token) {

    window.location.href = "login.html";

}


// ======================================================
// Global Variables
// ======================================================

let currentConversationId = null;

let uploadedDocumentId = null;

let uploadedImageId = null;

let historyCache = [];  

let conversationToDelete = null;

let currentUploadedFileName = null;
let currentUploadedStoredFileName = null;
let currentUploadedFileCategory = null;


// ======================================================
// DOM
// ======================================================

const chatBox = document.getElementById("chatBox");

const conversationList = document.getElementById("conversationList");

const questionInput = document.getElementById("question");

const sendBtn = document.getElementById("sendBtn");

const pdfInput = document.getElementById("pdfInput");

const imageInput = document.getElementById("imageInput");

const attachmentContainer = document.getElementById("attachmentContainer");

const typingIndicator = document.getElementById("typingIndicator");

const newChatBtn = document.getElementById("newChatBtn");

const dashboardBtn = document.getElementById("dashboardBtn");

const analysisBtn = document.getElementById("analysisBtn");

const historyBtn = document.getElementById("historyBtn");

const logoutBtn = document.getElementById("logoutBtn");

const deleteConversationModal =
    document.getElementById("deleteConversationModal");

const confirmConversationDelete =
    document.getElementById("confirmConversationDelete");

const cancelConversationDelete =
    document.getElementById("cancelConversationDelete");


// ======================================================
// Page Load
// ======================================================

window.addEventListener("load", async () => {

    try {

        await refreshHistory();

    }

    catch (e) {

        console.error(e);

    }

});


// ======================================================
// Navigation
// ======================================================

dashboardBtn.onclick = () => {

    window.location.href = "dashboard.html";

};

analysisBtn.onclick = () => {

    window.location.href = "analysis.html";

};

historyBtn.onclick = () => {

    window.location.href = "history.html";

};

logoutBtn.onclick = () => {

    localStorage.removeItem("token");

    window.location.href = "login.html";

};


// ======================================================
// New Chat
// ======================================================

newChatBtn.onclick = () => {

    currentConversationId = null;

    uploadedDocumentId = null;

    uploadedImageId = null;

    attachmentContainer.innerHTML = "";

    questionInput.value = "";

    hideTyping();

    chatBox.innerHTML = `

        <div class="welcome-card">

            <h2>👋 New Conversation</h2>

            <p>

                Ask anything about farming.

            </p>

            <ul>

                <li>🌾 Crop Recommendation</li>

                <li>🌦 Weather</li>

                <li>📄 PDF Question Answering</li>

                <li>🖼 Image Understanding</li>

                <li>💰 Crop Prices</li>

            </ul>

        </div>

    `;

};


// ======================================================
// Sidebar
// ======================================================
function renderConversationHistory(history) {

    historyCache = history;

    conversationList.innerHTML = "";

    history.forEach(conversation => {

        if (!conversation.message || conversation.message.length === 0) {
            return;
        }

        let title = "";

        const firstUserMessage = conversation.message.find(msg =>
            msg.role === "USER" &&
            msg.message &&
            msg.message.trim() !== ""
        );

        if (firstUserMessage) {

            title = firstUserMessage.message.trim();

        } else {

            const upload = conversation.message.find(
                msg => msg.documentId != null
            );

            if (!upload) {
                return;
            }

            title = "📎 " + upload.fileName;

        }

        if (title.length > 35) {
            title = title.substring(0, 35) + "...";
        }

        const item = document.createElement("div");

        item.className = "conversation-item";

        if (conversation.conversationId === currentConversationId) {
            item.classList.add("active");
        }

        item.innerHTML = `
            <span class="conversation-title">
                ${title}
            </span>

            <span class="delete-conversation">
                🗑
            </span>
        `;

        // ==========================
        // Open Conversation
        // ==========================

        item.querySelector(".conversation-title").onclick = async () => {

            const fullConversation =
                await loadConversation(conversation.conversationId);

            openConversation(fullConversation);

        };

        // ==========================
        // Delete Conversation
        // ==========================

        item.querySelector(".delete-conversation").onclick = (e) => {

            e.stopPropagation();

            conversationToDelete = conversation.conversationId;

            deleteConversationModal.style.display = "flex";

        };

        conversationList.appendChild(item);

    });

}

// ======================================================
// Open Conversation
// ======================================================

function openConversation(conversation) {

    currentConversationId = conversation.conversationId;

    uploadedDocumentId = null;

    uploadedImageId = null;

    attachmentContainer.innerHTML = "";

    chatBox.innerHTML = "";

    if (!conversation.message ||
        conversation.message.length === 0) {

        newChatBtn.click();

        return;

    }

    conversation.message.forEach(msg => {

    if (msg.role === "USER") {

        addUserMessage(msg);

    } else {

        addAIMessage(msg.message);

    }

});

    scrollBottom();

    renderConversationHistory(historyCache);

}

// ======================================================
// Message UI
// ======================================================

function addUserMessage(msg) {

    const div = document.createElement("div");

    div.className = "user-message";

    let attachment = "";

    if (msg.documentId != null) {

        // IMAGE (we'll fix this in the next step)
        if (msg.fileCategory === "IMAGE") {

            attachment = `
    <div class="attachment-preview">

        <img
            id="image-${msg.documentId}"
            class="chat-image"
            alt="${msg.fileName}">

    </div>
`;

        }

        // PDF
        else {

            attachment = `
                <div class="attachment-preview">

                    <a
                        href="#"
                        onclick="openAttachment(${msg.documentId}); return false;"
                        class="pdf-link">

                        📄 ${msg.fileName}

                    </a>

                </div>
            `;

        }

    }

    div.innerHTML = `

        ${attachment}

        <div class="message-text">

            ${msg.message == null ? "" : msg.message}

        </div>

    `;

    chatBox.appendChild(div);

    if (
    msg.documentId &&
    msg.fileCategory === "IMAGE"
) {

    const image = document.getElementById(
        "image-" + msg.documentId
    );

    loadProtectedImage(
        msg.documentId,
        image
    );

}

    scrollBottom();

}

function addAIMessage(message) {

    const div = document.createElement("div");

    div.className = "ai-message";

    div.innerHTML = `
        <div class="message-text">
            ${message}
        </div>
    `;

    chatBox.appendChild(div);

    scrollBottom();

}


// ======================================================
// Typing Indicator
// ======================================================

function showTyping() {

    typingIndicator.style.display = "block";

    scrollBottom();

}

function hideTyping() {

    typingIndicator.style.display = "none";

}


// ======================================================
// Scroll
// ======================================================

function scrollBottom() {

    chatBox.scrollTop = chatBox.scrollHeight;

}


// ======================================================
// Attachment Chip
// ======================================================

function showAttachmentChip(icon, name) {

    attachmentContainer.innerHTML = "";

    const chip = document.createElement("div");

    chip.className = "attachment-chip";

    chip.innerHTML = `
        <span>${icon} ${name}</span>
        <button id="removeAttachmentBtn">✕</button>
    `;

    attachmentContainer.appendChild(chip);

    document
        .getElementById("removeAttachmentBtn")
        .onclick = removeAttachment;

}


// ======================================================
// Remove Attachment
// ======================================================

function removeAttachment() {

    uploadedDocumentId = null;

    uploadedImageId = null;

    pdfInput.value = "";

    imageInput.value = "";

    attachmentContainer.innerHTML = "";

}


// ======================================================
// Upload PDF
// ======================================================

pdfInput.addEventListener("change", async function () {

    const file = this.files[0];

    if (!file) {

        return;

    }

    try {

        if (currentConversationId == null) {

            const conversation = await createConversation();

            currentConversationId = conversation.conversationId;

        }

        showTyping();

        const result = await uploadPdf(file);

        hideTyping();

        uploadedDocumentId = result.documentId;
        uploadedImageId = null;

        currentUploadedFileName = result.fileName;
        currentUploadedStoredFileName = result.storedFileName;
        currentUploadedFileCategory = "DOCUMENT";

        showAttachmentChip("📄", file.name);

    }

    catch (error) {

        hideTyping();

        console.error(error);

        alert("PDF upload failed.");

    }

});


// ======================================================
// Upload Image
// ======================================================

imageInput.addEventListener("change", async function () {

    const file = this.files[0];

    if (!file) {

        return;

    }

    try {

        if (currentConversationId == null) {

            const conversation = await createConversation();

            currentConversationId = conversation.conversationId;

        }

        showTyping();

        const result = await uploadImage(file);

        hideTyping();

        uploadedImageId = result.documentId;
        uploadedDocumentId = null;

        currentUploadedFileName = result.fileName;
        currentUploadedStoredFileName = result.storedFileName;
        currentUploadedFileCategory = "IMAGE";

            showAttachmentChip("🖼", file.name);

    }

    catch (error) {

        hideTyping();

        console.error(error);

        alert("Image upload failed.");

    }

}); 

// ======================================================
// Send Message
// ======================================================

async function sendMessage() {

    const question = questionInput.value.trim();

    if (question === "") {
        return;
    }

    try {

        // Create conversation only when required
        if (currentConversationId == null) {

            const conversation = await createConversation();

            currentConversationId = conversation.conversationId;

        }

        addUserMessage({

    message: question,

    documentId: uploadedImageId || uploadedDocumentId,

    fileName: currentUploadedFileName,

    storedFileName: currentUploadedStoredFileName,

    fileCategory: currentUploadedFileCategory

});

        questionInput.value = "";

        showTyping();

        let answer = "";

        // ======================================
        // IMAGE CHAT
        // ======================================

        if (uploadedImageId != null) {

            answer = await askImage(question);

        }

        // ======================================
        // NORMAL CHAT / PDF RAG
        // ======================================

        else {

            answer = await sendChat(question);

        }

        hideTyping();

        addAIMessage(answer);

        attachmentContainer.innerHTML = "";

        uploadedDocumentId = null;
        uploadedImageId = null;

        currentUploadedFileName = null;
        currentUploadedStoredFileName = null;
        currentUploadedFileCategory = null;

        pdfInput.value = "";
        imageInput.value = "";

        // Refresh sidebar titles
        await refreshHistory();

    }

    catch (error) {

        hideTyping();

        console.error(error);

        addAIMessage("❌ Sorry, something went wrong.");

    }

}


// ======================================================
// Send Button
// ======================================================

sendBtn.addEventListener("click", function () {

    sendMessage();

});


// ======================================================
// Press Enter
// ======================================================

questionInput.addEventListener("keydown", function (e) {

    if (e.key === "Enter" && !e.shiftKey) {

        e.preventDefault();

        sendMessage();

    }

});


// ======================================================
// Refresh Sidebar
// ======================================================

async function refreshHistory() {

    try {

        const history = await loadHistory();

        renderConversationHistory(history);

    }

    catch (error) {

        console.error(error);

    }

}


// ======================================================
// Clear Current Chat
// ======================================================

function clearChatWindow() {

    chatBox.innerHTML = "";

    attachmentContainer.innerHTML = "";

    uploadedDocumentId = null;

    uploadedImageId = null;

}


// ======================================================
// Start Fresh Chat
// ======================================================

function startNewChat() {

    currentConversationId = null;

    clearChatWindow();

    questionInput.value = "";

    hideTyping();

    chatBox.innerHTML = `

        <div class="welcome-card">

            <h2>👋 Welcome</h2>

            <p>

                I can help you with:

            </p>

            <ul>

                <li>🌾 Crop Recommendation</li>

                <li>🌦 Weather</li>

                <li>📄 PDF Question Answering</li>

                <li>🖼 Image Understanding</li>

                <li>💰 Market Prices</li>

            </ul>

        </div>

    `;

}

// ======================================================
// Delete Current Conversation
// ======================================================



// ======================================================
// Delete All Conversations
// ======================================================




// ======================================================
// Sidebar Active State
// ======================================================

function updateActiveConversation() {

    const items = document.querySelectorAll(
        ".conversation-item"
    );

    items.forEach(item => {

        item.classList.remove("active");

    });

}


// ======================================================
// Refresh Everything
// ======================================================

async function reloadChat() {

    await refreshHistory();

    if (currentConversationId == null) {

        return;

    }

    try {

        const conversation =
            await loadConversation(currentConversationId);

        openConversation(conversation);

    }

    catch (error) {

        console.log(error);

    }

}


// ======================================================
// Initial Welcome Screen
// ======================================================

if (chatBox.innerHTML.trim() === "") {

    startNewChat();

}


// ======================================================
// Window Resize
// ======================================================

window.addEventListener("resize", () => {

    scrollBottom();

});

async function openAttachment(documentId) {

    try {

        const response = await fetch(

            API_BASE_URL + "/api/files/" + documentId,

            {
                headers: {
                    Authorization: "Bearer " + token
                }
            }

        );

        if (!response.ok) {

            throw new Error("Unable to open file");

        }

        const blob = await response.blob();

        const url = window.URL.createObjectURL(blob);

        window.open(url, "_blank");

    }
    catch (e) {

        console.error(e);

        alert("Unable to open attachment.");

    }

}

async function loadProtectedImage(documentId, imageElement) {

    try {

        const response = await fetch(

            API_BASE_URL + "/api/files/" + documentId,

            {
                headers: {
                    Authorization: "Bearer " + token
                }
            }

        );

        if (!response.ok) {

            throw new Error("Unable to load image");

        }

        const blob = await response.blob();

        const imageUrl = URL.createObjectURL(blob);

        imageElement.src = imageUrl;

    }
    catch (e) {

        console.error(e);

    }

}

confirmConversationDelete.onclick = async () => {

    if (conversationToDelete == null) {

        deleteConversationModal.style.display = "none";

        return;

    }

    try {

        await deleteConversation(conversationToDelete);

        if (currentConversationId === conversationToDelete) {

            currentConversationId = null;

            uploadedDocumentId = null;
            uploadedImageId = null;

            currentUploadedFileName = null;
            currentUploadedStoredFileName = null;
            currentUploadedFileCategory = null;

            attachmentContainer.innerHTML = "";

            questionInput.value = "";

            chatBox.innerHTML = `
                <div class="welcome-card">
                    <h2>👋 Conversation Deleted</h2>
                    <p>Start a new conversation.</p>
                </div>
            `;

        }

        conversationToDelete = null;

        deleteConversationModal.style.display = "none";

        await refreshHistory();

    }

    catch (error) {

        console.error(error);

        deleteConversationModal.style.display = "none";

        alert("Unable to delete conversation.");

    }

};

cancelConversationDelete.onclick = () => {

    conversationToDelete = null;

    deleteConversationModal.style.display = "none";

};

// ======================================================
// END OF FILE
// ======================================================