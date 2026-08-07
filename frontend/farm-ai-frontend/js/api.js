// ===========================================
// Generic API Request
// ===========================================

async function apiRequest(url,
                          method = "GET",
                          body = null,
                          isForm = false) {

    const options = {

        method: method,

        headers: {

            Authorization: "Bearer " + token

        }

    };

    if (body) {

        if (isForm) {

            options.body = body;

        }

        else {

            options.headers["Content-Type"] = "application/json";

            options.body = JSON.stringify(body);

        }

    }

    const response = await fetch(

        API_BASE_URL + url,

        options

    );

    if (!response.ok) {

        throw new Error(await response.text());

    }

    return response;

}

// ===========================================
// Create Conversation
// ===========================================

async function createConversation() {

    const response = await apiRequest(

        "/api/chat/conversation",

        "POST"

    );

    const data = await response.json();

    currentConversationId = data.conversationId;

    uploadedDocumentId = null;
    uploadedImageId = null;

    return data;

}



// ===========================================
// Chat History
// ===========================================

async function loadHistory() {

    const response = await apiRequest(

        "/api/chat/history"

    );

    return await response.json();

}



// ===========================================
// Load Conversation
// ===========================================

async function loadConversation(conversationId) {

    currentConversationId = conversationId;

    // Reset upload state when switching conversations
    uploadedDocumentId = null;
    uploadedImageId = null;

    const response = await apiRequest(

        "/api/chat/" + conversationId

    );

    return await response.json();

}



// ===========================================
// Delete Conversation
// ===========================================

async function deleteConversation(conversationId) {

    await apiRequest(

        "/api/chat/" + conversationId,

        "DELETE"

    );

}



// ===========================================
// Delete All History
// ===========================================

async function deleteAllHistory() {

    await apiRequest(

        "/api/chat/history",

        "DELETE"

    );

}
// ===========================================
// Normal Chat
// ===========================================

async function sendChat(question) {

    if (!currentConversationId) {

        await createConversation();

    }

    const response = await apiRequest(

        "/api/ai/chat/" + currentConversationId,

        "POST",

        {

            question: question

        }

    );

    return await response.text();

}

// ===========================================
// Upload PDF
// ===========================================

async function uploadPdf(file) {

    if (!currentConversationId) {

        await createConversation();

    }

    const form = new FormData();

    form.append("file", file);

    const response = await apiRequest(

        "/api/rag/upload/" + currentConversationId,

        "POST",

        form,

        true

    );

    const data = await response.json();

    uploadedDocumentId = data.documentId;

    uploadedImageId = null;

    return data;

}



// ===========================================
// Upload Image
// ===========================================

async function uploadImage(file) {

    if (!currentConversationId) {

        await createConversation();

    }

    const form = new FormData();

    form.append("file", file);

    const response = await apiRequest(

        "/api/rag/upload/" + currentConversationId,

        "POST",

        form,

        true

    );

    const data = await response.json();

    uploadedImageId = data.documentId;

    uploadedDocumentId = null;

    return data;

}

// ===========================================
// Image Question Answer
// ===========================================

async function askImage(question) {

    if (!uploadedImageId) {

        throw new Error("Please upload an image first.");

    }

    const response = await apiRequest(

        "/api/ai/image/chat/" + currentConversationId,

        "POST",

        {

            documentId: uploadedImageId,

            question: question

        }

    );

    return await response.text();

}

// ===========================================
// Analyze Farm
// ===========================================

async function analyzeFarm(request) {

    const response = await apiRequest(

        "/api/ai/analyze",

        "POST",

        request

    );

    return await response.json();

}

// ===========================================
// Load Farm History
// ===========================================

async function loadFarmHistory(page = 0, size = 5) {

    const response = await apiRequest(

        "/api/farm/history?pageNumber=" +
        page +
        "&pageSize=" +
        size

    );

    return await response.json();

}

// ===========================================
// Delete Farm Analysis
// ===========================================

async function deleteFarmHistory(id) {

    await apiRequest(

        "/api/farm/delete/" + id,

        "DELETE"

    );

}

// ===========================================
// Refresh Chat History
// ===========================================

async function refreshHistory() {

    const history = await loadHistory();

    renderConversationHistory(history);

}