package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class LifeInsuranceService {
    private LifeInsuranceRepo lifeInsuranceRepo;

    public List<LifeInsurance> getAllLifeInsurances() {
        return lifeInsuranceRepo.findAll();
    }

    public LifeInsurance getLifeInsuranceById(String id) {
        return lifeInsuranceRepo.findById(id).orElseThrow(NoSuchInsuranceException::new);
    }

    public LifeInsurance addLifeInsurance(LifeInsurance lifeInsurance) {
        return lifeInsuranceRepo.save(lifeInsurance);
    }
}