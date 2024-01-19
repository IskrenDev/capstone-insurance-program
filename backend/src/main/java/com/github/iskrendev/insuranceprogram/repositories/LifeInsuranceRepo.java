package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeInsuranceRepo extends MongoRepository <LifeInsurance, String> {
    List<LifeInsurance> findByFirstName(String firstName);
    List<LifeInsurance> findByFamilyName(String familyName);
    List<LifeInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}
