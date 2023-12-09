package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.LifeInsuranceDTO;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsuranceUpdateDTO;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
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
class LifeInsuranceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LifeInsuranceRepo lifeInsuranceRepo;
    private static final String BASE_URI = "/api/life";

    @Test
    @DirtiesContext
    void getAllLifeInsurances_whenNoLifeInsuranceIsInList_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllLifeInsurances_whenOneLifeInsuranceIsInList_thenReturnList() throws Exception {
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
        lifeInsuranceRepo.save(lifeInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expected);
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void getLifeInsuranceById_whenIdIsValid_thenReturnInsurance() throws Exception {
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

        String newLifeInsuranceAsJson = objectMapper.writeValueAsString(lifeInsurance);
        lifeInsuranceRepo.save(lifeInsurance);
        mockMvc.perform(get(BASE_URI + "/" + lifeInsurance.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(newLifeInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void getLifeInsuranceById_whenIdIsNotValid_thenThrowException() throws Exception {
        mockMvc.perform(get(BASE_URI + "/invalidId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    void addLifeInsurance_whenDataIsComplete_thenReturnCompleteInsurance() throws Exception {
        LifeInsuranceDTO newLifeInsurance = LifeInsuranceDTO.builder()
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

        String lifeInsuranceAsJson = objectMapper.writeValueAsString(newLifeInsurance);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void addLifeInsurance_whenJustOneFieldIsFilledOut_thenReturnNullForEmptyFields() throws Exception {
        LifeInsuranceDTO newLifeInsurance = LifeInsuranceDTO.builder()
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
                .hasHealthIssues(null)
                .build();

        String lifeInsuranceAsJson = objectMapper.writeValueAsString(newLifeInsurance);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceAsJson));
    }

    @Test
    @DirtiesContext
    void updateLifeInsurance_whenInsuranceIdExistsInDb_thenReturnUpdatedInsurance() throws Exception {
        LifeInsurance lifeInsuranceBefore = LifeInsurance.builder()
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

        LifeInsurance updatedLifeInsurance = LifeInsurance.builder()
                .id("1")
                .firstName("TestFirstName")
                .familyName("TestFamilyName")
                .zipCode("65432")
                .city("NewTestCity")
                .address("Test str. 456")
                .telephone("044444")
                .email("testmail@example.com")
                .type(InsuranceType.LIFE)
                .duration(72)
                .paymentPerMonth(BigDecimal.valueOf(90))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2030, 1, 1))
                .hasHealthIssues(false)
                .healthConditionDetails("")
                .build();

        LifeInsuranceUpdateDTO lifeInsuranceUpdateDTO = LifeInsuranceUpdateDTO.builder()
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
                .hasHealthIssues(false)
                .healthConditionDetails("")
                .build();

        String updatedLifeInsuranceAsJson = objectMapper.writeValueAsString(updatedLifeInsurance);
        String lifeInsuranceUpdateDTOAsJson = objectMapper.writeValueAsString(lifeInsuranceUpdateDTO);

        lifeInsuranceRepo.save(lifeInsuranceBefore);

        mockMvc.perform(get(BASE_URI + "/" + lifeInsuranceBefore.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lifeInsuranceBefore)));

        mockMvc.perform(put(BASE_URI + "/" + updatedLifeInsurance.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedLifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceUpdateDTOAsJson));
    }

    @Test
    @DirtiesContext
    void updateLifeInsurance_whenInsuranceIdDoesNotExistsInDb_thenThrowException() throws Exception {
        LifeInsuranceUpdateDTO lifeInsuranceUpdateDTO = LifeInsuranceUpdateDTO.builder()
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
                .hasHealthIssues(false)
                .healthConditionDetails("")
                .build();

        String lifeInsuranceUpdateDTOasJson = objectMapper.writeValueAsString(lifeInsuranceUpdateDTO);

        mockMvc.perform(put(BASE_URI + "/invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceUpdateDTOasJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no insurance with this id"));
    }

    @Test
    @DirtiesContext
    void deleteLifeInsurance() throws Exception {
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
        lifeInsuranceRepo.save(lifeInsurance);
        mockMvc.perform(delete(BASE_URI + "/" + lifeInsurance.id()))
                .andExpect(status().isOk());
        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}