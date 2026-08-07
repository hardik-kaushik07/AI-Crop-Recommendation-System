package com.hardik.farmapp.Service;

import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.ChatRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChatRepository chatRepository;


    private Users getCurrentUser(Authentication authentication) {

        Users user = usersRepository.findByEmail(authentication.getName());

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public ChatConversation createConversation(Authentication authentication) {

        Users user = getCurrentUser(authentication);

        ChatConversation conversation = ChatConversation.builder()
                .conversationId(UUID.randomUUID().toString())
                .user(user)
                .build();

        return chatRepository.save(conversation);
    }

    public List<ChatConversation> getAllHistory(Authentication authentication) {

        Users user = getCurrentUser(authentication);

        return chatRepository.findByUserOrderByIdDesc(user);
    }

    public ChatConversation getConversation(String conversationId) {

        return chatRepository.findByConversationId(conversationId)
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found"));
    }

    public void deleteConversation(String conversationId) {

        ChatConversation conversation = getConversation(conversationId);

        chatRepository.delete(conversation);
    }

    public void deleteAllHistory(Authentication authentication) {

        Users user = getCurrentUser(authentication);

        List<ChatConversation> conversations =
                chatRepository.findByUserOrderByIdDesc(user);

        chatRepository.deleteAll(conversations);
    }
}
