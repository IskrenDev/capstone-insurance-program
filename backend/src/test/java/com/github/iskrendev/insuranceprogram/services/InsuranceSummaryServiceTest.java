package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.InsuranceSummaryDTO;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceSummaryServiceTest {
    private final LifeInsuranceRepo mockLifeInsuranceRepo = mock(LifeInsuranceRepo.class);
    private final PropertyInsuranceRepo mockPropertyInsuranceRepo = mock(PropertyInsuranceRepo.class);
    private final VehicleInsuranceRepo mockVehicleInsuranceRepo = mock(VehicleInsuranceRepo.class);
    private final InsuranceSummaryService insuranceSummaryService = new InsuranceSummaryService(mockLifeInsuranceRepo, mockPropertyInsuranceRepo, mockVehicleInsuranceRepo);

    @Test
    void countLifeInsurances_whenNoLifeInsurancesInRepo_thenReturn0() {
        when(mockLifeInsuranceRepo.count()).thenReturn(0L);
        long actual = insuranceSummaryService.countLifeInsurances();
        assertEquals(0L, actual);
    }

    @Test
    void countLifeInsurances_whenOneLifeInsuranceInRepo_thenReturn1() {
        when(mockLifeInsuranceRepo.count()).thenReturn(1L);
        long actual = insuranceSummaryService.countLifeInsurances();
        assertEquals(1L, actual);
    }

    @Test
    void countPropertyInsurances_whenNoPropertyInsurancesInRepo_thenReturn0() {
        when(mockPropertyInsuranceRepo.count()).thenReturn(0L);
        long actual = insuranceSummaryService.countPropertyInsurances();
        assertEquals(0L, actual);
    }

    @Test
    void countPropertyInsurances_whenOnePropertyInsuranceInRepo_thenReturn1() {
        when(mockPropertyInsuranceRepo.count()).thenReturn(1L);
        long actual = insuranceSummaryService.countPropertyInsurances();
        assertEquals(1L, actual);
    }

    @Test
    void countVehicleInsurances_whenNoVehicleInsurancesInRepo_thenReturn0() {
        when(mockVehicleInsuranceRepo.count()).thenReturn(0L);
        long actual = insuranceSummaryService.countVehicleInsurances();
        assertEquals(0L, actual);
    }

    @Test
    void countVehicleInsurances_whenOneVehicleInsuranceInRepo_thenReturn1() {
        when(mockVehicleInsuranceRepo.count()).thenReturn(1L);
        long actual = insuranceSummaryService.countVehicleInsurances();
        assertEquals(1L, actual);
    }

    @Test
    void calculateTotalInsuranceAmount_whenNoInsurancesInLists_thenReturn0() {
        when(mockLifeInsuranceRepo.findAll()).thenReturn(List.of());
        when(mockPropertyInsuranceRepo.findAll()).thenReturn(List.of());
        when(mockVehicleInsuranceRepo.findAll()).thenReturn(List.of());
        BigDecimal totalAmount = insuranceSummaryService.calculateTotalInsuranceAmount();
        assertEquals(BigDecimal.ZERO, totalAmount);
    }

    @Test
    void calculateTotalInsuranceAmount_whenOneInsuranceInEachList_thenReturnTotalAmount() {
        //GIVEN
        LifeInsurance lifeInsurance = LifeInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        PropertyInsurance propertyInsurance = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        BigDecimal expected = lifeInsurance.calculateInsuranceAmount()
                .add(propertyInsurance.calculateInsuranceAmount())
                .add(vehicleInsurance.calculateInsuranceAmount());
        //WHEN
        when(mockLifeInsuranceRepo.findAll()).thenReturn(List.of(lifeInsurance));
        when(mockPropertyInsuranceRepo.findAll()).thenReturn(List.of(propertyInsurance));
        when(mockVehicleInsuranceRepo.findAll()).thenReturn(List.of(vehicleInsurance));
        BigDecimal actual = insuranceSummaryService.calculateTotalInsuranceAmount();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getInsuranceSummary_whenNoInsurancesInRepos_thenReturn0() {
        //GIVEN
        when(mockLifeInsuranceRepo.count()).thenReturn(0L);
        when(mockPropertyInsuranceRepo.count()).thenReturn(0L);
        when(mockVehicleInsuranceRepo.count()).thenReturn(0L);
        //WHEN
        InsuranceSummaryDTO actual = insuranceSummaryService.getInsuranceSummary();
        //THEN
        assertNotNull(actual);
        assertEquals(BigDecimal.ZERO, actual.totalAmount());
        assertEquals(0L, actual.lifeInsuranceCount());
        assertEquals(0L, actual.propertyInsuranceCount());
        assertEquals(0L, actual.vehicleInsuranceCount());
    }

    @Test
    void getInsuranceSummary_whenOneInsuranceInEachRepo_thenReturnCorrectCountsAndTotalAmount() {
        //GIVEN
        LifeInsurance lifeInsurance = LifeInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        PropertyInsurance propertyInsurance = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        when(mockLifeInsuranceRepo.findAll()).thenReturn(List.of(lifeInsurance));
        when(mockPropertyInsuranceRepo.findAll()).thenReturn(List.of(propertyInsurance));
        when(mockVehicleInsuranceRepo.findAll()).thenReturn(List.of(vehicleInsurance));

        when(mockLifeInsuranceRepo.count()).thenReturn(1L);
        when(mockPropertyInsuranceRepo.count()).thenReturn(1L);
        when(mockVehicleInsuranceRepo.count()).thenReturn(1L);

        BigDecimal expectedTotalAmount = lifeInsurance.calculateInsuranceAmount()
                .add(propertyInsurance.calculateInsuranceAmount())
                .add(vehicleInsurance.calculateInsuranceAmount());
        //WHEN
        InsuranceSummaryDTO actual = insuranceSummaryService.getInsuranceSummary();
        //THEN
        assertEquals(expectedTotalAmount, actual.totalAmount());
        assertEquals(1L, actual.lifeInsuranceCount());
        assertEquals(1L, actual.propertyInsuranceCount());
        assertEquals(1L, actual.vehicleInsuranceCount());
    }
}