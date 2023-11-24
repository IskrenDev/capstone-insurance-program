package com.github.iskrendev.insuranceprogram.service;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repository.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repository.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repository.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    private final LifeInsuranceRepo mockLifeInsuranceRepo = mock(LifeInsuranceRepo.class);
    private final PropertyInsuranceRepo mockPropertyInsuranceRepo = mock(PropertyInsuranceRepo.class);
    private final VehicleInsuranceRepo mockVehicleInsuranceRepo = mock(VehicleInsuranceRepo.class);
    private final InsuranceService insuranceService = new InsuranceService(
                                                                            mockLifeInsuranceRepo,
                                                                            mockPropertyInsuranceRepo,
                                                                            mockVehicleInsuranceRepo
                                                                            );
    @Test
    void addLifeInsurance_whenDataIsComplete_thenReturnCompleteInsurance() {
        //GIVEN
        LifeInsurance expected = LifeInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .telephone("012345")
                .email("testmail@example.com")
                .type(InsuranceType.LIFE)
                .duration(48)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2028, 1, 1))
                .hasHealthIssues(false)
                .healthConditionDetails("")
                .build();
        //WHEN
        when(mockLifeInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        LifeInsurance actual = insuranceService.addLifeInsurance(expected);
        verify(mockLifeInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addPropertyInsurance_whenDataIsComplete_thenReturnCompleteInsurance() {
        //GIVEN
        PropertyInsurance expected = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .telephone("012345")
                .email("testmail@example.com")
                .type(InsuranceType.PROPERTY)
                .duration(48)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2028, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();
        //WHEN
        when(mockPropertyInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        PropertyInsurance actual = insuranceService.addPropertyInsurance(expected);
        verify(mockPropertyInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addVehicleInsurance_whenDataIsComplete_thenReturnCompleteInsurance() {
        //GIVEN
        VehicleInsurance expected = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .telephone("012345")
                .email("testmail@example.com")
                .type(InsuranceType.VEHICLE)
                .duration(12)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .vehicleMake("Testmake")
                .vehicleModel("Testmodel")
                .vehicleYear(2015)
                .licensePlateNumber("AB 123 CD")
                .build();
        //WHEN
        when(mockVehicleInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        VehicleInsurance actual = insuranceService.addVehicleInsurance(expected);
        verify(mockVehicleInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addLifeInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() {
        //GIVEN
        LifeInsurance expected = LifeInsurance.builder()
                .id("1")
                .firstName(null)
                .familyName(null)
                .zipCode(null)
                .city(null)
                .telephone(null)
                .email(null)
                .type(null)
                .duration(null)
                .paymentPerMonth(null)
                .startDate(null)
                .endDate(null)
                .hasHealthIssues(null)
                .healthConditionDetails(null)
                .build();
        //WHEN
        when(mockLifeInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        LifeInsurance actual = insuranceService.addLifeInsurance(expected);
        verify(mockLifeInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addPropertyInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() {
        //GIVEN
        PropertyInsurance expected = PropertyInsurance.builder()
                .id("1")
                .firstName(null)
                .familyName(null)
                .zipCode(null)
                .city(null)
                .telephone(null)
                .email(null)
                .type(null)
                .duration(null)
                .paymentPerMonth(null)
                .startDate(null)
                .endDate(null)
                .propertyType(null)
                .propertyAddress(null)
                .constructionYear(null)
                .build();
        //WHEN
        when(mockPropertyInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        PropertyInsurance actual = insuranceService.addPropertyInsurance(expected);
        verify(mockPropertyInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addVehicleInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() {
        //GIVEN
        VehicleInsurance expected = VehicleInsurance.builder()
                .id("1")
                .firstName(null)
                .familyName(null)
                .zipCode(null)
                .city(null)
                .telephone(null)
                .email(null)
                .type(null)
                .duration(null)
                .paymentPerMonth(null)
                .startDate(null)
                .endDate(null)
                .vehicleMake(null)
                .vehicleModel(null)
                .vehicleYear(null)
                .licensePlateNumber(null)
                .build();
        //WHEN
        when(mockVehicleInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        VehicleInsurance actual = insuranceService.addVehicleInsurance(expected);
        verify(mockVehicleInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }
}