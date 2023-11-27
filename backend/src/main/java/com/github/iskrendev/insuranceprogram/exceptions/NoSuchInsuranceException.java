package com.github.iskrendev.insuranceprogram.exceptions;

public class NoSuchInsuranceException extends RuntimeException {
        public NoSuchInsuranceException() {
            super("There is no insurance with this id");
        }

        public NoSuchInsuranceException(String message) {
            super(message);
        }
}
