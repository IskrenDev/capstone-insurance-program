package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.DTOLifeInsurance;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceAsJson));
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
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lifeInsuranceAsJson))
                .andExpect(status().isOk())
                .andExpect(content().json(lifeInsuranceAsJson));
    }
}