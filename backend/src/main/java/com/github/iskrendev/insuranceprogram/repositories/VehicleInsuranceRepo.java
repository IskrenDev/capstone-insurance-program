package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInsuranceRepo extends MongoRepository <VehicleInsurance, String> {
    List<VehicleInsurance> findByFirstName(String firstName);
    List<VehicleInsurance> findByFamilyName(String familyName);
    List<VehicleInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}
