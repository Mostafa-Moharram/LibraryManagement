package com.librarymanagement.library_management.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatronEndpointTests {
    private final MockMvc mockMvc;
    @Autowired
    public PatronEndpointTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    @Test
    @Order(1)
    void testInvalidPatronEmailFormat() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronEmailFormat.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    void testInvalidPatronFirstNameFormat() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronFirstNameFormat.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void testInvalidPatronLastNameFormat() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronLastNameFormat.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void testInvalidPatronNoAddress() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoAddress.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void testInvalidPatronNoDateOfBirth() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoDateOfBirth.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    void testInvalidPatronNoEmail() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoEmail.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    void testInvalidPatronNoFirstName() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoFirstName.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void testInvalidPatronNoLastName() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoLastName.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void testInvalidPatronNoPhoneNumber() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronNoPhoneNumber.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    void testInvalidPatronPhoneNumberFormat() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/invalidPatronPhoneNumberFormat.json")));
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    void testPostValidPatron1() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/validPatron1.json")));
        postValidPatron(patronAttributes);
    }

    @Test
    @Order(12)
    void testPostValidPatron2() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/validPatron2.json")));
        postValidPatron(patronAttributes);
    }

    @Test
    @Order(13)
    void testPutValidPatron2ChangePhoneNumber() throws Exception {
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/validPatron2ChangePhoneNumber.json")));
        MvcResult result = mockMvc.perform(put("/api/patrons/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isOk())
                .andReturn();
        String responsePatronAttributes = result.getResponse().getContentAsString();
        compareResponsePatronWithPatron(responsePatronAttributes, patronAttributes);
    }

    @Test
    @Order(14)
    void testGetInvalidPatronId100() throws Exception {
        mockMvc.perform(get("/api/patrons/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(15)
    void testGetPatron2WithChangedPhoneNumber() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/patrons/{id}", 2))
                .andExpect(status().isOk())
                .andReturn();
        String responsePatronAttributes = result.getResponse().getContentAsString();
        String patronAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/patrons/validPatron2ChangePhoneNumber.json")));
        compareResponsePatronWithPatron(responsePatronAttributes, patronAttributes);
    }

    @Test
    @Order(100)
    void testDeletePatronId1() throws Exception {
        mockMvc.perform(delete("/api/patrons/{id}", 1))
                .andExpect(status().isOk());
    }
    @Test
    @Order(101)
    void testDeletePatronId1Idempotency() throws Exception {
        mockMvc.perform(delete("/api/patrons/{id}", 1))
                .andExpect(status().isNotFound());
    }

    private static final String[] PATRON_FIELDS_TO_CHECK = {
            "firstName", "lastName", "dateOfBirth", "phoneNumber", "email", "address"
    };
    private void postValidPatron(String patronAttributes) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronAttributes))
                .andExpect(status().isCreated()).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        compareResponsePatronWithPatron(responseContent, patronAttributes);

    }
    private void compareResponsePatronWithPatron(String responsePatronContent, String patronContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responsePatronContent, Map.class);
        assertTrue(responseMap.containsKey("id"));
        Object id = responseMap.get("id");
        assertNotNull(id);
        ObjectMapper objectMapperExpectedPatron = new ObjectMapper();
        Map<String, Object> responseMapExpectedPatron = objectMapperExpectedPatron.readValue(patronContent, Map.class);
        for (String field : PATRON_FIELDS_TO_CHECK)
            assertEquals(responseMapExpectedPatron.get(field), responseMap.get(field));
    }
}
