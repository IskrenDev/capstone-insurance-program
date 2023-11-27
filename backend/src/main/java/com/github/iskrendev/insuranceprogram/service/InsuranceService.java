package com.github.iskrendev.insuranceprogram.service;

import com.github.iskrendev.insuranceprogram.common.Insurance;
import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class InsuranceService {
    private LifeInsuranceRepo lifeInsuranceRepo;
    private PropertyInsuranceRepo propertyInsuranceRepo;
    private VehicleInsuranceRepo vehicleInsuranceRepo;

    public List<Insurance> getAllInsurances() {
        List<LifeInsurance> lifeInsurances = lifeInsuranceRepo.findAll();
        List<PropertyInsurance> propertyInsurances = propertyInsuranceRepo.findAll();
        List<VehicleInsurance> vehicleInsurances = vehicleInsuranceRepo.findAll();

        List<Insurance> allInsurances = new ArrayList<>();
        allInsurances.addAll(lifeInsurances);
        allInsurances.addAll(propertyInsurances);
        allInsurances.addAll(vehicleInsurances);

        return allInsurances;
    }

    public Insurance getInsuranceById(String id) {
        Optional<LifeInsurance> resultLife = lifeInsuranceRepo.findById(id);
        Optional<PropertyInsurance> resultProperty = propertyInsuranceRepo.findById(id);
        Optional<VehicleInsurance> resultVehicle = vehicleInsuranceRepo.findById(id);

        if (resultLife.isPresent()) {
            return resultLife.get();
        }
        if (resultProperty.isPresent()) {
            return resultProperty.get();
        }
        if (resultVehicle.isPresent()) {
            return resultVehicle.get();
        }
        throw new NoSuchInsuranceException();
    }

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
