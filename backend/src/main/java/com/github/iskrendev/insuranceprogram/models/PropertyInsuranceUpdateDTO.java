package com.github.iskrendev.insuranceprogram.models;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PropertyInsuranceUpdateDTO(
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
        String propertyType,
        String propertyAddress,
        Integer constructionYear
) {
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth) {
        return BigDecimal.valueOf(duration).multiply(paymentPerMonth);
    }
}
