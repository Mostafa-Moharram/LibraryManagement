package com.librarymanagement.library_management.repository;

import com.librarymanagement.library_management.entity.Patron;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatronRepositoryTests {
    private final PatronRepository patronRepository;
    @Autowired
    public PatronRepositoryTests(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Test
    @Order(1)
    void testNullFirstName() {
        Patron patron = new Patron(
                null,
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(2)
    void testFirstNameFormat() {
        Patron patron = new Patron(
                "firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(3)
    void testNullLastName() {
        Patron patron = new Patron(
                "Firstname",
                null,
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(4)
    void testLastNameFormat() {
        Patron patron = new Patron(
                "Firstname",
                "lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(5)
    void testNullDateOfBirthFormat() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                null,
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(6)
    void testNullPhoneNumber() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                null,
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(7)
    void testInvalidPhoneNumberStartingWith7Format() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01723456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(8)
    void testInvalidPhoneNumberShorterThan11Format() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "0112345678",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(9)
    void testNullEmail() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                null,
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(10)
    void testEmailWithoutDomain() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastnamedomain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(11)
    void testEmailWithoutName() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "@domain.com",
                "St. Area City Country"
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(12)
    void testNullAddress() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                null
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(13)
    void testBlankAddress() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "   "
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(14)
    void testSave() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        Patron savedPatron = patronRepository.save(patron);
        assertNotNull(savedPatron.getId());
        assertEquals(patron.getFirstName(), savedPatron.getFirstName());
        assertEquals(patron.getLastName(), savedPatron.getLastName());
        assertEquals(patron.getDateOfBirth(), savedPatron.getDateOfBirth());
        assertEquals(patron.getPhoneNumber(), savedPatron.getPhoneNumber());
        assertEquals(patron.getEmail(), savedPatron.getEmail());
        assertEquals(patron.getAddress(), savedPatron.getAddress());
    }

    @Test
    @Order(15)
    void testEmailUniqueness() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01223456789",
                "firstname.lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }

    @Test
    @Order(16)
    void testPhoneNumberUniqueness() {
        Patron patron = new Patron(
                "Firstname",
                "Lastname",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "lastname@domain.com",
                "St. Area City Country"
        );
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            patronRepository.save(patron);
        });
    }
}
