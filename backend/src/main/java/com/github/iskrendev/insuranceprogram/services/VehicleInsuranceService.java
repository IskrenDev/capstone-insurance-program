package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class VehicleInsuranceService {
    private VehicleInsuranceRepo vehicleInsuranceRepo;

    public List<VehicleInsurance> getAllVehicleInsurances() {
        return vehicleInsuranceRepo.findAll();
    }

    public VehicleInsurance getVehicleInsuranceById(String id) {
        return vehicleInsuranceRepo.findById(id).orElseThrow(NoSuchInsuranceException::new);
    }

    public VehicleInsurance addVehicleInsurance(VehicleInsurance vehicleInsurance) {
        return vehicleInsuranceRepo.save(vehicleInsurance);
    }
}
