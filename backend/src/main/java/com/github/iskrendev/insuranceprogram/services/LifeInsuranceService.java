package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsuranceUpdateDTO;
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

    public LifeInsurance updateLifeInsurance(String id, LifeInsuranceUpdateDTO lifeInsuranceUpdateDTO) {
        getLifeInsuranceById(id);
        LifeInsurance lifeInsuranceToUpdate = LifeInsurance.builder()
                .firstName(lifeInsuranceUpdateDTO.firstName())
                .familyName(lifeInsuranceUpdateDTO.familyName())
                .zipCode(lifeInsuranceUpdateDTO.zipCode())
                .city(lifeInsuranceUpdateDTO.city())
                .telephone(lifeInsuranceUpdateDTO.telephone())
                .email(lifeInsuranceUpdateDTO.email())
                .duration(lifeInsuranceUpdateDTO.duration())
                .paymentPerMonth(lifeInsuranceUpdateDTO.paymentPerMonth())
                .startDate(lifeInsuranceUpdateDTO.startDate())
                .endDate(lifeInsuranceUpdateDTO.endDate())
                .hasHealthIssues(lifeInsuranceUpdateDTO.hasHealthIssues())
                .healthConditionDetails(lifeInsuranceUpdateDTO.healthConditionDetails())
                .build();

        return lifeInsuranceRepo.save(lifeInsuranceToUpdate);
    }
}