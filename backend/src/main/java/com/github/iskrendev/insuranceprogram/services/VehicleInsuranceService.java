package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsuranceUpdateDTO;
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

    public VehicleInsurance updateVehicleInsurance(String id, VehicleInsuranceUpdateDTO vehicleInsuranceUpdateDTO) {
        getVehicleInsuranceById(id);
        VehicleInsurance vehicleInsuranceToUpdate = VehicleInsurance.builder()
                .id(id)
                .firstName(vehicleInsuranceUpdateDTO.firstName())
                .familyName(vehicleInsuranceUpdateDTO.familyName())
                .zipCode(vehicleInsuranceUpdateDTO.zipCode())
                .city(vehicleInsuranceUpdateDTO.city())
                .telephone(vehicleInsuranceUpdateDTO.telephone())
                .email(vehicleInsuranceUpdateDTO.email())
                .duration(vehicleInsuranceUpdateDTO.duration())
                .paymentPerMonth(vehicleInsuranceUpdateDTO.paymentPerMonth())
                .startDate(vehicleInsuranceUpdateDTO.startDate())
                .endDate(vehicleInsuranceUpdateDTO.endDate())
                .vehicleMake(vehicleInsuranceUpdateDTO.vehicleMake())
                .vehicleModel(vehicleInsuranceUpdateDTO.vehicleModel())
                .vehicleYear(vehicleInsuranceUpdateDTO.vehicleYear())
                .licensePlateNumber(vehicleInsuranceUpdateDTO.licensePlateNumber())
                .build();

        return vehicleInsuranceRepo.save(vehicleInsuranceToUpdate);
    }
}
