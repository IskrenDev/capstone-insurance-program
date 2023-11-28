package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PropertyInsuranceService {
    private PropertyInsuranceRepo propertyInsuranceRepo;

    public List<PropertyInsurance> getAllPropertyInsurances() {
        return propertyInsuranceRepo.findAll();
    }

    public PropertyInsurance getPropertyInsuranceById(String id) {
        return propertyInsuranceRepo.findById(id).orElseThrow(NoSuchInsuranceException::new);
    }

    public PropertyInsurance addPropertyInsurance(PropertyInsurance propertyInsurance) {
        return propertyInsuranceRepo.save(propertyInsurance);
    }
}
