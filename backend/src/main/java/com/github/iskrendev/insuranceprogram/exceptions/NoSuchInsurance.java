package com.github.iskrendev.insuranceprogram.exceptions;

public class NoSuchInsurance extends RuntimeException {
        public NoSuchInsurance() {
            super("There is no insurance with this id");
        }

        public NoSuchInsurance(String message) {
            super(message);
        }
}
