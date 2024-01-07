package com.github.iskrendev.insuranceprogram.controllers;

import com.github.iskrendev.insuranceprogram.services.InsuranceSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/summary")
public class InsuranceSummaryController {

    private InsuranceSummaryService insuranceSummaryService;

    @GetMapping("/total-amount")
    public ResponseEntity<BigDecimal> calculateTotalInsuranceAmount() {
        BigDecimal totalAmount = insuranceSummaryService.calculateTotalInsuranceAmount();
        return ResponseEntity.ok(totalAmount);
    }
}