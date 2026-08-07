package com.hardik.farmapp.Controller;

import com.hardik.farmapp.Entity.DocumentMetaData;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.DocumentMetaDataRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private DocumentMetaDataRepository documentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/{documentId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long documentId,
            Authentication authentication) {

        Users user = usersRepository.findByEmail(authentication.getName());

        DocumentMetaData document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!document.getUploadedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        File file = new File(document.getStoragePath());

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + document.getOriginalFileName() + "\""
                )
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .body(resource);

    }

}