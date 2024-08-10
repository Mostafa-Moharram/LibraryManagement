package com.librarymanagement.library_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UpsertBookDto {
    @Pattern(regexp = "^(?:\\d[\\- ]?){9}[\\dX]$|^(?:\\d[\\- ]?){13}$", message = "Invalid ISBN format. Please enter a valid ISBN-10 or ISBN-13.")
    private String isbn;

    @NotBlank(message = "Title cannot be empty or contain only whitespace.")
    private String title;

    @NotBlank(message = "Author name cannot be empty or contain only whitespace.")
    private String authorName;

    @NotNull(message = "Publication year is required and must be a valid year.")
    @Min(value = 1000, message = "Publication year must be a valid year (e.g., 1900 or later).")
    private Integer publicationYear;

    public UpsertBookDto(String isbn, String title, String authorName, Integer publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
    }

    public UpsertBookDto() {
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
