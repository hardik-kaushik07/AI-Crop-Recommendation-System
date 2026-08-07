package com.hardik.farmapp.Repository;

import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findTopByConversationOrderByCreatedAtDesc(ChatConversation conversation);
}
