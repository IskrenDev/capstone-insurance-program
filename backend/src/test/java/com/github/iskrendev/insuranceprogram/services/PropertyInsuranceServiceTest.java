package com.github.iskrendev.insuranceprogram.services;

import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.exceptions.NoSuchInsuranceException;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsuranceUpdateDTO;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyInsuranceServiceTest {
    private final PropertyInsuranceRepo mockPropertyInsuranceRepo = mock(PropertyInsuranceRepo.class);
    private final PropertyInsuranceService propertyInsuranceService = new PropertyInsuranceService(mockPropertyInsuranceRepo);

    @Test
    void getAllPropertyInsurances_whenNoPropertyInsuranceIsInList_thenReturnEmptyList() {
        when(mockPropertyInsuranceRepo.findAll()).thenReturn(List.of());
        List<PropertyInsurance> actual = propertyInsuranceService.getAllPropertyInsurances();
        verify(mockPropertyInsuranceRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllPropertyInsurances_whenOnePropertyInsuranceIsInList_thenReturnList() {
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
        //WHEN
        when(mockPropertyInsuranceRepo.findAll()).thenReturn(expected);
        //THEN
        List<PropertyInsurance> actual = propertyInsuranceService.getAllPropertyInsurances();
        verify(mockPropertyInsuranceRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getPropertyInsuranceById_whenIdIsValid_thenReturnInsurance() {
        //GIVEN
        PropertyInsurance expected = PropertyInsurance.builder()
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
        //WHEN
        when(mockPropertyInsuranceRepo.findById(expected.id())).thenReturn(Optional.of(expected));
        //THEN
        PropertyInsurance actual = propertyInsuranceService.getPropertyInsuranceById(expected.id());
        verify(mockPropertyInsuranceRepo).findById(expected.id());
        assertEquals(expected, actual);
    }

    @Test
    void getPropertyInsuranceById_whenIdIsNotValid_thenThrowError() {
        assertThrows(NoSuchInsuranceException.class, () -> propertyInsuranceService.getPropertyInsuranceById("1"));
        verify(mockPropertyInsuranceRepo).findById("1");
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
        //WHEN
        when(mockPropertyInsuranceRepo.save(expected)).thenReturn(expected);
        //THEN
        PropertyInsurance actual = propertyInsuranceService.addPropertyInsurance(expected);
        verify(mockPropertyInsuranceRepo).save(expected);
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
                .address(null)
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
        PropertyInsurance actual = propertyInsuranceService.addPropertyInsurance(expected);
        verify(mockPropertyInsuranceRepo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void updatePropertyInsurance_whenInsuranceIdExistsInDb_thenReturnUpdatedInsurance() {
        // GIVEN
        PropertyInsurance propertyInsuranceBefore = PropertyInsurance.builder()
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

        PropertyInsurance updatedPropertyInsurance = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .type(InsuranceType.PROPERTY)
                .duration(72)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2030, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();

        PropertyInsuranceUpdateDTO propertyInsuranceUpdateDTO = PropertyInsuranceUpdateDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .duration(72)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .endDate(LocalDate.of(2030, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();

        when(mockPropertyInsuranceRepo.findById("1")).thenReturn(Optional.of(propertyInsuranceBefore));
        when(mockPropertyInsuranceRepo.save(any(PropertyInsurance.class))).thenReturn(updatedPropertyInsurance);

        // WHEN
        PropertyInsurance actual = propertyInsuranceService.updatePropertyInsurance(updatedPropertyInsurance.id(), propertyInsuranceUpdateDTO);

        // THEN
        verify(mockPropertyInsuranceRepo).findById("1");
        verify(mockPropertyInsuranceRepo).save(updatedPropertyInsurance);
        assertEquals(updatedPropertyInsurance, actual);
    }

    @Test
    void updatePropertyInsurance_whenInsuranceIdDoesNotExistsInDb_thenThrowException() {
        PropertyInsuranceUpdateDTO propertyInsuranceUpdateDTO = PropertyInsuranceUpdateDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 123")
                .telephone("044444")
                .email("testmail@example.com")
                .duration(72)
                .paymentPerMonth(BigDecimal.valueOf(90))
                .endDate(LocalDate.of(2030, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();

        when(mockPropertyInsuranceRepo.findById("invalidId")).thenReturn(Optional.empty());
        assertThrows(NoSuchInsuranceException.class, () -> propertyInsuranceService.updatePropertyInsurance("invalidId", propertyInsuranceUpdateDTO));
        verify(mockPropertyInsuranceRepo).findById("invalidId");
        verify(mockPropertyInsuranceRepo, never()).save(any(PropertyInsurance.class));
    }

    @Test
    void deletePropertyInsurance() {
        String propertyInsuranceId = "1";
        propertyInsuranceService.deletePropertyInsurance(propertyInsuranceId);
        verify(mockPropertyInsuranceRepo).deleteById(propertyInsuranceId);
    }
}