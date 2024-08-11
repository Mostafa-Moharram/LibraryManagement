package com.librarymanagement.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Period;
import java.time.ZonedDateTime;

@Entity
public class Borrowing {
    public static final Period MAX_BORROWING_LENGTH = Period.ofWeeks(2);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Patron patron;

    @NotNull
    private ZonedDateTime borrowDate;

    @NotNull
    private ZonedDateTime dueDate;

    private ZonedDateTime returnDate;

    public Borrowing(Book book, Patron patron, ZonedDateTime borrowDate, ZonedDateTime returnDate, ZonedDateTime dueDate) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
    }

    public Borrowing() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public @NotNull ZonedDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(@NotNull ZonedDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public @NotNull ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }
}