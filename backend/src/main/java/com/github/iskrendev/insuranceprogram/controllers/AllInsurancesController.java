package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.exceptions.AllInsurancesRetrievalException;
import com.github.iskrendev.insuranceprogram.models.AllInsurancesResponse;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.services.LifeInsuranceService;
import com.github.iskrendev.insuranceprogram.services.PropertyInsuranceService;
import com.github.iskrendev.insuranceprogram.services.VehicleInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/getall")
public class AllInsurancesController {
    private LifeInsuranceService lifeInsuranceService;
    private PropertyInsuranceService propertyInsuranceService;
    private VehicleInsuranceService vehicleInsuranceService;

    @GetMapping
    public AllInsurancesResponse getAllInsurances() {
        List<LifeInsurance> lifeInsurances = lifeInsuranceService.getAllLifeInsurances();
        List<PropertyInsurance> propertyInsurances = propertyInsuranceService.getAllPropertyInsurances();
        List<VehicleInsurance> vehicleInsurances = vehicleInsuranceService.getAllVehicleInsurances();

        return new AllInsurancesResponse(lifeInsurances, propertyInsurances, vehicleInsurances);
    }

    @ExceptionHandler(AllInsurancesRetrievalException.class)
    public ResponseEntity<String> handleAllInsurancesRetrievalException(AllInsurancesRetrievalException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

