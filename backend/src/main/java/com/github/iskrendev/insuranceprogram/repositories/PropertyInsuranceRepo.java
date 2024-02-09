package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyInsuranceRepo extends MongoRepository <PropertyInsurance, String> {
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' } }")
    List<PropertyInsurance> findByFirstName(String firstName);
    @Query("{ 'familyName': { $regex: ?0, $options: 'i' } }")
    List<PropertyInsurance> findByFamilyName(String familyName);
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' }, 'familyName': { $regex: ?1, $options: 'i' } }")
    List<PropertyInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}

