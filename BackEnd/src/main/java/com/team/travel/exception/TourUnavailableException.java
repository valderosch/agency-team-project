package com.team.travel.exception;

public class TourUnavailableException extends RuntimeException {
    public TourUnavailableException(String message) {
        super(message);
    }
}
