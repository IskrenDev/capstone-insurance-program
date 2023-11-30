package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
public record VehicleInsuranceDTO(String firstName,
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
                                  String vehicleMake,
                                  String vehicleModel,
                                  Integer vehicleYear,
                                  String licensePlateNumber

) {
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal paymentPerMonth) {
        return BigDecimal.valueOf(duration).multiply(paymentPerMonth);
    }
}
