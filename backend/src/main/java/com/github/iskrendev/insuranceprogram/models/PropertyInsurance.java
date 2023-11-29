package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Document(collection = "property_insurance")
public record PropertyInsurance(
        @Id
        String id,
        String firstName,
        String familyName,
        String zipCode,
        String city,
        String address,
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