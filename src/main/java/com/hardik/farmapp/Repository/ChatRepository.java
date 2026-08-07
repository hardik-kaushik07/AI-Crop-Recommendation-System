package com.hardik.farmapp.Repository;

import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatConversation,Long>{

    List<ChatConversation> findByUserOrderByIdDesc(Users user);

    Optional<ChatConversation> findByConversationId(String conversationId);

    long countByUser(Users user);

}
