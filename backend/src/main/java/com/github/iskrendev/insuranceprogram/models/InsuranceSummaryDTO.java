package com.github.iskrendev.insuranceprogram.models;

import java.math.BigDecimal;

public record InsuranceSummaryDTO(
        BigDecimal totalAmount,
        Long lifeInsuranceCount,
        Long propertyInsuranceCount,
        Long vehicleInsuranceCount
) {
}
