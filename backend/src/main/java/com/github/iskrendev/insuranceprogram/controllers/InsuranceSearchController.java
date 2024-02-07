package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.models.AllInsurancesResponse;
import com.github.iskrendev.insuranceprogram.exceptions.InvalidSearchCriteriaException;
import com.github.iskrendev.insuranceprogram.services.InsuranceSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/search")
public class InsuranceSearchController {

    private final InsuranceSearchService insuranceSearchService;

    @GetMapping
    public ResponseEntity<AllInsurancesResponse> searchInsurance(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false, defaultValue = "all") String type) {

        if ((firstName == null || firstName.isEmpty()) && (familyName == null || familyName.isEmpty())) {
            return ResponseEntity.badRequest().build();
        }

        AllInsurancesResponse response;
        switch (type.toLowerCase()) {
            case "life":
                response = new AllInsurancesResponse(insuranceSearchService.searchLifeInsuranceByName(firstName, familyName), List.of(), List.of());
                break;
            case "property":
                response = new AllInsurancesResponse(List.of(), insuranceSearchService.searchPropertyInsuranceByName(firstName, familyName), List.of());
                break;
            case "vehicle":
                response = new AllInsurancesResponse(List.of(), List.of(), insuranceSearchService.searchVehicleInsuranceByName(firstName, familyName));
                break;
            case "all":
                response = insuranceSearchService.searchAllInsurancesByName(firstName, familyName);
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidSearchCriteriaException.class)
    public ResponseEntity<String> handleInvalidSearchCriteriaException(InvalidSearchCriteriaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}