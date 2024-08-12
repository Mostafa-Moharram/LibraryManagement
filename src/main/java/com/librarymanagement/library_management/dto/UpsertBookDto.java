package com.librarymanagement.library_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UpsertBookDto {
    @NotBlank
    @Pattern(regexp = "^(?:\\d[\\- ]?){9}[\\dX]$|^(?:\\d[\\- ]?){13}$", message = "Invalid ISBN format. Please enter a valid ISBN-10 or ISBN-13.")
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

    public UpsertBookDto(String isbn, String title, String authorName, int publicationYear, int availableCopiesCount) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
        this.availableCopiesCount = availableCopiesCount;
    }

    public UpsertBookDto() {
    }

    public Integer getAvailableCopiesCount() {
        return availableCopiesCount;
    }

    public void setAvailableCopiesCount(Integer availableCopiesCount) {
        this.availableCopiesCount = availableCopiesCount;
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

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
}
