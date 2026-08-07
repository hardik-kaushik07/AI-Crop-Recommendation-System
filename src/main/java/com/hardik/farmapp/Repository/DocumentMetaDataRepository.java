package com.hardik.farmapp.Repository;

import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Entity.DocumentMetaData;
import com.hardik.farmapp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMetaDataRepository extends JpaRepository<DocumentMetaData, Long> {
    List<DocumentMetaData> findByConversation(ChatConversation conversation);

    long countByUploadedByAndFileCategory(Users user, String fileCategory);
}
