package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
public record PropertyInsuranceDTO(String firstName,
                                   String familyName,
                                   String zipCode,
                                   String city,
                                   String telephone,
                                   String email,
                                   InsuranceType type,
                                   Integer duration,
                                   BigDecimal paymentPerMonth,
                                   LocalDate startDate,
                                   LocalDate endDate,
                                   String propertyType,
                                   String propertyAddress,
                                   Integer constructionYear

) {
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth) {
        return BigDecimal.valueOf(duration).multiply(paymentPerMonth);
    }
}

