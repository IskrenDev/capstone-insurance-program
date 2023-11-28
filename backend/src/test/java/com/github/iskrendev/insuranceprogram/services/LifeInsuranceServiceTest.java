package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LifeInsuranceServiceTest {
    private final LifeInsuranceRepo mockLifeInsuranceRepo = mock(LifeInsuranceRepo.class);
    private final LifeInsuranceService lifeInsuranceService = new LifeInsuranceService(mockLifeInsuranceRepo);

    @Test
    void getAllLifeInsurances_whenNoLifeInsuranceIsInList_thenReturnEmptyList() {
        when(mockLifeInsuranceRepo.findAll()).thenReturn(List.of());
        List<LifeInsurance> actual = lifeInsuranceService.getAllLifeInsurances();
        verify(mockLifeInsuranceRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllLifeInsurances_whenOneLifeInsuranceIsInList_thenReturnList() {
        //GIVEN
        LifeInsurance lifeInsurance = LifeInsurance.builder()
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

        List<LifeInsurance> expected = List.of(lifeInsurance);
        //WHEN
        when(mockLifeInsuranceRepo.findAll()).thenReturn(expected);
        //THEN
        List<LifeInsurance> actual = lifeInsuranceService.getAllLifeInsurances();
        verify(mockLifeInsuranceRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getLifeInsuranceById_whenIdIsValid_thenReturnInsurance() {
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
        when(mockLifeInsuranceRepo.findById(expected.id())).thenReturn(Optional.of(expected));
        //THEN
        LifeInsurance actual = lifeInsuranceService.getLifeInsuranceById(expected.id());
        verify(mockLifeInsuranceRepo).findById(expected.id());
        assertEquals(expected, actual);
    }

    @Test
    void getLifeInsuranceById_whenIdIsNotValid_thenThrowError() {
        assertThrows(NoSuchInsuranceException.class, () -> lifeInsuranceService.getLifeInsuranceById("1"));
        verify(mockLifeInsuranceRepo).findById("1");
    }

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
        LifeInsurance actual = lifeInsuranceService.addLifeInsurance(expected);
        verify(mockLifeInsuranceRepo).save(expected);
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
        LifeInsurance actual = lifeInsuranceService.addLifeInsurance(expected);
        verify(mockLifeInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }
}