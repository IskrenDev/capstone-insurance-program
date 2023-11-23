package com.github.iskrendev.insuranceprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InsuranceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URI_LIFE = "/api/insurances/life";
    private static final String BASE_URI_PROPERTY = "/api/insurances/property";
    private static final String BASE_URI_VEHICLE = "/api/insurances/vehicle";

    @Test
    @DirtiesContext
    void addLifeInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        DTOLifeInsurance newLifeInsurance = DTOLifeInsurance.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .email("testmail@example.com")
                .type(InsuranceType.LIFE)
                .duration(48)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2028, 1, 1))
                .hasHealthIssues(false)
                .healthConditionDetails("")
                .build();

            String lifeInsuranceAsJson = objectMapper.writeValueAsString(newLifeInsurance);
            mockMvc.perform(post(BASE_URI_LIFE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(lifeInsuranceAsJson))
                        .andExpect(status().isOk())
                        .andExpect(content().json(lifeInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addPropertyInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        DTOPropertyInsurance newPropertyInsurance = DTOPropertyInsurance.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
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
        mockMvc.perform(post(BASE_URI_PROPERTY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propertyInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(propertyInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addVehicleInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        DTOVehicleInsurance newVehicleInsurance = DTOVehicleInsurance.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
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

        String vehicleInsuranceAsJson = objectMapper.writeValueAsString(newVehicleInsurance);
        mockMvc.perform(post(BASE_URI_VEHICLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(vehicleInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addLifeInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        DTOLifeInsurance newLifeInsurance = DTOLifeInsurance.builder()
                .firstName("TestFirstName")
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
                .build();

        String lifeInsuranceAsJson = objectMapper.writeValueAsString(newLifeInsurance);
        mockMvc.perform(post(BASE_URI_LIFE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addPropertyInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        DTOPropertyInsurance newPropertyInsurance = DTOPropertyInsurance.builder()
                .firstName("TestFirstName")
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

        String propertyInsuranceAsJson = objectMapper.writeValueAsString(newPropertyInsurance);
        mockMvc.perform(post(BASE_URI_PROPERTY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propertyInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(propertyInsuranceAsJson));
    }
    @Test
    @DirtiesContext
    void addVehicleInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        DTOVehicleInsurance newVehicleInsurance = DTOVehicleInsurance.builder()
                .firstName("TestFirstName")
                .familyName(null)
                .zipCode(null)
                .city(null)
                .telephone(null)
                .email(null)
                .duration(null)
                .type(null)
                .paymentPerMonth(null)
                .startDate(null)
                .endDate(null)
                .vehicleMake(null)
                .vehicleModel(null)
                .vehicleYear(null)
                .licensePlateNumber(null)
                .build();

        String vehicleInsuranceAsJson = objectMapper.writeValueAsString(newVehicleInsurance);
        mockMvc.perform(post(BASE_URI_VEHICLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(vehicleInsuranceAsJson));
    }
}