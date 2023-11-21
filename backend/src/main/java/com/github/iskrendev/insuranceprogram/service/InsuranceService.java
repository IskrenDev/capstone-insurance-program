package com.github.iskrendev.insuranceprogram.service;

import com.github.iskrendev.insuranceprogram.common.Insurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repository.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repository.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repository.VehicleInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InsuranceService {
    private LifeInsuranceRepo lifeInsuranceRepo;
    private PropertyInsuranceRepo propertyInsuranceRepo;
    private VehicleInsuranceRepo vehicleInsuranceRepo;

    public LifeInsurance addLifeInsurance(LifeInsurance lifeInsurance) {
        return lifeInsuranceRepo.save(lifeInsurance);
    }

    public PropertyInsurance addPropertyInsurance(PropertyInsurance propertyInsurance) {
        return propertyInsuranceRepo.save(propertyInsurance);
    }

    public VehicleInsurance addVehicleInsurance(VehicleInsurance vehicleInsurance) {
        return vehicleInsuranceRepo.save(vehicleInsurance);
    }
}
