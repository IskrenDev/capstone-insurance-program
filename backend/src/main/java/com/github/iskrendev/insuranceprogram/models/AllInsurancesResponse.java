package com.github.iskrendev.insuranceprogram.models;

import java.util.List;

public record AllInsurancesResponse(
        List<LifeInsurance> lifeInsurances,
        List<PropertyInsurance> propertyInsurances,
        List<VehicleInsurance> vehicleInsurances
) {

}
