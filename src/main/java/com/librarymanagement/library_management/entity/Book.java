package com.librarymanagement.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^(?:\\d[\\- ]?){9}[\\dX]$|^(?:\\d[\\- ]?){13}$")
    @Column(unique = true)
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String authorName;
    @NotNull
    @Min(value = 1000)
    private Integer publicationYear;
    @NotNull
    @Min(value = 0)
    private Integer availableCopiesCount;
    @NotNull
    private Integer borrowedCopiesCount;

    public Book(String isbn, String title, String authorName, int publicationYear, int availableCopiesCount) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
        this.availableCopiesCount = availableCopiesCount;
        this.borrowedCopiesCount = 0;
    }
    public Book() {
    }

    public @NotNull Integer getBorrowedCopiesCount() {
        return borrowedCopiesCount;
    }

    public void setBorrowedCopiesCount(@NotNull Integer borrowedCopiesCount) {
        this.borrowedCopiesCount = borrowedCopiesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(@NotNull @Min(value = 1000) Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public @NotNull @Min(value = 0) Integer getAvailableCopiesCount() {
        return availableCopiesCount;
    }

    public void setAvailableCopiesCount(@NotNull @Min(value = 0) Integer availableCopiesCount) {
        this.availableCopiesCount = availableCopiesCount;
    }
}
