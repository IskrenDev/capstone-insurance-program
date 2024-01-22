package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeInsuranceRepo extends MongoRepository <LifeInsurance, String> {
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' } }")
    List<LifeInsurance> findByFirstName(String firstName);
    @Query("{ 'familyName': { $regex: ?0, $options: 'i' } }")
    List<LifeInsurance> findByFamilyName(String familyName);
    @Query("{ 'firstName': { $regex: ?0, $options: 'i' }, 'familyName': { $regex: ?1, $options: 'i' } }")
    List<LifeInsurance> findByFirstNameAndFamilyName(String firstName, String familyName);
}
