package com.github.iskrendev.insuranceprogram.models;

import com.github.iskrendev.insuranceprogram.common.Insurance;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
public record DTOVehicleInsurance(String firstName,
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
                                  String vehicleMake,
                                  String vehicleModel,
                                  Integer vehicleYear,
                                  String licensePlateNumber

) implements Insurance {

    @Override
    public BigDecimal calculateInsuranceAmount(int duration, BigDecimal rate) {
        return BigDecimal.valueOf(duration).multiply(rate);
    }
}
