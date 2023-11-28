package com.github.iskrendev.insuranceprogram.models;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record VehicleInsuranceUpdateDTO(
        @Id
        String id,
        String firstName,
        String familyName,
        String zipCode,
        String city,
        String telephone,
        String email,
        Integer duration,
        BigDecimal paymentPerMonth,
        LocalDate startDate,
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
