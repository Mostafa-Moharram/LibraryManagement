package com.librarymanagement.library_management.repository;

import com.librarymanagement.library_management.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    @Query("SELECT b FROM Borrowing b WHERE b.book.id = :bookId AND b.patron.id = :patronId AND b.returnDate IS NULL")
    Optional<Borrowing> getNonReturnedBorrowsOfBookIdByPatronId(@Param("bookId") Long bookId, @Param("patronId") Long patronId);
}
