package com.github.iskrendev.insuranceprogram.exceptions;

public class InvalidSearchCriteriaException extends RuntimeException {
    public InvalidSearchCriteriaException() {
        super("Invalid search criteria: Please provide at least a first name or a family name.");
    }

    public InvalidSearchCriteriaException(String message) {
        super(message);
    }
}
