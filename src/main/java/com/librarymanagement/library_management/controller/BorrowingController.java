package com.librarymanagement.library_management.controller;

import com.librarymanagement.library_management.dto.PatronBookIdsDto;
import com.librarymanagement.library_management.entity.Borrowing;
import com.librarymanagement.library_management.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    private final BorrowingService borrowingService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingService.BorrowResult result = borrowingService.borrowBook(new PatronBookIdsDto(patronId, bookId));
        if (result.getStatus() == BorrowingService.BorrowResult.NO_BOOK_FOUND)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(String.format("No book with Id %s.", bookId));
        if (result.getStatus() == BorrowingService.BorrowResult.NO_PATRON_FOUND)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(String.format("No patron with Id %s.", patronId));
        if (result.getStatus() == BorrowingService.BorrowResult.NO_AVAILABLE_BOOK_COPY)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No available book copies.");
        if (result.getStatus() == BorrowingService.BorrowResult.BOOK_ALREADY_BORROWED_BY_PATRON)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The books is already borrowed and not returned yet by the patron.");
        return ResponseEntity.ok(result.getBorrowing());
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        Optional<Borrowing> borrowingOptional = borrowingService.returnBook(new PatronBookIdsDto(patronId, bookId));
        if (borrowingOptional.isPresent())
            return ResponseEntity.ok(borrowingOptional.get());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("No active borrowing of book with Id %s by patron with Id %s.", bookId, patronId));
    }
}
