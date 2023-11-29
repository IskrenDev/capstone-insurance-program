package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsuranceUpdateDTO;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PropertyInsuranceService {
    private PropertyInsuranceRepo propertyInsuranceRepo;

    public List<PropertyInsurance> getAllPropertyInsurances() {
        return propertyInsuranceRepo.findAll();
    }

    public PropertyInsurance getPropertyInsuranceById(String id) {
        return propertyInsuranceRepo.findById(id).orElseThrow(NoSuchInsuranceException::new);
    }

    public PropertyInsurance addPropertyInsurance(PropertyInsurance propertyInsurance) {
        return propertyInsuranceRepo.save(propertyInsurance);
    }

    public PropertyInsurance updatePropertyInsurance(String id, PropertyInsuranceUpdateDTO propertyInsuranceUpdateDTO) {
        PropertyInsurance propertyInsuranceToUpdate = getPropertyInsuranceById(id);

        PropertyInsurance updatedPropertyInsurance = PropertyInsurance.builder()
                .id(id)
                .firstName(propertyInsuranceUpdateDTO.firstName())
                .familyName(propertyInsuranceUpdateDTO.familyName())
                .zipCode(propertyInsuranceUpdateDTO.zipCode())
                .city(propertyInsuranceUpdateDTO.city())
                .address(propertyInsuranceUpdateDTO.address())
                .telephone(propertyInsuranceUpdateDTO.telephone())
                .email(propertyInsuranceUpdateDTO.email())
                .type(propertyInsuranceToUpdate.type())
                .duration(propertyInsuranceUpdateDTO.duration())
                .paymentPerMonth(propertyInsuranceUpdateDTO.paymentPerMonth())
                .startDate(propertyInsuranceToUpdate.startDate())
                .endDate(propertyInsuranceUpdateDTO.endDate())
                .propertyType(propertyInsuranceUpdateDTO.propertyType())
                .propertyAddress(propertyInsuranceUpdateDTO.propertyAddress())
                .constructionYear(propertyInsuranceUpdateDTO.constructionYear())
                .build();

        return propertyInsuranceRepo.save(updatedPropertyInsurance);
    }
}
