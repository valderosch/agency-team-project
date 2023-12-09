package com.team.travel.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void create(MultipartFile image, String path);
    void update(MultipartFile image, String path);
    void delete(String path);
}
