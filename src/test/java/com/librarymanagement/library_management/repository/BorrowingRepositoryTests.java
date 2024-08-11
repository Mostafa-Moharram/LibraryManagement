package com.librarymanagement.library_management.repository;

import com.librarymanagement.library_management.entity.Book;
import com.librarymanagement.library_management.entity.Borrowing;
import com.librarymanagement.library_management.entity.Patron;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowingRepositoryTests {
    private BorrowingRepository borrowingRepository;
    private static Book[] books;
    private static Patron[] patrons;

    @Autowired
    public BorrowingRepositoryTests(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    @BeforeAll
    static void setUp(@Autowired BookRepository bookRepository, @Autowired PatronRepository patronRepository) {
        books = new Book[2];
        patrons = new Patron[2];
        Book book1 = new Book(
                "978-0735211292",
                "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                "James Clear",
                2018,
                20);
        Book book2 = new Book(
                "978-1682612385",
                "The 5 Second Rule: Transform your Life, Work, and Confidence with Everyday Courage",
                "Mel Robbins",
                2017,
                30);
        Patron patron1 = new Patron(
                "Fpone",
                "Lpone",
                LocalDate.of(2001, 7, 14),
                "01123456789",
                "fp1.lp1@domain.com",
                "St. Area City Country"
        );
        Patron patron2 = new Patron(
                "Fptwo",
                "Lptwo",
                LocalDate.of(1999, 11, 20),
                "01223456789",
                "fp2.lp2@domain.com",
                "Street Area City Country"
        );
        books[0] = bookRepository.save(book1);
        books[1] = bookRepository.save(book2);
        patrons[0] = patronRepository.save(patron1);
        patrons[1] = patronRepository.save(patron2);
    }

    @Test
    @Order(1)
    void testNullBorrowingDateSave() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Borrowing borrowing = new Borrowing(
                books[0],
                patrons[0],
                null,
                null,
                zonedDateTime.plus(Borrowing.MAX_BORROWING_LENGTH));
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> borrowingRepository.save(borrowing));
    }

    @Test
    @Order(2)
    void testNullDueDateSave() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Borrowing borrowing = new Borrowing(
                books[0],
                patrons[0],
                zonedDateTime,
                null,
                null);
        assertThrows(jakarta.validation.ConstraintViolationException.class,
                () -> borrowingRepository.save(borrowing));
    }

    @Test
    @Order(3)
    void testBorrowingSave() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Borrowing borrowing = new Borrowing(
                books[0],
                patrons[0],
                zonedDateTime,
                null,
                zonedDateTime.plus(Borrowing.MAX_BORROWING_LENGTH));
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        assertNotNull(savedBorrowing.getId());
        assertEquals(borrowing.getBorrowDate(), savedBorrowing.getBorrowDate());
        assertEquals(borrowing.getDueDate(), savedBorrowing.getDueDate());
        assertEquals(borrowing.getBook(), savedBorrowing.getBook());
        assertEquals(borrowing.getPatron(), savedBorrowing.getPatron());
        assertNull(savedBorrowing.getReturnDate());
    }

    @Test
    @Order(4)
    void testNotReturnedBorrows1() {
        Optional<Borrowing> borrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[0].getId(), patrons[0].getId());
        assertTrue(borrowingOptional.isPresent());
    }

    @Test
    @Order(5)
    void testNotReturnedBorrows2() {
        Optional<Borrowing> borrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[1].getId(), patrons[0].getId());
        assertTrue(borrowingOptional.isEmpty());
    }

    @Test
    @Order(6)
    void testNotReturnedBorrows3() {
        Optional<Borrowing> borrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[0].getId(), patrons[1].getId());
        assertTrue(borrowingOptional.isEmpty());
    }

    @Test
    @Order(7)
    void testNotReturnedBorrows4() {
        Optional<Borrowing> borrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[1].getId(), patrons[1].getId());
        assertTrue(borrowingOptional.isEmpty());
    }

    @Test
    @Order(8)
    void testNotReturnedBorrows5() {
        Optional<Borrowing> borrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[0].getId(), patrons[0].getId());
        assertTrue(borrowingOptional.isPresent());
        Borrowing borrowing = borrowingOptional.get();
        borrowing.setReturnDate(ZonedDateTime.now());
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        assertEquals(savedBorrowing.getId(), borrowing.getId());
        Optional<Borrowing> noBorrowingOptional =
                borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(books[0].getId(), patrons[0].getId());
        assertTrue(noBorrowingOptional.isEmpty());
    }
}
