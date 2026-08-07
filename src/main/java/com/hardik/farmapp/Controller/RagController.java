package com.hardik.farmapp.Controller;

import com.hardik.farmapp.Service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    @Autowired
    private RagService ragService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("upload/{conversationId}")
    public ResponseEntity<?> uploadDocument(
            @PathVariable String conversationId, @RequestParam MultipartFile file, Authentication authentication) throws IOException {
        return ResponseEntity.ok(ragService.uploadDocument(conversationId, file, authentication));
    }

}
