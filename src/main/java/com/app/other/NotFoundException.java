package com.app.other;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id, String entity) {
        super(entity+" not found with ID : "+id);
    }
}