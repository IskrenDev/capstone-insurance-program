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
import org.mockito.ArgumentMatchers;

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
        when(mockLifeInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<LifeInsurance> actualUpperCase = insuranceSearchService.searchLifeInsuranceByName("TESTFIRSTNAME", null);
        List<LifeInsurance> actualLowerCase = insuranceSearchService.searchLifeInsuranceByName("testfirstname", null);
        List<LifeInsurance> actualMixedCase = insuranceSearchService.searchLifeInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstName("^\\QTESTFIRSTNAME\\E$");
        verify(mockLifeInsuranceRepo).findByFirstName("^\\Qtestfirstname\\E$");
        verify(mockLifeInsuranceRepo).findByFirstName("^\\QTestFirstName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockLifeInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<LifeInsurance> actualUpperCase = insuranceSearchService.searchLifeInsuranceByName(null, "TESTFAMILYNAME");
        List<LifeInsurance> actualLowerCase = insuranceSearchService.searchLifeInsuranceByName(null, "testfamilyname");
        List<LifeInsurance> actualMixedCase = insuranceSearchService.searchLifeInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFamilyName("^\\QTESTFAMILYNAME\\E$");
        verify(mockLifeInsuranceRepo).findByFamilyName("^\\Qtestfamilyname\\E$");
        verify(mockLifeInsuranceRepo).findByFamilyName("^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockLifeInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expected;
                    } else {
                        return Collections.emptyList();
                    }
                });
        //WHEN
        List<LifeInsurance> actualUpperCase = insuranceSearchService.searchLifeInsuranceByName("TESTFIRSTNAME", "TESTFAMILYNAME");
        List<LifeInsurance> actualLowerCase = insuranceSearchService.searchLifeInsuranceByName("testfirstname", "testfamilyname");
        List<LifeInsurance> actualMixedCase = insuranceSearchService.searchLifeInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo).findByFirstNameAndFamilyName("^\\QTESTFIRSTNAME\\E$", "^\\QTESTFAMILYNAME\\E$");
        verify(mockLifeInsuranceRepo).findByFirstNameAndFamilyName("^\\Qtestfirstname\\E$", "^\\Qtestfamilyname\\E$");
        verify(mockLifeInsuranceRepo).findByFirstNameAndFamilyName("^\\QTestFirstName\\E$", "^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockPropertyInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<PropertyInsurance> actualUpperCase = insuranceSearchService.searchPropertyInsuranceByName("TESTFIRSTNAME", null);
        List<PropertyInsurance> actualLowerCase = insuranceSearchService.searchPropertyInsuranceByName("testfirstname", null);
        List<PropertyInsurance> actualMixedCase = insuranceSearchService.searchPropertyInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockPropertyInsuranceRepo).findByFirstName("^\\QTESTFIRSTNAME\\E$");
        verify(mockPropertyInsuranceRepo).findByFirstName("^\\Qtestfirstname\\E$");
        verify(mockPropertyInsuranceRepo).findByFirstName("^\\QTestFirstName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockPropertyInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<PropertyInsurance> actualUpperCase = insuranceSearchService.searchPropertyInsuranceByName(null, "TESTFAMILYNAME");
        List<PropertyInsurance> actualLowerCase = insuranceSearchService.searchPropertyInsuranceByName(null, "testfamilyname");
        List<PropertyInsurance> actualMixedCase = insuranceSearchService.searchPropertyInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockPropertyInsuranceRepo).findByFamilyName("^\\QTESTFAMILYNAME\\E$");
        verify(mockPropertyInsuranceRepo).findByFamilyName("^\\Qtestfamilyname\\E$");
        verify(mockPropertyInsuranceRepo).findByFamilyName("^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockPropertyInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expected;
                    } else {
                        return Collections.emptyList();
                    }
                });
        //WHEN
        List<PropertyInsurance> actualUpperCase = insuranceSearchService.searchPropertyInsuranceByName("TESTFIRSTNAME", "TESTFAMILYNAME");
        List<PropertyInsurance> actualLowerCase = insuranceSearchService.searchPropertyInsuranceByName("testfirstname", "testfamilyname");
        List<PropertyInsurance> actualMixedCase = insuranceSearchService.searchPropertyInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockPropertyInsuranceRepo).findByFirstNameAndFamilyName("^\\QTESTFIRSTNAME\\E$", "^\\QTESTFAMILYNAME\\E$");
        verify(mockPropertyInsuranceRepo).findByFirstNameAndFamilyName("^\\Qtestfirstname\\E$", "^\\Qtestfamilyname\\E$");
        verify(mockPropertyInsuranceRepo).findByFirstNameAndFamilyName("^\\QTestFirstName\\E$", "^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockVehicleInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<VehicleInsurance> actualUpperCase = insuranceSearchService.searchVehicleInsuranceByName("TESTFIRSTNAME", null);
        List<VehicleInsurance> actualLowerCase = insuranceSearchService.searchVehicleInsuranceByName("testfirstname", null);
        List<VehicleInsurance> actualMixedCase = insuranceSearchService.searchVehicleInsuranceByName("TestFirstName", null);
        //THEN
        verify(mockVehicleInsuranceRepo).findByFirstName("^\\QTESTFIRSTNAME\\E$");
        verify(mockVehicleInsuranceRepo).findByFirstName("^\\Qtestfirstname\\E$");
        verify(mockVehicleInsuranceRepo).findByFirstName("^\\QTestFirstName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockVehicleInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expected;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        List<VehicleInsurance> actualUpperCase = insuranceSearchService.searchVehicleInsuranceByName(null, "TESTFAMILYNAME");
        List<VehicleInsurance> actualLowerCase = insuranceSearchService.searchVehicleInsuranceByName(null, "testfamilyname");
        List<VehicleInsurance> actualMixedCase = insuranceSearchService.searchVehicleInsuranceByName(null, "TestFamilyName");
        //THEN
        verify(mockVehicleInsuranceRepo).findByFamilyName("^\\QTESTFAMILYNAME\\E$");
        verify(mockVehicleInsuranceRepo).findByFamilyName("^\\Qtestfamilyname\\E$");
        verify(mockVehicleInsuranceRepo).findByFamilyName("^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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
        when(mockVehicleInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expected;
                    } else {
                        return Collections.emptyList();
                    }
                });
        //WHEN
        List<VehicleInsurance> actualUpperCase = insuranceSearchService.searchVehicleInsuranceByName("TESTFIRSTNAME", "TESTFAMILYNAME");
        List<VehicleInsurance> actualLowerCase = insuranceSearchService.searchVehicleInsuranceByName("testfirstname", "testfamilyname");
        List<VehicleInsurance> actualMixedCase = insuranceSearchService.searchVehicleInsuranceByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockVehicleInsuranceRepo).findByFirstNameAndFamilyName("^\\QTESTFIRSTNAME\\E$", "^\\QTESTFAMILYNAME\\E$");
        verify(mockVehicleInsuranceRepo).findByFirstNameAndFamilyName("^\\Qtestfirstname\\E$", "^\\Qtestfamilyname\\E$");
        verify(mockVehicleInsuranceRepo).findByFirstNameAndFamilyName("^\\QTestFirstName\\E$", "^\\QTestFamilyName\\E$");
        assertEquals(expected, actualUpperCase);
        assertEquals(expected, actualLowerCase);
        assertEquals(expected, actualMixedCase);
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

        when(mockLifeInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expectedLifeInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        when(mockPropertyInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expectedPropertyInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        when(mockVehicleInsuranceRepo.findByFirstName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String firstNameRegex = invocation.getArgument(0);
            if (firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") || firstNameRegex.equals("^\\Qtestfirstname\\E$") || firstNameRegex.equals("^\\QTestFirstName\\E$")) {
                return expectedVehicleInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        AllInsurancesResponse actualUpperCase = insuranceSearchService.searchAllInsurancesByName("TESTFIRSTNAME", null);
        AllInsurancesResponse actualLowerCase = insuranceSearchService.searchAllInsurancesByName("testfirstname", null);
        AllInsurancesResponse actualMixedCase = insuranceSearchService.searchAllInsurancesByName("TestFirstName", null);
        //THEN
        verify(mockLifeInsuranceRepo, times(3)).findByFirstName(anyString());
        verify(mockPropertyInsuranceRepo, times(3)).findByFirstName(anyString());
        verify(mockVehicleInsuranceRepo, times(3)).findByFirstName(anyString());

        assertEquals(expectedLifeInsurances, actualUpperCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualUpperCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualUpperCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualLowerCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualLowerCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualLowerCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualMixedCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualMixedCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualMixedCase.vehicleInsurances());
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

        when(mockLifeInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expectedLifeInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        when(mockPropertyInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expectedPropertyInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        when(mockVehicleInsuranceRepo.findByFamilyName(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String familyNameRegex = invocation.getArgument(0);
            if (familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$") || familyNameRegex.equals("^\\Qtestfamilyname\\E$") || familyNameRegex.equals("^\\QTestFamilyName\\E$")) {
                return expectedVehicleInsurances;
            } else {
                return Collections.emptyList();
            }
        });
        //WHEN
        AllInsurancesResponse actualUpperCase = insuranceSearchService.searchAllInsurancesByName(null, "TESTFAMILYNAME");
        AllInsurancesResponse actualLowerCase = insuranceSearchService.searchAllInsurancesByName(null, "testfamilyname");
        AllInsurancesResponse actualMixedCase = insuranceSearchService.searchAllInsurancesByName(null, "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo, times(3)).findByFamilyName(anyString());
        verify(mockPropertyInsuranceRepo, times(3)).findByFamilyName(anyString());
        verify(mockVehicleInsuranceRepo, times(3)).findByFamilyName(anyString());

        assertEquals(expectedLifeInsurances, actualUpperCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualUpperCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualUpperCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualLowerCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualLowerCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualLowerCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualMixedCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualMixedCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualMixedCase.vehicleInsurances());
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

        when(mockLifeInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expectedLifeInsurances;
                    } else {
                        return Collections.emptyList();
                    }
                });
        when(mockPropertyInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expectedPropertyInsurances;
                    } else {
                        return Collections.emptyList();
                    }
                });
        when(mockVehicleInsuranceRepo.findByFirstNameAndFamilyName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenAnswer(invocation -> {
                    String firstNameRegex = invocation.getArgument(0);
                    String familyNameRegex = invocation.getArgument(1);
                    if ((firstNameRegex.equals("^\\QTESTFIRSTNAME\\E$") && familyNameRegex.equals("^\\QTESTFAMILYNAME\\E$")) ||
                            (firstNameRegex.equals("^\\Qtestfirstname\\E$") && familyNameRegex.equals("^\\Qtestfamilyname\\E$")) ||
                            (firstNameRegex.equals("^\\QTestFirstName\\E$") && familyNameRegex.equals("^\\QTestFamilyName\\E$"))) {
                        return expectedVehicleInsurances;
                    } else {
                        return Collections.emptyList();
                    }
                });
        //WHEN
        AllInsurancesResponse actualUpperCase = insuranceSearchService.searchAllInsurancesByName("TESTFIRSTNAME", "TESTFAMILYNAME");
        AllInsurancesResponse actualLowerCase = insuranceSearchService.searchAllInsurancesByName("testfirstname", "testfamilyname");
        AllInsurancesResponse actualMixedCase = insuranceSearchService.searchAllInsurancesByName("TestFirstName", "TestFamilyName");
        //THEN
        verify(mockLifeInsuranceRepo, times(3)).findByFirstNameAndFamilyName(anyString(), anyString());
        verify(mockPropertyInsuranceRepo, times(3)).findByFirstNameAndFamilyName(anyString(), anyString());
        verify(mockVehicleInsuranceRepo, times(3)).findByFirstNameAndFamilyName(anyString(), anyString());

        assertEquals(expectedLifeInsurances, actualUpperCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualUpperCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualUpperCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualLowerCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualLowerCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualLowerCase.vehicleInsurances());

        assertEquals(expectedLifeInsurances, actualMixedCase.lifeInsurances());
        assertEquals(expectedPropertyInsurances, actualMixedCase.propertyInsurances());
        assertEquals(expectedVehicleInsurances, actualMixedCase.vehicleInsurances());
    }
}