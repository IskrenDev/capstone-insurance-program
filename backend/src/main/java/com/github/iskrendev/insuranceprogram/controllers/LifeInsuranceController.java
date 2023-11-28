package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.DTOLifeInsurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.services.LifeInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/life")
public class LifeInsuranceController {
    private LifeInsuranceService lifeInsuranceService;
    @GetMapping
    public List<LifeInsurance> getAllLifeInsurances() {
        return lifeInsuranceService.getAllLifeInsurances();
    }

    @GetMapping("/{id}")
    public LifeInsurance getLifeInsuranceById(@PathVariable String id) {
        return lifeInsuranceService.getLifeInsuranceById(id);
    }

    @PostMapping("")
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
        return lifeInsuranceService.addLifeInsurance(newLifeInsurance);
    }

    @ExceptionHandler(NoSuchInsuranceException.class)
    public ResponseEntity<String> handleNoSuchInsuranceException(NoSuchInsuranceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
