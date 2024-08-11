package com.librarymanagement.library_management.dto;

public class PatronBookIdsDto {
    private Long patronId;
    private Long bookId;

    public PatronBookIdsDto() {
    }

    public PatronBookIdsDto(Long patronId, Long bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
