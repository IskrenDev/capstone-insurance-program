package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.*;
import com.github.iskrendev.insuranceprogram.services.VehicleInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleInsuranceController {
    private VehicleInsuranceService vehicleInsuranceService;
    @GetMapping
    public List<VehicleInsurance> getAllVehicleInsurances() {
        return vehicleInsuranceService.getAllVehicleInsurances();
    }

    @GetMapping("/{id}")
    public VehicleInsurance getVehicleInsuranceById(@PathVariable String id) {
        return vehicleInsuranceService.getVehicleInsuranceById(id);
    }

    @PostMapping
    public VehicleInsurance addVehicleInsurance(@RequestBody VehicleInsuranceDTO vehicleInsurance) {
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
        return vehicleInsuranceService.addVehicleInsurance(newVehicleInsurance);
    }

    @PutMapping("/{id}")
    public VehicleInsurance updateVehicleInsurance(@PathVariable String id, @RequestBody VehicleInsuranceUpdateDTO vehicleInsuranceUpdateDTO) {
        return vehicleInsuranceService.updateVehicleInsurance(id, vehicleInsuranceUpdateDTO);
    }

    @ExceptionHandler(NoSuchInsuranceException.class)
    public ResponseEntity<String> handleNoSuchInsuranceException(NoSuchInsuranceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
