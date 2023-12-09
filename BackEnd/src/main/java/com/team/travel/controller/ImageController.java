package com.team.travel.controller;

import com.team.travel.service.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@CrossOrigin(origins = {"${application.cors.origin}"})
@RequestMapping(value = "/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> upload(@RequestParam MultipartFile image, @RequestParam String folderName) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String relativePath = folderName + "/" + UUID.randomUUID() + "." + extension;
        service.create(image, relativePath);
        return new ResponseEntity<>(relativePath, HttpStatus.CREATED);
    }

    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestParam MultipartFile image, @RequestParam String path) {
        service.update(image, path);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@RequestParam String path) {
        service.delete(path);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
