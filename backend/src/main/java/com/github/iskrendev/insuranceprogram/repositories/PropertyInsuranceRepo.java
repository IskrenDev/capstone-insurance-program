package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyInsuranceRepo extends MongoRepository <PropertyInsurance, String> {
    List<PropertyInsurance> findByFirstName(String firstName);
    List<PropertyInsurance> findByFamilyName(String familyName);
    List<PropertyInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}

