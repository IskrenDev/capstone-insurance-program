package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.exceptions.InvalidSearchCriteriaException;
import com.github.iskrendev.insuranceprogram.services.InsuranceSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/search")
public class InsuranceSearchController {

    private final InsuranceSearchService insuranceSearchService;

    @GetMapping
    public ResponseEntity<?> searchInsurance(@RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String familyName,
                                             @RequestParam(required = false, defaultValue = "all") String type) {

        if ((firstName == null || firstName.isEmpty()) && (familyName == null || familyName.isEmpty())) {
            return ResponseEntity.badRequest().body("At least one of firstName or familyName must be provided");
        }

        switch (type.toLowerCase()) {
            case "life":
                return ResponseEntity.ok(insuranceSearchService.searchLifeInsuranceByName(firstName, familyName));
            case "property":
                return ResponseEntity.ok(insuranceSearchService.searchPropertyInsuranceByName(firstName, familyName));
            case "vehicle":
                return ResponseEntity.ok(insuranceSearchService.searchVehicleInsuranceByName(firstName, familyName));
            case "all":
                return ResponseEntity.ok(insuranceSearchService.searchAllInsurancesByName(firstName, familyName));
            default:
                return ResponseEntity.badRequest().body("Invalid insurance type");
        }
    }

    @ExceptionHandler(InvalidSearchCriteriaException.class)
    public ResponseEntity<String> handleInvalidSearchCriteriaException(InvalidSearchCriteriaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}