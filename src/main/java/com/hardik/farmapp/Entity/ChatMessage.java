package com.hardik.farmapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Data@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String role;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String message;

    private LocalDateTime createdAt;


    private Long documentId;
    private String fileName;
    private String storedFileName;
    private String fileType;
    private String fileCategory;

    @ManyToOne
    @JsonIgnore
    private ChatConversation conversation;
}
