package com.team.travel.controller;

import com.team.travel.dto.ErrorResponse;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DuplicateException;
import com.team.travel.exception.DeleteException;
import com.team.travel.exception.ImageProcessingException;
import com.team.travel.exception.InvalidFileException;
import com.team.travel.exception.FileNotFoundException;
import com.team.travel.exception.TourUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.ZonedDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(DatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseFetchException(DatabaseFetchException e, WebRequest request){
        LOGGER.error("Could not fetch data from the database", e);
        return getResponse(HttpStatus.NOT_FOUND, request, e.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException e, WebRequest request) {
        LOGGER.error("Attempted to insert duplicate data to the database", e);
        return getResponse(HttpStatus.BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ErrorResponse> handleDeleteException(DeleteException e, WebRequest request) {
        LOGGER.error("Attempted to delete data with non-empty relation from the database", e);
        return getResponse(HttpStatus.FORBIDDEN, request, e.getMessage());
    }

    @ExceptionHandler(ImageProcessingException.class)
    public ResponseEntity<ErrorResponse> handleImageProcessingException(ImageProcessingException e, WebRequest request) {
        LOGGER.error("Couldn't process image", e);
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, e.getMessage());
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileException(InvalidFileException e, WebRequest request) {
        LOGGER.error("Invalid file format", e);
        return getResponse(HttpStatus.BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchFileException(FileNotFoundException e, WebRequest request) {
        LOGGER.error(e.getMessage());
        return getResponse(HttpStatus.NOT_FOUND, request, e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, WebRequest request) {
        LOGGER.error("Uploaded file exceeds the maximum allowed size of " + maxFileSize);
        return getResponse(HttpStatus.PAYLOAD_TOO_LARGE, request, e.getMessage() + " (" + maxFileSize + ")");
    }

    @ExceptionHandler(TourUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleTourUnavailableException(TourUnavailableException e, WebRequest request) {
        LOGGER.error("Attempted to order unavailable tour", e);
        return getResponse(HttpStatus.FORBIDDEN, request, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        LOGGER.error("Attempted to log in with incorrect credentials", e);
        return getResponse(HttpStatus.UNAUTHORIZED, request, "такого акаунту не існує або ви ввели неправильний пароль");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        LOGGER.error("Attempted to perform forbidden action", e);
        return getResponse(HttpStatus.FORBIDDEN, request, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        LOGGER.error("An unexpected error occurred", e);
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, "An unexpected error occurred");
    }

    private ResponseEntity<ErrorResponse> getResponse(HttpStatus status, WebRequest request, String message) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorMessage = new ErrorResponse(status.value(), status.getReasonPhrase(), ZonedDateTime.now(), path, message);
        return new ResponseEntity<>(errorMessage, status);
    }
}
