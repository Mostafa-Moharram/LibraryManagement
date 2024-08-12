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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookEndpointTests {
    private final MockMvc mockMvc;
    
    @Autowired
    public BookEndpointTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @Order(1)
    void testInvalidBookNoIsbn() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNoIsbn.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    void testInvalidBookIsbnFormat() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookIsbnFormat.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void testInvalidBookNoTitle() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNoTitle.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void testInvalidBookBlankTitle() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookBlankTitle.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void testInvalidBookNoAuthorName() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNoAuthorName.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    void testInvalidBookBlankAuthorName() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookBlankAuthorName.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    void testInvalidBookNoPublicationYear() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNoPublicationYear.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void testInvalidBookPublicationYearEarlierThan1000() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookPublicationYearEarlierThan1000.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void testInvalidBookNoAvailableCopiesCount() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNoAvailableCopiesCount.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    void testInvalidBookNegativeAvailableCopiesCount() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/invalidBookNegativeAvailableCopiesCount.json")));
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    void testPostValidBook1() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/validBook1.json")));
        postValidBook(bookAttributes);
    }

    @Test
    @Order(12)
    void testPostValidBook2() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/validBook2.json")));
        postValidBook(bookAttributes);
    }

    @Test
    @Order(13)
    void testPutValidBook1WithShorterTitle() throws Exception {
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/validBook1WithShorterTitle.json")));
        MvcResult result = mockMvc.perform(put("/api/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookAttributes))
                .andExpect(status().isOk())
                .andReturn();
        String responseBookAttributes = result.getResponse().getContentAsString();
        compareResponseBookWithBook(responseBookAttributes, bookAttributes, 0);
    }

    @Test
    @Order(14)
    void testGetInvalidBookId100() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(15)
    void testGetBook1WithShorterTitle() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/books/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        String responseBookAttributes = result.getResponse().getContentAsString();
        String bookAttributes = new String(Files.readAllBytes(Path.of("src/test/resources/books/validBook1WithShorterTitle.json")));
        compareResponseBookWithBook(responseBookAttributes, bookAttributes, 0);
    }

    @Test
    @Order(100)
    void testDeleteBookId1() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1))
                .andExpect(status().isOk());
    }
    @Test
    @Order(101)
    void testDeleteBookId1Idempotency() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1))
                .andExpect(status().isNotFound());
    }

    private static final String[] BOOK_FIELDS_TO_CHECK = {
            "isbn", "title", "authorName", "publicationYear", "availableCopiesCount"
    };
    private void postValidBook(String bookAttributes) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAttributes))
                .andExpect(status().isCreated()).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        compareResponseBookWithBook(responseContent, bookAttributes, 0);

    }
    private void compareResponseBookWithBook(String responseBookContent, String bookContent, int ExpectedBorrowedCopiesCount) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseBookContent, Map.class);
        assertTrue(responseMap.containsKey("id"));
        assertTrue(responseMap.containsKey("borrowedCopiesCount"));
        Object id = responseMap.get("id");
        Object borrowedCopiesCount = responseMap.get("borrowedCopiesCount");
        assertNotNull(id);
        assertEquals(ExpectedBorrowedCopiesCount, borrowedCopiesCount);
        ObjectMapper objectMapperExpectedBook = new ObjectMapper();
        Map<String, Object> responseMapExpectedBook = objectMapperExpectedBook.readValue(bookContent, Map.class);
        for (String field : BOOK_FIELDS_TO_CHECK)
            assertEquals(responseMapExpectedBook.get(field), responseMap.get(field));
    }
}
