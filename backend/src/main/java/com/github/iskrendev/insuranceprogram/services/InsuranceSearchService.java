package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.exceptions.InvalidSearchCriteriaException;
import com.github.iskrendev.insuranceprogram.models.AllInsurancesResponse;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class InsuranceSearchService {
    private final LifeInsuranceRepo lifeInsuranceRepo;
    private final PropertyInsuranceRepo propertyInsuranceRepo;
    private final VehicleInsuranceRepo vehicleInsuranceRepo;

    public List<LifeInsurance> searchLifeInsuranceByName(String firstName, String familyName) {
        return searchInsurance(firstName, familyName,
                (fn, ln) -> lifeInsuranceRepo.findByFirstNameAndFamilyName(fn, ln),
                (fn, ln) -> lifeInsuranceRepo.findByFirstName(fn),
                (fn, ln) -> lifeInsuranceRepo.findByFamilyName(ln));
    }

    public List<PropertyInsurance> searchPropertyInsuranceByName(String firstName, String familyName) {
        return searchInsurance(firstName, familyName,
                (fn, ln) -> propertyInsuranceRepo.findByFirstNameAndFamilyName(fn, ln),
                (fn, ln) -> propertyInsuranceRepo.findByFirstName(fn),
                (fn, ln) -> propertyInsuranceRepo.findByFamilyName(ln));
    }

    public List<VehicleInsurance> searchVehicleInsuranceByName(String firstName, String familyName) {
        return searchInsurance(firstName, familyName,
                (fn, ln) -> vehicleInsuranceRepo.findByFirstNameAndFamilyName(fn, ln),
                (fn, ln) -> vehicleInsuranceRepo.findByFirstName(fn),
                (fn, ln) -> vehicleInsuranceRepo.findByFamilyName(ln));
    }

    private <T> List<T> searchInsurance(String firstName, String familyName,
                                        BiFunction<String, String, List<T>> searchBothNames,
                                        BiFunction<String, String, List<T>> searchFirstName,
                                        BiFunction<String, String, List<T>> searchFamilyName) {
        if ((firstName == null || firstName.isEmpty()) && (familyName == null || familyName.isEmpty())) {
            throw new InvalidSearchCriteriaException("Both firstName and familyName cannot be null or empty.");
        }

        String firstNameRegex = firstName != null && !firstName.isEmpty() ? "^" + Pattern.quote(firstName) + "$" : null;
        String familyNameRegex = familyName != null && !familyName.isEmpty() ? "^" + Pattern.quote(familyName) + "$" : null;

        if (firstNameRegex != null && familyNameRegex != null) {
            return searchBothNames.apply(firstNameRegex, familyNameRegex);
        } else if (firstNameRegex != null) {
            return searchFirstName.apply(firstNameRegex, null);
        } else {
            return searchFamilyName.apply(null, familyNameRegex);
        }
    }

    public AllInsurancesResponse searchAllInsurancesByName(String firstName, String familyName) {
        List<LifeInsurance> lifeInsurances = searchLifeInsuranceByName(firstName, familyName);
        List<PropertyInsurance> propertyInsurances = searchPropertyInsuranceByName(firstName, familyName);
        List<VehicleInsurance> vehicleInsurances = searchVehicleInsuranceByName(firstName, familyName);
        return new AllInsurancesResponse(lifeInsurances, propertyInsurances, vehicleInsurances);
    }
}