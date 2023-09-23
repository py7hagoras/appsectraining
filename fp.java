package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileController {

    // Let's assume the application wants to allow users to view certain files from a 'files' directory
    private static final String BASE_DIRECTORY = "/var/www/uploads/";

    @GetMapping("/getFile")
    public void getFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        
        FileInputStream fileInputStream = new FileInputStream(BASE_DIRECTORY + filename);
        
        // Copy the file contents to the response output stream
        FileCopyUtils.copy(fileInputStream, response.getOutputStream());
    }
}
