package com.github.iskrendev.insuranceprogram.models;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record VehicleInsuranceUpdateDTO(
        String firstName,
        String familyName,
        String zipCode,
        String city,
        String address,
        String telephone,
        String email,
        Integer duration,
        BigDecimal paymentPerMonth,
        LocalDate endDate,
        String vehicleMake,
        String vehicleModel,
        Integer vehicleYear,
        String licensePlateNumber
) {
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth) {
        return BigDecimal.valueOf(duration).multiply(paymentPerMonth);
    }
}
