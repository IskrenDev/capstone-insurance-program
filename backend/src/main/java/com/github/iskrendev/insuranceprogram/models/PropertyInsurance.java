package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.github.iskrendev.insuranceprogram.common.Insurance;

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

) implements Insurance {

    @Override
    public String getFirstName() {
        return firstName;
    }
    @Override
    public String getFamilyName() {
        return familyName;
    }
    @Override
    public String getZipCode() {
        return zipCode;
    }
    @Override
    public String getCity() {
        return city;
    }
    @Override
    public String getTelephone() {
        return telephone;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public InsuranceType getType() {
        return type;
    }
    @Override
    public Integer getDuration() {
        return duration;
    }
    @Override
    public BigDecimal getPaymentPerMonth() {
        return paymentPerMonth;
    }
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }
    @Override
    public LocalDate getEndDate() {
        return endDate;
    }
    @Override
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth) {
        return BigDecimal.valueOf(duration).multiply(paymentPerMonth);
    }
}