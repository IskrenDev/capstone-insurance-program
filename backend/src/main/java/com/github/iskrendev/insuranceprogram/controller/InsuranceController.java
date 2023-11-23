package com.github.iskrendev.insuranceprogram.controller;

import com.github.iskrendev.insuranceprogram.models.*;
import com.github.iskrendev.insuranceprogram.service.InsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/insurances")
public class InsuranceController {
    private InsuranceService insuranceService;
    @PostMapping("/life")
    public LifeInsurance addLifeInsurance(@RequestBody DTOLifeInsurance lifeInsurance) {
        LifeInsurance newLifeInsurance = LifeInsurance.builder()
                .firstName(lifeInsurance.firstName())
                .familyName(lifeInsurance.familyName())
                .zipCode(lifeInsurance.zipCode())
                .city(lifeInsurance.city())
                .telephone(lifeInsurance.telephone())
                .email(lifeInsurance.email())
                .type(lifeInsurance.type())
                .duration(lifeInsurance.duration())
                .paymentPerMonth(lifeInsurance.paymentPerMonth())
                .startDate(lifeInsurance.startDate())
                .endDate(lifeInsurance.endDate())
                .hasHealthIssues(lifeInsurance.hasHealthIssues())
                .healthConditionDetails(lifeInsurance.healthConditionDetails())
                .build();
        return insuranceService.addLifeInsurance(newLifeInsurance);
    }

    @PostMapping("/property")
    public PropertyInsurance addPropertyInsurance(@RequestBody DTOPropertyInsurance propertyInsurance) {
        PropertyInsurance newPropertyInsurance = PropertyInsurance.builder()
                .firstName(propertyInsurance.firstName())
                .familyName(propertyInsurance.familyName())
                .zipCode(propertyInsurance.zipCode())
                .city(propertyInsurance.city())
                .telephone(propertyInsurance.telephone())
                .email(propertyInsurance.email())
                .type(propertyInsurance.type())
                .duration(propertyInsurance.duration())
                .paymentPerMonth(propertyInsurance.paymentPerMonth())
                .startDate(propertyInsurance.startDate())
                .endDate(propertyInsurance.endDate())
                .propertyType(propertyInsurance.propertyType())
                .propertyAddress(propertyInsurance.propertyAddress())
                .constructionYear(propertyInsurance.constructionYear())
                .build();
        return insuranceService.addPropertyInsurance(newPropertyInsurance);
    }

    @PostMapping("/vehicle")
    public VehicleInsurance addVehicleInsurance(@RequestBody DTOVehicleInsurance vehicleInsurance) {
        VehicleInsurance newVehicleInsurance = VehicleInsurance.builder()
                .firstName(vehicleInsurance.firstName())
                .familyName(vehicleInsurance.familyName())
                .zipCode(vehicleInsurance.zipCode())
                .city(vehicleInsurance.city())
                .telephone(vehicleInsurance.telephone())
                .email(vehicleInsurance.email())
                .type(vehicleInsurance.type())
                .duration(vehicleInsurance.duration())
                .paymentPerMonth(vehicleInsurance.paymentPerMonth())
                .startDate(vehicleInsurance.startDate())
                .endDate(vehicleInsurance.endDate())
                .vehicleMake(vehicleInsurance.vehicleMake())
                .vehicleModel(vehicleInsurance.vehicleModel())
                .vehicleYear(vehicleInsurance.vehicleYear())
                .licensePlateNumber(vehicleInsurance.licensePlateNumber())
                .build();
        return insuranceService.addVehicleInsurance(newVehicleInsurance);
    }
}
