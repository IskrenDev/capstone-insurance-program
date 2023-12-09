package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.*;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PropertyInsuranceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PropertyInsuranceRepo propertyInsuranceRepo;
    private static final String BASE_URI = "/api/property";

    @Test
    @DirtiesContext
    void getAllPropertyInsurances_whenNoPropertyInsuranceIsInList_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllPropertyInsurances_whenOnePropertyInsuranceIsInList_thenReturnList() throws Exception {
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
        propertyInsuranceRepo.save(propertyInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void getPropertyInsuranceById_whenIdIsValid_thenReturnInsurance() throws Exception {
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

        String newPropertyInsuranceAsJson = objectMapper.writeValueAsString(propertyInsurance);
        propertyInsuranceRepo.save(propertyInsurance);
        mockMvc.perform(get(BASE_URI + "/" + propertyInsurance.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(newPropertyInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void getPropertyInsuranceById_whenIdIsNotValid_thenThrowException() throws Exception {
        mockMvc.perform(get(BASE_URI + "/invalidId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    void addPropertyInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        PropertyInsuranceDTO newPropertyInsurance = PropertyInsuranceDTO.builder()
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

        String propertyInsuranceAsJson = objectMapper.writeValueAsString(newPropertyInsurance);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propertyInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(propertyInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addPropertyInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        PropertyInsuranceDTO newPropertyInsurance = PropertyInsuranceDTO.builder()
                .firstName("TestFirstName")
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

        String propertyInsuranceAsJson = objectMapper.writeValueAsString(newPropertyInsurance);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propertyInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(propertyInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void updatePropertyInsurance_whenInsuranceIdExistsInDb_thenReturnUpdatedInsurance() throws Exception {
        PropertyInsurance propertyInsuranceBefore = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 456")
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
                .paymentPerMonth(BigDecimal.valueOf(90))
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
                .paymentPerMonth(BigDecimal.valueOf(90))
                .endDate(LocalDate.of(2030, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();

        String updatedPropertyInsuranceAsJson = objectMapper.writeValueAsString(updatedPropertyInsurance);
        String propertyInsuranceUpdateDTOAsJson = objectMapper.writeValueAsString(propertyInsuranceUpdateDTO);

        propertyInsuranceRepo.save(propertyInsuranceBefore);

        mockMvc.perform(get(BASE_URI + "/" + propertyInsuranceBefore.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(propertyInsuranceBefore)));

        mockMvc.perform(put(BASE_URI + "/" + updatedPropertyInsurance.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPropertyInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(propertyInsuranceUpdateDTOAsJson));
    }

    @Test
    @DirtiesContext
    void updatePropertyInsurance_whenInsuranceIdDoesNotExistsInDb_thenThrowException() throws Exception {
        PropertyInsuranceUpdateDTO propertyInsuranceUpdateDTO = PropertyInsuranceUpdateDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .duration(72)
                .paymentPerMonth(BigDecimal.valueOf(90))
                .endDate(LocalDate.of(2030, 1, 1))
                .propertyType("House")
                .propertyAddress("Test str. 1")
                .constructionYear(1994)
                .build();

        String propertyInsuranceUpdateDTOasJson = objectMapper.writeValueAsString(propertyInsuranceUpdateDTO);

        mockMvc.perform(put(BASE_URI + "/invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propertyInsuranceUpdateDTOasJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    void deletePropertyInsurance() throws Exception {
        PropertyInsurance propertyInsurance = PropertyInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 456")
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
        propertyInsuranceRepo.save(propertyInsurance);
        mockMvc.perform(delete(BASE_URI + "/" + propertyInsurance.id()))
                .andExpect(status().isOk());
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}