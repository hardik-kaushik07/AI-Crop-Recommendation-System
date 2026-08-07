// ======================================================
// DOM
// ======================================================

const chatBox = document.getElementById("chatBox");
const conversationList = document.getElementById("conversationList");
const uploadStatus = document.getElementById("uploadStatus");
const typingIndicator = document.getElementById("typingIndicator");

// ======================================================
// Clear Chat
// ======================================================

function clearChat() {

    chatBox.innerHTML = "";

}

// ======================================================
// Welcome Message
// ======================================================

function showWelcomeCard() {

    clearChat();

    chatBox.innerHTML = `

        <div class="welcome-card">

            <h2>👋 Welcome to CropWise AI</h2>

            <p>

                Your intelligent farming assistant.

            </p>

            <br>

            <b>I can help you with:</b>

            <ul>

                <li>🌾 Crop Recommendation</li>

                <li>🌦 Weather Information</li>

                <li>💰 Crop Market Prices</li>

                <li>📄 PDF Question Answering</li>

                <li>🖼 Image Understanding</li>

            </ul>

        </div>

    `;

}

// ======================================================
// Scroll Bottom
// ======================================================

function scrollBottom() {

    chatBox.scrollTop = chatBox.scrollHeight;

}

// ======================================================
// User Message
// ======================================================

function addUserMessage(message) {

    chatBox.innerHTML += `

        <div class="user-message">

            ${message}

        </div>

    `;

    scrollBottom();

}

// ======================================================
// AI Message
// ======================================================

function addAIMessage(message) {

    chatBox.innerHTML += `

        <div class="ai-message">

            ${message}

        </div>

    `;

    scrollBottom();

}

// ======================================================
// Attachment Chip
// ======================================================

function showAttachment(fileName, type) {

    uploadStatus.innerHTML = "";

    const icon = type === "image"

        ? "🖼"

        : "📄";

    uploadStatus.innerHTML = `

        <div class="attachment-chip">

            <span>

                ${icon} ${fileName}

            </span>

            <button onclick="removeAttachment()">

                ×

            </button>

        </div>

    `;

}

// ======================================================
// Remove Attachment
// ======================================================

function removeAttachment() {

    uploadedDocumentId = null;

    uploadedImageId = null;

    document.getElementById("pdfInput").value = "";

    document.getElementById("imageInput").value = "";

    const attachmentContainer =
    document.getElementById("attachmentContainer");

attachmentContainer.innerHTML = "";

}

// ======================================================
// Typing
// ======================================================

function showTyping() {

    typingIndicator.style.display = "block";

}

function hideTyping() {

    typingIndicator.style.display = "none";

}

// ======================================================
// Conversation History
// ======================================================

function renderConversationHistory(history) {

    conversationList.innerHTML = "";

    if (!history || history.length === 0) {

        conversationList.innerHTML =

            "<p style='padding:10px;'>No Conversations</p>";

        return;

    }

    history.forEach(conversation => {

        const item = document.createElement("div");

        item.className = "conversation-item";

        if (conversation.conversationId === currentConversationId) {

            item.classList.add("active");

        }

       item.dataset.id = conversation.conversationId;

item.innerHTML = `

    <strong>

        ${conversation.title ??
        conversation.conversationId.substring(0,8)}

    </strong>

`;

        item.onclick = () => {

            openConversation(conversation.conversationId);

        };

        conversationList.appendChild(item);

    });

}

// ======================================================
// Active Conversation
// ======================================================

function highlightConversation(id) {

    document

        .querySelectorAll(".conversation-item")

        .forEach(item => item.classList.remove("active"));

}