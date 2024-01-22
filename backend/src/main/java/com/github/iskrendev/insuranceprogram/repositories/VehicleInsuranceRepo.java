package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInsuranceRepo extends MongoRepository <VehicleInsurance, String> {
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' } }")
    List<VehicleInsurance> findByFirstName(String firstName);
    @Query("{ 'familyName': { $regex: ?0, $options: 'i' } }")
    List<VehicleInsurance> findByFamilyName(String familyName);
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' }, 'familyName': { $regex: ?1, $options: 'i' } }")
    List<VehicleInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}
