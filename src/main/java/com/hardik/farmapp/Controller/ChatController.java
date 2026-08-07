package com.hardik.farmapp.Controller;

import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/conversation")
    public ChatConversation createConversation(Authentication authentication){
        return chatService.createConversation(authentication);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/history")
    public List<ChatConversation> getAllHistory(Authentication authentication){
        return chatService.getAllHistory(authentication);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{conversationId}")
    public ChatConversation getConversation(@PathVariable String conversationId) {
        return chatService.getConversation(conversationId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{conversationId}")
    public String deleteConversation(@PathVariable String conversationId) {
        chatService.deleteConversation(conversationId);
        return "Conversation deleted successfully.";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/history")
    public String deleteAllHistory(Authentication authentication) {
        chatService.deleteAllHistory(authentication);
        return "All chat history deleted successfully.";
    }

}
