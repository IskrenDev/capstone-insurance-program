package com.github.iskrendev.insuranceprogram.exceptions;

public class AllInsurancesRetrievalException extends RuntimeException {
    public AllInsurancesRetrievalException() {
        super("Error fetching insurances");
    }

    public AllInsurancesRetrievalException(String message) {
        super(message);
    }
}
