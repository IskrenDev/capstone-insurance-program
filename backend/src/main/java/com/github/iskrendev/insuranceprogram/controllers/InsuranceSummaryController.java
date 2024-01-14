package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.models.InsuranceSummaryDTO;
import com.github.iskrendev.insuranceprogram.services.InsuranceSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/summary")
public class InsuranceSummaryController {

    private InsuranceSummaryService insuranceSummaryService;

    @GetMapping
    public ResponseEntity<InsuranceSummaryDTO> getInsuranceSummary() {
        InsuranceSummaryDTO summary = insuranceSummaryService.getInsuranceSummary();
        return ResponseEntity.ok(summary);
    }
}