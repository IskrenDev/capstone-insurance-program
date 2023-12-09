package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.*;
import com.github.iskrendev.insuranceprogram.services.PropertyInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/property")
public class PropertyInsuranceController {
    private PropertyInsuranceService propertyInsuranceService;
    @GetMapping
    public List<PropertyInsurance> getAllPropertyInsurances() {
        return propertyInsuranceService.getAllPropertyInsurances();
    }

    @GetMapping("/{id}")
    public PropertyInsurance getPropertyInsuranceById(@PathVariable String id) {
        return propertyInsuranceService.getPropertyInsuranceById(id);
    }

    @PostMapping
    public PropertyInsurance addPropertyInsurance(@RequestBody PropertyInsuranceDTO propertyInsurance) {
        PropertyInsurance newPropertyInsurance = PropertyInsurance.builder()
                .firstName(propertyInsurance.firstName())
                .familyName(propertyInsurance.familyName())
                .zipCode(propertyInsurance.zipCode())
                .city(propertyInsurance.city())
                .address(propertyInsurance.address())
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
        return propertyInsuranceService.addPropertyInsurance(newPropertyInsurance);
    }

    @PutMapping("/{id}")
    public PropertyInsurance updatePropertyInsurance(@PathVariable String id, @RequestBody PropertyInsuranceUpdateDTO propertyInsuranceUpdateDTO) {
        return propertyInsuranceService.updatePropertyInsurance(id, propertyInsuranceUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePropertyInsurance(@PathVariable String id) {
        propertyInsuranceService.deletePropertyInsurance(id);
    }

    @ExceptionHandler(NoSuchInsuranceException.class)
    public ResponseEntity<String> handleNoSuchInsuranceException(NoSuchInsuranceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}