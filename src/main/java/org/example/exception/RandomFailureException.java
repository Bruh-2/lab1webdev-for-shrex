package org.example.exception;

public class RandomFailureException extends RuntimeException {
    public RandomFailureException(String message) {
        super(message);
    }
}
