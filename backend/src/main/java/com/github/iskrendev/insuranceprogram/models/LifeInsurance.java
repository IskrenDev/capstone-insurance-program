package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.github.iskrendev.insuranceprogram.common.Insurance;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Document(collection = "life insurance")
public record LifeInsurance(
        @Id
        String id,
        String firstName,
        String familyName,
        String zipCode,
        String city,
        String telephone,
        String email,
        InsuranceType type,
        int duration,
        BigDecimal rate,
        LocalDate startDate,
        LocalDate endDate,
        boolean hasHealthIssues,
        String healthConditionDetails

) implements Insurance {

    @Override
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal rate) {
        return BigDecimal.valueOf(duration).multiply(rate);
    }
}