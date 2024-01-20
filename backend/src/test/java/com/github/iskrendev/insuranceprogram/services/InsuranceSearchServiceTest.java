package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.exceptions.InvalidSearchCriteriaException;
import com.github.iskrendev.insuranceprogram.models.AllInsurancesResponse;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceSearchServiceTest {
    private final LifeInsuranceRepo mockLifeInsuranceRepo = mock(LifeInsuranceRepo.class);
    private final PropertyInsuranceRepo mockPropertyInsuranceRepo = mock(PropertyInsuranceRepo.class);
    private final VehicleInsuranceRepo mockVehicleInsuranceRepo = mock(VehicleInsuranceRepo.class);

    private final InsuranceSearchService insuranceSearchService = new InsuranceSearchService(mockLifeInsuranceRepo, mockPropertyInsuranceRepo, mockVehicleInsuranceRepo);

    @Test
    void searchLifeInsuranceByName_whenNoFirstOrFamilyNameAsInput_throwsInvalidSearchCriteriaException() {
        assertThrows(InvalidSearchCriteriaException.class, () -> {
            insuranceSearchService.searchLifeInsuranceByName(null, null);
        });

        verify(mockLifeInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockLifeInsuranceRepo, never()).findByFirstName(any());
        verify(mockLifeInsuranceRepo, never()).findByFamilyName(any());
    }

    @Test
    void searchLifeInsuranceByName_whenFirstNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expected = List.of(lifeInsurance);
        when(mockLifeInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expected);
        //WHEN
        List<LifeInsurance> actual = insuranceSearchService.searchLifeInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstName("TestFirstName");
        assertEquals(expected, actual);
    }

    @Test
    void searchLifeInsuranceByName_whenFamilyNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expected = List.of(lifeInsurance);
        when(mockLifeInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expected);
        //WHEN
        List<LifeInsurance> actual = insuranceSearchService.searchLifeInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFamilyName("TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchLifeInsuranceByName_whenFirstAndFamilyNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expected = List.of(lifeInsurance);
        when(mockLifeInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expected);
        //WHEN
        List<LifeInsurance> actual = insuranceSearchService.searchLifeInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchPropertyInsuranceByName_whenNoFirstOrFamilyNameAsInput_throwsInvalidSearchCriteriaException() {
        assertThrows(InvalidSearchCriteriaException.class, () -> {
            insuranceSearchService.searchPropertyInsuranceByName(null, null);
        });

        verify(mockPropertyInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockPropertyInsuranceRepo, never()).findByFirstName(any());
        verify(mockPropertyInsuranceRepo, never()).findByFamilyName(any());
    }

    @Test
    void searchPropertyInsuranceByName_whenFirstNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<PropertyInsurance> expected = List.of(propertyInsurance);
        when(mockPropertyInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expected);
        //WHEN
        List<PropertyInsurance> actual = insuranceSearchService.searchPropertyInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockPropertyInsuranceRepo).findByFirstName("TestFirstName");
        assertEquals(expected, actual);
    }

    @Test
    void searchPropertyInsuranceByName_whenFamilyNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<PropertyInsurance> expected = List.of(propertyInsurance);
        when(mockPropertyInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expected);
        //WHEN
        List<PropertyInsurance> actual = insuranceSearchService.searchPropertyInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockPropertyInsuranceRepo).findByFamilyName("TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchPropertyInsuranceByName_whenFirstAndFamilyNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<PropertyInsurance> expected = List.of(propertyInsurance);
        when(mockPropertyInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expected);
        //WHEN
        List<PropertyInsurance> actual = insuranceSearchService.searchPropertyInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockPropertyInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchVehicleInsuranceByName_whenNoFirstOrFamilyNameAsInput_throwsInvalidSearchCriteriaException() {
        assertThrows(InvalidSearchCriteriaException.class, () -> {
            insuranceSearchService.searchPropertyInsuranceByName(null, null);
        });

        verify(mockVehicleInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockVehicleInsuranceRepo, never()).findByFirstName(any());
        verify(mockVehicleInsuranceRepo, never()).findByFamilyName(any());
    }

    @Test
    void searchVehicleInsuranceByName_whenFirstNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<VehicleInsurance> expected = List.of(vehicleInsurance);
        when(mockVehicleInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expected);
        //WHEN
        List<VehicleInsurance> actual = insuranceSearchService.searchVehicleInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockVehicleInsuranceRepo).findByFirstName("TestFirstName");
        assertEquals(expected, actual);
    }

    @Test
    void searchVehicleInsuranceByName_whenFamilyNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<VehicleInsurance> expected = List.of(vehicleInsurance);
        when(mockVehicleInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expected);
        //WHEN
        List<VehicleInsurance> actual = insuranceSearchService.searchVehicleInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockVehicleInsuranceRepo).findByFamilyName("TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchVehicleInsuranceByName_whenFirstAndFamilyNameAsInput_thenReturnMatchingResults() {
        //GIVEN
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

        List<VehicleInsurance> expected = List.of(vehicleInsurance);
        when(mockVehicleInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expected);
        //WHEN
        List<VehicleInsurance> actual = insuranceSearchService.searchVehicleInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockVehicleInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        assertEquals(expected, actual);
    }

    @Test
    void searchInsuranceByName_whenNameDoesNotExist_thenReturnEmptyList() {
        //GIVEN
        when(mockLifeInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        when(mockPropertyInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        when(mockVehicleInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        //WHEN
        List<LifeInsurance> actualLifeResult = insuranceSearchService.searchLifeInsuranceByName("NonExistentName", null);
        List<PropertyInsurance> actualPropertyResult = insuranceSearchService.searchPropertyInsuranceByName("NonExistentName", null);
        List<VehicleInsurance> actualVehicleResult = insuranceSearchService.searchVehicleInsuranceByName("NonExistentName", null);
        //THEN
        assertTrue(actualLifeResult.isEmpty());
        assertTrue(actualPropertyResult.isEmpty());
        assertTrue(actualVehicleResult.isEmpty());
    }

    @Test
    void searchAllInsurancesByName_whenNoFirstOrFamilyNameAsInput_throwsInvalidSearchCriteriaException() {
        assertThrows(InvalidSearchCriteriaException.class, () -> {
            insuranceSearchService.searchAllInsurancesByName(null, null);
        });

        verify(mockLifeInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockLifeInsuranceRepo, never()).findByFirstName(any());
        verify(mockLifeInsuranceRepo, never()).findByFamilyName(any());
        verify(mockPropertyInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockPropertyInsuranceRepo, never()).findByFirstName(any());
        verify(mockPropertyInsuranceRepo, never()).findByFamilyName(any());
        verify(mockVehicleInsuranceRepo, never()).findByFirstNameAndFamilyName(any(), any());
        verify(mockVehicleInsuranceRepo, never()).findByFirstName(any());
        verify(mockVehicleInsuranceRepo, never()).findByFamilyName(any());
    }

    @Test
    void searchAllInsurancesByName_whenNameDoesNotExist_thenReturnEmptyResponseObject() {
        //GIVEN
        when(mockLifeInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        when(mockPropertyInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        when(mockVehicleInsuranceRepo.findByFirstName("NonExistentName")).thenReturn(Collections.emptyList());
        //WHEN
        AllInsurancesResponse actual = insuranceSearchService.searchAllInsurancesByName("NonExistentName", null);
        //THEN
        assertTrue(actual.lifeInsurances().isEmpty());
        assertTrue(actual.propertyInsurances().isEmpty());
        assertTrue(actual.vehicleInsurances().isEmpty());
    }

    @Test
    void searchAllInsurancesByName_whenFirstNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);
        List<PropertyInsurance> expectedPropertyInsurances = List.of(propertyInsurance);
        List<VehicleInsurance> expectedVehicleInsurances = List.of(vehicleInsurance);

        when(mockLifeInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expectedLifeInsurances);
        when(mockPropertyInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expectedPropertyInsurances);
        when(mockVehicleInsuranceRepo.findByFirstName("TestFirstName")).thenReturn(expectedVehicleInsurances);
        //WHEN
        AllInsurancesResponse actual = insuranceSearchService.searchAllInsurancesByName("TestFirstName", null);
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstName("TestFirstName");
        verify(mockPropertyInsuranceRepo).findByFirstName("TestFirstName");
        verify(mockVehicleInsuranceRepo).findByFirstName("TestFirstName");
        assertEquals(expectedLifeInsurances, actual.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actual.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actual.vehicleInsurances());
    }

    @Test
    void searchAllInsurancesByName_whenFamilyNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);
        List<PropertyInsurance> expectedPropertyInsurances = List.of(propertyInsurance);
        List<VehicleInsurance> expectedVehicleInsurances = List.of(vehicleInsurance);

        when(mockLifeInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expectedLifeInsurances);
        when(mockPropertyInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expectedPropertyInsurances);
        when(mockVehicleInsuranceRepo.findByFamilyName("TestFamilyName")).thenReturn(expectedVehicleInsurances);
        //WHEN
        AllInsurancesResponse actual = insuranceSearchService.searchAllInsurancesByName(null, "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFamilyName("TestFamilyName");
        verify(mockPropertyInsuranceRepo).findByFamilyName("TestFamilyName");
        verify(mockVehicleInsuranceRepo).findByFamilyName("TestFamilyName");
        assertEquals(expectedLifeInsurances, actual.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actual.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actual.vehicleInsurances());
    }

    @Test
    void searchAllInsurancesByName_whenFirstAndFamilyNameAsInput_thenReturnMatchingResults() {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);
        List<PropertyInsurance> expectedPropertyInsurances = List.of(propertyInsurance);
        List<VehicleInsurance> expectedVehicleInsurances = List.of(vehicleInsurance);

        when(mockLifeInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expectedLifeInsurances);
        when(mockPropertyInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expectedPropertyInsurances);
        when(mockVehicleInsuranceRepo.findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName")).thenReturn(expectedVehicleInsurances);
        //WHEN
        AllInsurancesResponse actual = insuranceSearchService.searchAllInsurancesByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        verify(mockPropertyInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        verify(mockVehicleInsuranceRepo).findByFirstNameAndFamilyName("TestFirstName", "TestFamilyName");
        assertEquals(expectedLifeInsurances, actual.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actual.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actual.vehicleInsurances());
    }
}