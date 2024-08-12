package com.librarymanagement.library_management.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.library_management.entity.Book;
import com.librarymanagement.library_management.entity.Patron;
import com.librarymanagement.library_management.repository.BookRepository;
import com.librarymanagement.library_management.repository.PatronRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowingEndpointTests {
    private final MockMvc mockMvc;
    @Autowired
    public BorrowingEndpointTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeAll
    static void setUpBookData(@Autowired BookRepository bookRepository) {
        Book book1 = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                1
        );
        Book book2 = new Book(
                "978-0385348638",
                "Better Than Before: What I Learned About Making and Breaking Habits to Sleep More, Quit Sugar, Procrastinate Less, and Generally Build a Happier Life",
                "Gretchen Rubin",
                2015,
                15
        );
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @BeforeAll
    static void setUpPatronData(@Autowired PatronRepository patronRepository) {
        Patron patron1 = new Patron(
                "Personfirstname",
                "Personlastname",
                LocalDate.of(1999, 9, 20),
                "01223456789",
                "personfirstname.personlastname@domain.com",
                "some place city country"
        );
        Patron patron2 = new Patron(
                "Pfn",
                "Pln",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "pfn.pln@domain.com",
                "Street place city country"
        );
        patronRepository.save(patron1);
        patronRepository.save(patron2);
    }

    @Test
    @Order(1)
    void invalidPostPatronId5() throws Exception {
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 1, 5))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    void invalidPostBookId7() throws Exception {
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 7, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void validPostPatron1Book1() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 1, 1))
                .andExpect(status().isOk())
                .andReturn();
        String borrowingContentString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(borrowingContentString, Map.class);
        assertTrue(map.containsKey("returnDate"));
        assertNull(map.get("returnDate"));
    }

    @Test
    @Order(4)
    void invalidPostPatron2Book1NoAvailableCopies() throws Exception {
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 1, 2))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void invalidPostPatron1Book1AlreadyBorrowed() throws Exception {
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 1, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    void validPutPatron1Book2() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", 1, 1))
                .andExpect(status().isOk())
                .andReturn();
        String borrowingContentString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(borrowingContentString, Map.class);
        assertTrue(map.containsKey("returnDate"));
        assertNotNull(map.get("returnDate"));
    }
}
