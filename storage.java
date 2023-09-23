package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class FileController {

    private final String UPLOAD_DIRECTORY = "./uploads/";

    // Route for handling file uploads
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        // Construct filename using current date-time as prefix
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                + "-" + file.getOriginalFilename();

        // Determine the path to save the file and copy the uploaded file content there
        Path copyLocation = Paths.get(UPLOAD_DIRECTORY + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok("File uploaded successfully. Stored as: " + fileName);
    }

    // Route for handling file downloads
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        // Determine the path to the file
        Path filePath = Paths.get(UPLOAD_DIRECTORY).toAbsolutePath().resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists
        if (!resource.exists()) {
            throw new Exception("File not found: " + filename);
        }

        // Return the file for download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
