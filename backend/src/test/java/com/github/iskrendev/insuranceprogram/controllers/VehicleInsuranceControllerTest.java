package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.DTOVehicleInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class VehicleInsuranceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VehicleInsuranceRepo vehicleInsuranceRepo;
    private static final String BASE_URI = "/api/vehicle";

    @Test
    @DirtiesContext
    void getAllVehicleInsurances_whenNoVehicleInsuranceIsInList_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllVehicleInsurances_whenListContainsInsurances_thenReturnListOfInsurances() throws Exception {
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
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

        List<VehicleInsurance> expected = List.of(vehicleInsurance);
        vehicleInsuranceRepo.save(vehicleInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void getVehicleInsuranceById_whenIdIsValid_thenReturnInsurance() throws Exception {
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
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

        String newPropertyInsuranceAsJson = objectMapper.writeValueAsString(vehicleInsurance);
        vehicleInsuranceRepo.save(vehicleInsurance);
        mockMvc.perform(get(BASE_URI + "/" + vehicleInsurance.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(newPropertyInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void getVehicleInsuranceById_whenIdIsNotValid_thenThrowException() throws Exception {
        mockMvc.perform(get(BASE_URI + "/invalidId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
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
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(vehicleInsuranceAsJson));
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
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(vehicleInsuranceAsJson));
    }
}