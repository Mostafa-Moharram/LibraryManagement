package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.dto.PatronBookIdsDto;
import com.librarymanagement.library_management.entity.Book;
import com.librarymanagement.library_management.entity.Borrowing;
import com.librarymanagement.library_management.entity.Patron;
import com.librarymanagement.library_management.repository.BookRepository;
import com.librarymanagement.library_management.repository.BorrowingRepository;
import com.librarymanagement.library_management.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class BorrowingService {
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;
    private final PatronRepository patronRepository;

    @Autowired
    public BorrowingService(BookRepository bookRepository, BorrowingRepository borrowingRepository, PatronRepository patronRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public BorrowResult borrowBook(PatronBookIdsDto patronBookIdsDto) {
        Optional<Borrowing> oldBorrowing = borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(
                patronBookIdsDto.getBookId(),
                patronBookIdsDto.getPatronId());
        if (oldBorrowing.isPresent())
            return BorrowResult.failed(BorrowResult.BOOK_ALREADY_BORROWED_BY_PATRON);
        Optional<Book> bookOptional = bookRepository.findById(patronBookIdsDto.getBookId());
        if (bookOptional.isEmpty())
            return BorrowResult.failed(BorrowResult.NO_BOOK_FOUND);
        Optional<Patron> patronOptional = patronRepository.findById(patronBookIdsDto.getPatronId());
        if (patronOptional.isEmpty())
            return BorrowResult.failed(BorrowResult.NO_PATRON_FOUND);
        Book book = bookOptional.get();
        if (book.getAvailableCopiesCount() <= 0)
            return BorrowResult.failed(BorrowResult.NO_AVAILABLE_BOOK_COPY);
        book.setAvailableCopiesCount(book.getAvailableCopiesCount() - 1);
        book.setBorrowedCopiesCount(book.getBorrowedCopiesCount() + 1);
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        Borrowing borrowing = new Borrowing(
                book,
                patronOptional.get(),
                currentDateTime,
                null,
                currentDateTime.plus(Borrowing.MAX_BORROWING_LENGTH));
        borrowingRepository.save(borrowing);
        return BorrowResult.succeeded(borrowing);
    }

    @Transactional
    public Optional<Borrowing> returnBook(PatronBookIdsDto patronBookIdsDto) {
        Optional<Borrowing> borrowingOptional = borrowingRepository.getNonReturnedBorrowsOfBookIdByPatronId(
                patronBookIdsDto.getBookId(),
                patronBookIdsDto.getPatronId()
        );
        if (borrowingOptional.isEmpty())
            return Optional.empty();
        Borrowing borrowing = borrowingOptional.get();
        borrowing.setReturnDate(ZonedDateTime.now());
        Book book = borrowing.getBook();
        book.setBorrowedCopiesCount(book.getBorrowedCopiesCount() - 1);
        book.setAvailableCopiesCount(book.getAvailableCopiesCount() + 1);
        return Optional.of(borrowing);
    }

    public static class BorrowResult {
        /* Borrow result status begin */
        public static int SUCCESS = 1;
        public static int NO_PATRON_FOUND = 2;
        public static int NO_BOOK_FOUND = 3;
        public static int NO_AVAILABLE_BOOK_COPY = 4;
        public static int BOOK_ALREADY_BORROWED_BY_PATRON = 5;
        /* Borrow result status end */

        private final int status;
        private final Borrowing borrowingOptional;

        private BorrowResult(int status, Borrowing borrowingOptional) {
            this.status = status;
            this.borrowingOptional = borrowingOptional;
        }

        public int getStatus() {
            return status;
        }

        public Borrowing getBorrowing() {
            return borrowingOptional;
        }

        public static BorrowResult failed(int status) {
            return new BorrowResult(status, null);
        }

        public static BorrowResult succeeded(Borrowing borrowing) {
            return new BorrowResult(SUCCESS, borrowing);
        }
    }
}
