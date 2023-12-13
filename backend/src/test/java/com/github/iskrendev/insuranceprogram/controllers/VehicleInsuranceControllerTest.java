package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.*;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @WithMockUser
    void getAllVehicleInsurances_whenNoVehicleInsuranceIsInList_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getAllVehicleInsurances_whenListContainsInsurances_thenReturnListOfInsurances() throws Exception {
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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
    @WithMockUser
    void getVehicleInsuranceById_whenIdIsValid_thenReturnInsurance() throws Exception {
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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

        String newVehicleInsuranceAsJson = objectMapper.writeValueAsString(vehicleInsurance);
        vehicleInsuranceRepo.save(vehicleInsurance);
        mockMvc.perform(get(BASE_URI + "/" + vehicleInsurance.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(newVehicleInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getVehicleInsuranceById_whenIdIsNotValid_thenThrowException() throws Exception {
        mockMvc.perform(get(BASE_URI + "/invalidId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void addVehicleInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        VehicleInsuranceDTO newVehicleInsurance = VehicleInsuranceDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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
    @WithMockUser
    void addVehicleInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        VehicleInsuranceDTO newVehicleInsurance = VehicleInsuranceDTO.builder()
                .firstName("TestFirstName")
                .familyName(null)
                .zipCode(null)
                .city(null)
                .address(null)
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

    @Test
    @DirtiesContext
    @WithMockUser
    void updateVehicleInsurance_whenInsuranceIdExistsInDb_thenReturnUpdatedInsurance() throws Exception {
        VehicleInsurance vehicleInsuranceBefore = VehicleInsurance.builder()
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

        VehicleInsurance updatedVehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .type(InsuranceType.VEHICLE)
                .duration(24)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2026, 1, 1))
                .vehicleMake("Testmake")
                .vehicleModel("Testmodel")
                .vehicleYear(2015)
                .licensePlateNumber("AB 123 CD")
                .build();

        VehicleInsuranceUpdateDTO vehicleInsuranceUpdateDTO = VehicleInsuranceUpdateDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .duration(24)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .endDate(LocalDate.of(2026, 1, 1))
                .vehicleMake("Testmake")
                .vehicleModel("Testmodel")
                .vehicleYear(2015)
                .licensePlateNumber("AB 123 CD")
                .build();

        String updatedVehicleInsuranceAsJson = objectMapper.writeValueAsString(updatedVehicleInsurance);
        String vehicleInsuranceUpdateDTOAsJson = objectMapper.writeValueAsString(vehicleInsuranceUpdateDTO);

        vehicleInsuranceRepo.save(vehicleInsuranceBefore);

        mockMvc.perform(get(BASE_URI + "/" + vehicleInsuranceBefore.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleInsuranceBefore)));

        mockMvc.perform(put(BASE_URI + "/" + updatedVehicleInsurance.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedVehicleInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(vehicleInsuranceUpdateDTOAsJson));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateVehicleInsurance_whenInsuranceIdDoesNotExistsInDb_thenThrowException() throws Exception {
        VehicleInsuranceUpdateDTO vehicleInsuranceUpdateDTO = VehicleInsuranceUpdateDTO.builder()
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .duration(24)
                .paymentPerMonth(BigDecimal.valueOf(100))
                .endDate(LocalDate.of(2026, 1, 1))
                .vehicleMake("Testmake")
                .vehicleModel("Testmodel")
                .vehicleYear(2015)
                .licensePlateNumber("AB 123 CD")
                .build();

        String vehicleInsuranceUpdateDTOasJson = objectMapper.writeValueAsString(vehicleInsuranceUpdateDTO);

        mockMvc.perform(put(BASE_URI + "/invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleInsuranceUpdateDTOasJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteVehicleInsurance() throws Exception {
        VehicleInsurance vehicleInsurance = VehicleInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("12345")
                .city("Testcity")
                .address("Test str. 123")
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
        vehicleInsuranceRepo.save(vehicleInsurance);
        mockMvc.perform(delete(BASE_URI + "/" + vehicleInsurance.id()))
                .andExpect(status().isOk());
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}