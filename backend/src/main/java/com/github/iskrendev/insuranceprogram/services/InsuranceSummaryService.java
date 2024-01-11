package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.models.InsuranceSummaryDTO;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class InsuranceSummaryService {
    private LifeInsuranceRepo lifeInsuranceRepo;
    private PropertyInsuranceRepo propertyInsuranceRepo;
    private VehicleInsuranceRepo vehicleInsuranceRepo;

    public long countLifeInsurances() {
        return lifeInsuranceRepo.count();
    }

    public long countPropertyInsurances() {
        return propertyInsuranceRepo.count();
    }

    public long countVehicleInsurances() {
        return vehicleInsuranceRepo.count();
    }

    public BigDecimal calculateTotalInsuranceAmount() {
        BigDecimal totalLifeInsurance = lifeInsuranceRepo.findAll().stream()
                .map(LifeInsurance::calculateInsuranceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPropertyInsurance = propertyInsuranceRepo.findAll().stream()
                .map(PropertyInsurance::calculateInsuranceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalVehicleInsurance = vehicleInsuranceRepo.findAll().stream()
                .map(VehicleInsurance::calculateInsuranceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalLifeInsurance.add(totalPropertyInsurance).add(totalVehicleInsurance);
    }

    public InsuranceSummaryDTO getInsuranceSummary() {
        BigDecimal totalAmount = calculateTotalInsuranceAmount();
        long lifeCount = countLifeInsurances();
        long propertyCount = countPropertyInsurances();
        long vehicleCount = countVehicleInsurances();

        return new InsuranceSummaryDTO(totalAmount, lifeCount, propertyCount, vehicleCount);
    }
}