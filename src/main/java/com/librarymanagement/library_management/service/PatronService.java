package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.dto.UpsertPatronDto;
import com.librarymanagement.library_management.entity.Patron;
import com.librarymanagement.library_management.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long Id) {
        return patronRepository.findById(Id);
    }

    public Patron addPatron(UpsertPatronDto upsertPatronDto) {
        Patron patron = new Patron(
                upsertPatronDto.getFirstName(),
                upsertPatronDto.getLastName(),
                upsertPatronDto.getDateOfBirth(),
                upsertPatronDto.getPhoneNumber(),
                upsertPatronDto.getEmail(),
                upsertPatronDto.getAddress()
        );
        return patronRepository.save(patron);
    }

    @Transactional
    public Optional<Patron> updatePatron(Long Id, UpsertPatronDto upsertPatronDto) {
        Optional<Patron> patronOptional = patronRepository.findById(Id);
        if (patronOptional.isEmpty())
            return Optional.empty();
        Patron patron = patronOptional.get();
        patron.setFirstName(upsertPatronDto.getFirstName());
        patron.setLastName(upsertPatronDto.getLastName());
        patron.setDateOfBirth(upsertPatronDto.getDateOfBirth());
        patron.setPhoneNumber(upsertPatronDto.getPhoneNumber());
        patron.setEmail(upsertPatronDto.getEmail());
        patron.setAddress(upsertPatronDto.getAddress());
        return Optional.of(patron);
    }

    public boolean deleteBook(Long Id) {
        if (!patronRepository.existsById(Id))
            return false;
        patronRepository.deleteById(Id);
        return true;
    }
}
