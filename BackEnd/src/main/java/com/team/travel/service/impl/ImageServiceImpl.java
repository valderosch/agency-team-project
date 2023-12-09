package com.team.travel.service.impl;

import com.team.travel.exception.FileNotFoundException;
import com.team.travel.exception.ImageProcessingException;
import com.team.travel.exception.InvalidFileException;
import com.team.travel.service.ImageService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String[] ALLOWED_EXTS = {"jpeg", "jpg", "png", "gif"};
    private final Path root;

    public ImageServiceImpl(@Value("${image.storage}") String path) {
        this.root = Paths.get(path);
    }

    @Override
    public void create(MultipartFile image, String path) {
        if (isNotImage(image.getOriginalFilename())) {
            throw new InvalidFileException("Invalid file format. Only images " + Arrays.toString(ALLOWED_EXTS) + " are allowed");
        }
        try {
            Files.copy(image.getInputStream(), root.resolve(path));
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("Couldn't save image. The folder to save the file in doesn't exist");
        } catch (Exception e) {
            throw new ImageProcessingException("Couldn't save image: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(MultipartFile image, String path) {
        if (isNotImage(image.getOriginalFilename())) {
            throw new InvalidFileException("Invalid file format. Only images " + Arrays.toString(ALLOWED_EXTS) + " are allowed");
        }
        if (!Files.exists(root.resolve(path))) {
            throw new FileNotFoundException("Couldn't find image to update");
        }
        try {
            Files.copy(image.getInputStream(), root.resolve(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ImageProcessingException("Couldn't update image: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            Files.delete(root.resolve(path));
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("Couldn't delete image. The file doesn't exist");
        } catch (Exception e) {
            throw new ImageProcessingException("Couldn't delete image: " + e.getMessage(), e);
        }
    }

    private boolean isNotImage(String path) {
        String extension = FilenameUtils.getExtension(path);
        return !Arrays.asList(ALLOWED_EXTS).contains(extension.toLowerCase());
    }
}
