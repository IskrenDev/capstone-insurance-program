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
        LifeInsurance lifeInsuranceToUpdate = getLifeInsuranceById(id);

        LifeInsurance updatedLifeInsurance = LifeInsurance.builder()
                .id(id)
                .firstName(lifeInsuranceUpdateDTO.firstName())
                .familyName(lifeInsuranceUpdateDTO.familyName())
                .zipCode(lifeInsuranceUpdateDTO.zipCode())
                .city(lifeInsuranceUpdateDTO.city())
                .address(lifeInsuranceUpdateDTO.address())
                .telephone(lifeInsuranceUpdateDTO.telephone())
                .email(lifeInsuranceUpdateDTO.email())
                .type(lifeInsuranceToUpdate.type())
                .duration(lifeInsuranceUpdateDTO.duration())
                .paymentPerMonth(lifeInsuranceUpdateDTO.paymentPerMonth())
                .startDate(lifeInsuranceToUpdate.startDate())
                .endDate(lifeInsuranceUpdateDTO.endDate())
                .hasHealthIssues(lifeInsuranceUpdateDTO.hasHealthIssues())
                .healthConditionDetails(lifeInsuranceUpdateDTO.healthConditionDetails())
                .build();

        return lifeInsuranceRepo.save(updatedLifeInsurance);
    }

    public void deleteLifeInsurance(String id) {
        lifeInsuranceRepo.deleteById(id);
    }
}