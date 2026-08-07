    package com.hardik.farmapp.Controller;

    import com.hardik.farmapp.DTO.ChatRequest;
    import com.hardik.farmapp.DTO.ImageChatResponses;
    import com.hardik.farmapp.Service.ChatAiService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    import reactor.core.publisher.Flux;

    import java.io.IOException;

    @RestController
    @RequestMapping("/api/ai")
    public class AiChatController {

        @Autowired
        private ChatAiService chatAiService;

        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        @PostMapping("/chat/{conversationId}")
        public String chat(@PathVariable String conversationId,
                                 @RequestBody ChatRequest request) {

            return chatAiService.chatWithAi(conversationId, request);
        }

        @PostMapping("/image/chat/{conversationId}")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public String imageChat(@PathVariable String conversationId ,@RequestBody ImageChatResponses imageChatResponses) throws IOException {
            return chatAiService.chatWithImage(conversationId,imageChatResponses);
        }
    }
