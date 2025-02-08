package org.hanteo.exception;

public class CategoryException extends RuntimeException {
    private String message;

    public CategoryException(String message) {
        this.message = message;
    }
}
