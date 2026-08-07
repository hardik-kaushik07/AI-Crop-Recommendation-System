package com.hardik.farmapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponses {

    private Long documentId;
    private String filenames;
    private String storedFileName;
    private String fileType;
    private Long fileSize;
    private String message;
}
