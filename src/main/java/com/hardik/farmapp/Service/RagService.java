package com.hardik.farmapp.Service;

import com.hardik.farmapp.DTO.UploadResponses;
import com.hardik.farmapp.Entity.ChatConversation;
import com.hardik.farmapp.Entity.ChatMessage;
import com.hardik.farmapp.Entity.DocumentMetaData;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.ChatMessageRepository;
import com.hardik.farmapp.Repository.ChatRepository;
import com.hardik.farmapp.Repository.DocumentMetaDataRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;

@Service
public class RagService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private DocumentMetaDataRepository documentMetaDataRepository;

        @Autowired
    private TokenTextSplitter tokenTextSplitter;

    @Autowired
    private SimpleVectorStore vectorStore;


    @Value("${app.document.upload-dir}")
    private String uploadDir;


    public UploadResponses uploadDocument(String conversationId, MultipartFile file, Authentication authentication) throws IOException {

        Users user = usersRepository.findByEmail(authentication.getName());

        if(user==null){
            throw new UsernameNotFoundException("User Not Found");
        }

        String originalName = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + originalName;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path destination =
                uploadPath.resolve(storedName);

        Files.createDirectories(destination.getParent());
        Files.copy(file.getInputStream(), destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        System.out.println("uploadDir = " + uploadDir);
        System.out.println("uploadPath = " + uploadPath.toAbsolutePath());
        System.out.println("destination = " + destination.toAbsolutePath());
        System.out.println("exists before = " + Files.exists(uploadPath));

        ChatConversation conversation = chatRepository.findByConversationId(conversationId)
                .orElseThrow(()-> new RuntimeException("Conversation Not Found"));

        String contentType = file.getContentType();
        String fileCategory;

        if(contentType!=null && contentType.startsWith("image/")){
            fileCategory="IMAGE";
        }
        else{
            fileCategory="DOCUMENT";
        }


        DocumentMetaData metaData = DocumentMetaData.builder()
                .originalFileName(originalName)
                .storedFileName(storedName)
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .storagePath(destination.toAbsolutePath().toString())
                .fileCategory(fileCategory)
                .uploadedAt(LocalDateTime.now())
                .uploadedBy(user)
                .conversation(conversation)
        .build();

        DocumentMetaData saved = documentMetaDataRepository.save(metaData);
        if("DOCUMENT".equals(fileCategory)) {
            indexDocument(new File(saved.getStoragePath()), saved.getId());
        }

        ChatMessage uploadMessage = ChatMessage.builder()
                .documentId(saved.getId())
                .role("USER")
                .message("")
                .fileCategory(saved.getFileCategory())
                .createdAt(saved.getUploadedAt())
                .fileName(saved.getOriginalFileName())
                .storedFileName(saved.getStoredFileName())
                .fileType(saved.getFileType())
                .conversation(saved.getConversation())
                .build();

        chatMessageRepository.save(uploadMessage);

        String message = "IMAGE".equals(fileCategory)
                ?"Image Uploaded Successfully."
                :"Document Uploaded Successfully.";
        return new UploadResponses(saved.getId(),
                saved.getOriginalFileName(),
                saved.getStoredFileName(),
                saved.getFileType(),
                saved.getFileSize() ,
                message);
    }

    private void indexDocument(File file, Long documentId){

        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());

        List<Document> documents;

        if(file.getName().toLowerCase().endsWith(".pdf")){

            PagePdfDocumentReader reader = new PagePdfDocumentReader(new FileSystemResource(file));

            documents = reader.get();
        }
        else if(file.getName().toLowerCase().endsWith(".txt")){

            TextReader reader = new TextReader(new FileSystemResource(file));
            documents = reader.get();
        }
        else{
            return;
        }

        List<Document> chunks = tokenTextSplitter.apply(documents);

        for (Document document : chunks) {
            document.getMetadata().put("documentId",documentId );
        }

        vectorStore.add(chunks);

    }

//    public RagResponse askQuestion(RagRequest request, Authentication authentication){
//
//        Users user = usersRepository.findByEmail(authentication.getName());
//
//        if(user == null){
//            throw new UsernameNotFoundException("User Not Found");
//        }
//        DocumentMetaData document = documentMetaDataRepository
//                .findById(request.getDocumentId())
//                .orElseThrow(()-> new RuntimeException("Document Not Find"));
//
//
//        List<Document> similarDocument = vectorStore.similaritySearch(
//                SearchRequest.builder()
//                        .query(request.getQuestion())
//                        .topK(5)
//                        .filterExpression("documentId == " + document.getId())
//                        .build()
//        );
//
//        StringBuilder context = new StringBuilder();
//        for (Document doc: similarDocument){
//                context.append(doc.getText()).append("\n\n");
//        }
//
//        String prompt = """
//You are an AI assistant.
//
//Answer ONLY from the given context.
//
//If the answer is not present in the context,
//reply exactly:
//
//"I could not find this information in the uploaded document."
//
//Context:
//
//%s
//
//Question:
//
//%s
//""".formatted(context.toString(), request.getQuestion());
//
//        String answer = chatClient.prompt()
//                .user(prompt)
//                .call()
//                .content();
//
//        RagChatHistory history = RagChatHistory.builder()
//                .question(request.getQuestion())
//                .answer(answer)
//                .user(user)
//                .document(document)
//                .build();
//
//        ragChatRepository.save(history);
//
//        return new RagResponse(answer);
//    }

    public String getDocumentContext(String conversationId, String question){

        ChatConversation conversation = chatRepository.findByConversationId(conversationId)
                .orElseThrow(()->new RuntimeException("Conversation Not Found"));

        List<DocumentMetaData> documents = documentMetaDataRepository.findByConversation(conversation);

        if(documents.isEmpty()){
            return "";
        }
        StringBuilder context = new StringBuilder();
        for (DocumentMetaData document : documents) {

            List<Document> similarDocs = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(question)
                            .topK(3)
                            .filterExpression("documentId == " + document.getId())
                            .build()
            );

            for (Document doc : similarDocs) {
                context.append(doc.getText()).append("\n\n");
            }
        }

        return context.toString();
    }
}
