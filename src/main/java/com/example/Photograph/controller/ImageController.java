package com.example.Photograph.controller;

import com.example.Photograph.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private FileService fileService;

    @GetMapping("/{name}")
    private ResponseEntity<Resource> getImage(@PathVariable String name){
        Resource resource = fileService.getImageResource(name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileService.getContentType(resource)))
                .body(resource);
    }
}
