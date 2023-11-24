package com.github.iskrendev.insuranceprogram.common;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Insurance {
    String getFirstName();
    String getFamilyName();
    String getZipCode();
    String getCity();
    String getTelephone();
    String getEmail();
    InsuranceType getType();
    Integer getDuration();
    BigDecimal getPaymentPerMonth();
    LocalDate getStartDate();
    LocalDate getEndDate();
    BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth);
}
