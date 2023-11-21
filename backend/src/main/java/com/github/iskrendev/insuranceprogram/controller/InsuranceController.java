package com.github.iskrendev.insuranceprogram.controller;

import com.github.iskrendev.insuranceprogram.common.Insurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.service.InsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/insurances")
public class InsuranceController {
    private InsuranceService insuranceService;
    @PostMapping("/life")
    public LifeInsurance addLifeInsurance(@RequestBody LifeInsurance lifeInsurance) {
        return insuranceService.addLifeInsurance(lifeInsurance);
    }

    @PostMapping("/property")
    public PropertyInsurance addPropertyInsurance(@RequestBody PropertyInsurance propertyInsurance) {
        return insuranceService.addPropertyInsurance(propertyInsurance);
    }

    @PostMapping("/vehicle")
    public VehicleInsurance addPropertyInsurance(@RequestBody VehicleInsurance vehicleInsurance) {
        return insuranceService.addVehicleInsurance(vehicleInsurance);
    }

}
