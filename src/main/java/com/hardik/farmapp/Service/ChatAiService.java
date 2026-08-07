    package com.hardik.farmapp.Service;

    import com.hardik.farmapp.DTO.ChatRequest;
    import com.hardik.farmapp.DTO.ImageChatResponses;
    import com.hardik.farmapp.Entity.ChatConversation;
    import com.hardik.farmapp.Entity.ChatMessage;
    import com.hardik.farmapp.Entity.DocumentMetaData;
    import com.hardik.farmapp.Repository.ChatMessageRepository;
    import com.hardik.farmapp.Repository.ChatRepository;
    import com.hardik.farmapp.Repository.DocumentMetaDataRepository;
    import org.springframework.ai.chat.client.ChatClient;
    import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
    import org.springframework.ai.chat.memory.ChatMemory;
    import org.springframework.ai.tool.ToolCallbackProvider;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.io.FileSystemResource;
    import org.springframework.core.io.Resource;
    import org.springframework.stereotype.Service;
    import org.springframework.util.MimeTypeUtils;

    import java.io.IOException;
    import java.time.LocalDateTime;

    @Service
    public class ChatAiService {

        @Autowired
        private ChatRepository chatRepository;

        @Autowired
        private ChatMessageRepository chatMessageRepository;

        @Autowired
        private DocumentMetaDataRepository documentMetaDataRepository;

        @Autowired
        private RagService ragService;

        private final ChatClient chatClient;

        public ChatAiService(ChatClient.Builder builder,
                             ToolCallbackProvider toolCallbackProvider,
                             ChatMemory chatMemory) {

            this.chatClient = builder
                    .defaultToolCallbacks(toolCallbackProvider)
                    .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                    .build();
        }

        private final String system = """
                You are Farm AI, an intelligent agricultural assistant designed exclusively for farmers.
                Answer only agriculture and farming related questions.
                Use weather tools whenever weather is required.
                Use crop price tools whenever prices are required.
                If the question is outside farming, politely refuse.
                """;

        public String chatWithAi(String conversationId, ChatRequest request) {

            ChatConversation conversation = chatRepository
                    .findByConversationId(conversationId)
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));

            ChatMessage userMessage = new ChatMessage();
            userMessage.setRole("USER");
            userMessage.setMessage(request.getQuestion());
            userMessage.setCreatedAt(LocalDateTime.now());
            userMessage.setConversation(conversation);

            chatMessageRepository.save(userMessage);

            String context = ragService.getDocumentContext(conversationId, request.getQuestion());

            String finalPrompt;

            if (context.isBlank()) {
                finalPrompt = request.getQuestion();
            } else {
                finalPrompt = """
                        You are Farm AI.
    
                        Answer ONLY using the uploaded document.
    
                        If the answer is not present, reply:
                        "I could not find this information in the uploaded document."
    
                        Context:
                        %s
    
                        Question:
                        %s
                        """.formatted(context, request.getQuestion());
            }

            String aiResponse;

            try {

                aiResponse = chatClient.prompt()
                        .advisors(advisor ->
                                advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                        .system(system)
                        .user(finalPrompt)
                        .call()
                        .content();

            } catch (Exception e) {

                aiResponse = "Sorry, the AI service is temporarily busy. Please try again later.";
            }

            ChatMessage assistantMessage = new ChatMessage();
            assistantMessage.setRole("ASSISTANT");
            assistantMessage.setMessage(aiResponse);
            assistantMessage.setCreatedAt(LocalDateTime.now());
            assistantMessage.setConversation(conversation);

            chatMessageRepository.save(assistantMessage);

            return aiResponse;
        }

        public String chatWithImage(String conversationId, ImageChatResponses imageChatResponses) throws IOException {

            ChatConversation conversation = chatRepository
                    .findByConversationId(conversationId)
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));

            DocumentMetaData image = documentMetaDataRepository
                    .findById(imageChatResponses.getDocumentId())
                    .orElseThrow(()-> new RuntimeException("Image Not Found"));

            if(!"IMAGE".equals(image.getFileCategory())){
                throw new RuntimeException("Selected file is not Image");
            }

            Resource imageResource = new FileSystemResource(image.getStoragePath());

            ChatMessage userMessage = chatMessageRepository
                    .findTopByConversationOrderByCreatedAtDesc(conversation)
                    .orElse(null);

            if (userMessage != null
                    && "USER".equals(userMessage.getRole())
                    && userMessage.getDocumentId() != null
                    && (userMessage.getMessage() == null || userMessage.getMessage().isBlank())) {

                userMessage.setMessage(imageChatResponses.getQuestion());

                chatMessageRepository.save(userMessage);

            } else {

                userMessage = new ChatMessage();

                userMessage.setRole("USER");
                userMessage.setMessage(imageChatResponses.getQuestion());
                userMessage.setCreatedAt(LocalDateTime.now());
                userMessage.setConversation(conversation);

                chatMessageRepository.save(userMessage);
            }

            chatMessageRepository.save(userMessage);

            System.out.println("==================================");
            System.out.println("Image Path      : " + image.getStoragePath());
            System.out.println("Image Type      : " + image.getFileType());
            System.out.println("File Exists     : " + imageResource.exists());
            System.out.println("File Readable   : " + imageResource.isReadable());
            System.out.println("File Length     : " + imageResource.contentLength());
            System.out.println("==================================");
            System.out.println("Document Id : " + imageChatResponses.getDocumentId());
            System.out.println("Question    : " + imageChatResponses.getQuestion());

                String aiResponse;
                try{
                    aiResponse = chatClient.prompt()
                            .advisors(advisor ->
                                    advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                            .system(system)
                            .user(user -> user
                                    .text(imageChatResponses.getQuestion())
                                    .media(
                                            MimeTypeUtils.parseMimeType(image.getFileType()),
                                            imageResource))
                            .call()
                            .content();
                } catch (Exception e) {
                    aiResponse = "Sorry, the AI service is temporarily busy. Please try again later.";
                }


            ChatMessage assistantMessage = new ChatMessage();

            assistantMessage.setRole("ASSISTANT");
            assistantMessage.setMessage(aiResponse);
            assistantMessage.setCreatedAt(LocalDateTime.now());
            assistantMessage.setConversation(conversation);

            chatMessageRepository.save(assistantMessage);

            return aiResponse;

        }
    }