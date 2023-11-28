package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleInsuranceServiceTest {
    private final VehicleInsuranceRepo mockVehicleInsuranceRepo = mock(VehicleInsuranceRepo.class);
    private final VehicleInsuranceService vehicleInsuranceService = new VehicleInsuranceService(mockVehicleInsuranceRepo);
    @Test
    void getAllVehicleInsurances_whenNoVehicleInsuranceIsInList_thenReturnEmptyList() {
        when(mockVehicleInsuranceRepo.findAll()).thenReturn(List.of());
        List<VehicleInsurance> actual = vehicleInsuranceService.getAllVehicleInsurances();
        verify(mockVehicleInsuranceRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllVehicleInsurances_whenOneVehicleInsuranceIsInList_thenReturnList() {
        //GIVEN
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
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

        List<VehicleInsurance> expected = List.of(vehicleInsurance);
        //WHEN
        when(mockVehicleInsuranceRepo.findAll()).thenReturn(expected);
        //THEN
        List<VehicleInsurance> actual = vehicleInsuranceService.getAllVehicleInsurances();
        verify(mockVehicleInsuranceRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getVehicleInsuranceById_whenIdIsValid_thenReturnInsurance() {
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
        when(mockVehicleInsuranceRepo.findById(expected.id())).thenReturn(Optional.of(expected));
        //THEN
        VehicleInsurance actual = vehicleInsuranceService.getVehicleInsuranceById(expected.id());
        verify(mockVehicleInsuranceRepo).findById(expected.id());
        assertEquals(expected, actual);
    }

    @Test
    void getVehicleInsuranceById_whenIdIsNotValid_thenThrowError() {
        assertThrows(NoSuchInsuranceException.class, () -> vehicleInsuranceService.getVehicleInsuranceById("1"));
        verify(mockVehicleInsuranceRepo).findById("1");
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
        VehicleInsurance actual = vehicleInsuranceService.addVehicleInsurance(expected);
        verify(mockVehicleInsuranceRepo).save(expected);
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
        VehicleInsurance actual = vehicleInsuranceService.addVehicleInsurance(expected);
        verify(mockVehicleInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }
}