package com.hardik.farmapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String fileType;
    private Long fileSize;
    private String storagePath;
    private String fileCategory;
    private LocalDateTime uploadedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private Users uploadedBy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "conversation_id")
    private ChatConversation conversation;
}
