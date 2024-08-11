package com.librarymanagement.library_management.repository;

import com.librarymanagement.library_management.entity.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTests {
    private final BookRepository bookRepository;
    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    @Order(1)
    void testNullIsbnBookSave() {
        Book book = new Book(
                null,
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(2)
    void testInvalidIsbnFormatBookSave() {
        Book book = new Book(
                "73521122",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(3)
    void testNullTitleBookSave() {
        Book book = new Book(
                "978-0735211292",
                null,
                "James Clear",
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(4)
    void testBlankTitleBookSave() {
        Book book = new Book(
                "978-0735211292",
                "        ",
                "James Clear",
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(5)
    void testNullAuthorNameBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                null,
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(6)
    void testBlankAuthorNameBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "   ",
                2018,
                20
        );
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(7)
    void testEarlierThan1000PublicationYearBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                999,
                20);
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(8)
    void testNegativeAvailableCopiesCountBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                999,
                -1);
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            bookRepository.save(book);
        });
    }

    @Test
    @Order(9)
    void testValidBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                20);
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook.getId());
        assertEquals(book.getIsbn(), savedBook.getIsbn());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthorName(), savedBook.getAuthorName());
        assertEquals(book.getPublicationYear(), savedBook.getPublicationYear());
        assertEquals(book.getAvailableCopiesCount(), savedBook.getAvailableCopiesCount());
        assertEquals(0, savedBook.getBorrowedCopiesCount());
    }

    @Test
    @Order(10)
    void testIsbnUniquenessBookSave() {
        Book book = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                20
        );
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            bookRepository.save(book);
        });
    }
}
