package com.librarymanagement.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class UpsertPatronDto {
    @NotBlank
    @Pattern(regexp = "^\\p{Lu}\\p{Ll}*$")
    private String firstName;
    @NotBlank
    @Pattern(regexp = "^\\p{Lu}\\p{Ll}*$")
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "An example for valid email is \"john.doe@domain.com\"")
    private String email;
    @NotNull
    private LocalDate dateOfBirth;
    // This pattern matches phone numbers in Egypt. If this thing is international then the validation should be localized to a specific country
    @NotBlank
    @Pattern(regexp = "^01[0125]\\d{8}$")
    private String phoneNumber;
    @NotBlank
    private String address;

    public UpsertPatronDto(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String address) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public UpsertPatronDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
