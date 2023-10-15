package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "upload image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok().body(this.imageService.upload(file));
    }
}
