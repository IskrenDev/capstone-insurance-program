package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.enums.InsuranceType;
import com.github.iskrendev.insuranceprogram.models.AllInsurancesResponse;
import com.github.iskrendev.insuranceprogram.models.LifeInsurance;
import com.github.iskrendev.insuranceprogram.models.PropertyInsurance;
import com.github.iskrendev.insuranceprogram.models.VehicleInsurance;
import com.github.iskrendev.insuranceprogram.repositories.LifeInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.PropertyInsuranceRepo;
import com.github.iskrendev.insuranceprogram.repositories.VehicleInsuranceRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InsuranceSearchControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LifeInsuranceRepo lifeInsuranceRepo;
    @Autowired
    private PropertyInsuranceRepo propertyInsuranceRepo;
    @Autowired
    private VehicleInsuranceRepo vehicleInsuranceRepo;

    private static final String BASE_URI = "/api/search";

    @Test
    @DirtiesContext
    void searchInsurance_whenNoNamesProvided_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI)
                        .param("type", "all"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void searchInsurance_whenInvalidTypeProvided_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI)
                        .param("type", "invalidType"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void searchInsurance_whenNameDoesNotExistInDatabase_thenReturnEmptyResponseObject() throws Exception {
        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "NonExistentName")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"lifeInsurances\":[],\"propertyInsurances\":[],\"vehicleInsurances\":[]}"));
    }

    @Test
    @DirtiesContext
    void searchInsurance_whenFirstNameAsInput_thenReturnMatchingResults() throws Exception {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);

        AllInsurancesResponse expectedResponse = new AllInsurancesResponse(
                expectedLifeInsurances,
                List.of(),
                List.of());

        lifeInsuranceRepo.save(lifeInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TESTFIRSTNAME")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "testfirstname")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TestFirstName")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void searchInsurance_whenFamilyNameAsInput_thenReturnMatchingResults() throws Exception {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);

        AllInsurancesResponse expectedResponse = new AllInsurancesResponse(
                expectedLifeInsurances,
                List.of(),
                List.of());

        lifeInsuranceRepo.save(lifeInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get(BASE_URI)
                        .param("familyName", "TESTFAMILYNAME")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("familyName", "testfamilyname")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("familyName", "TestFamilyName")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void searchInsurance_whenFirstAndFamilyNameAsInput_thenReturnMatchingResults() throws Exception {
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);

        AllInsurancesResponse expectedResponse = new AllInsurancesResponse(
                expectedLifeInsurances,
                List.of(),
                List.of());

        lifeInsuranceRepo.save(lifeInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TESTFIRSTNAME")
                        .param("familyName", "TESTFAMILYNAME")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "testfirstname")
                        .param("familyName", "testfamilyname")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TestFirstName")
                        .param("familyName", "TestFamilyName")
                        .param("type", "life"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }

    @Test
    @DirtiesContext
    void searchInsurance_forEachInsuranceType_thenReturnMatchingResults() throws Exception {
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
                .id("2")
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
                .id("3")
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

        List<LifeInsurance> expectedLifeInsurances = List.of(lifeInsurance);
        List<PropertyInsurance> expectedPropertyInsurances = List.of(propertyInsurance);
        List<VehicleInsurance> expectedVehicleInsurances = List.of(vehicleInsurance);

        AllInsurancesResponse expectedResponse = new AllInsurancesResponse(
                expectedLifeInsurances,
                expectedPropertyInsurances,
                expectedVehicleInsurances
        );

        lifeInsuranceRepo.save(lifeInsurance);
        propertyInsuranceRepo.save(propertyInsurance);
        vehicleInsuranceRepo.save(vehicleInsurance);

        String expectedAsJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TESTFIRSTNAME")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "testfirstname")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));

        mockMvc.perform(get(BASE_URI)
                        .param("firstName", "TestFirstName")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAsJson));
    }
}