package com.github.iskrendev.insuranceprogram.repositories;

import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeInsuranceRepo extends MongoRepository <LifeInsurance, String> {

}
