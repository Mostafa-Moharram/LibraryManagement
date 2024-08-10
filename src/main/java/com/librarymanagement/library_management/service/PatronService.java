package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.dto.UpsertPatronDto;
import com.librarymanagement.library_management.entity.Patron;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatronService {
    private static final Map<Long, Patron> mp = initialize();
    private static long newId = 3L;
    private static Map<Long, Patron> initialize() {
        Map<Long, Patron> mp = new HashMap<>();
        mp.put(1L, new Patron(1L, "Mostafa", "Moharram", LocalDate.of(2001, Month.JULY, 14), "01123456789", "moharram.moharram@moharram.com", "St. Area City Country"));
        mp.put(2L, new Patron(2L, "Some", "Person", LocalDate.of(2000, Month.FEBRUARY, 19), "01023456789", "some.person@sp.com", "St. District Area City Country"));
        return mp;
    }

    public List<Patron> getAllPatrons() {
        return mp.values().stream().toList();
    }

    public Optional<Patron> getPatronById(Long Id) {
        return Optional.ofNullable(mp.get(Id));
    }

    public Patron addPatron(UpsertPatronDto upsertPatronDto) {
        Patron patron = new Patron(
                newId,
                upsertPatronDto.getFirstName(),
                upsertPatronDto.getLastName(),
                upsertPatronDto.getDateOfBirth(),
                upsertPatronDto.getPhoneNumber(),
                upsertPatronDto.getEmail(),
                upsertPatronDto.getAddress()
        );
        mp.put(newId, patron);
        ++newId;
        return patron;
    }

    public Optional<Patron> updatePatron(Long Id, UpsertPatronDto upsertPatronDto) {
        Patron patron = mp.get(Id);
        if (patron == null)
            return Optional.empty();
        patron.setFirstName(upsertPatronDto.getFirstName());
        patron.setLastName(upsertPatronDto.getLastName());
        patron.setDateOfBirth(upsertPatronDto.getDateOfBirth());
        patron.setPhoneNumber(upsertPatronDto.getPhoneNumber());
        patron.setEmail(upsertPatronDto.getEmail());
        patron.setAddress(upsertPatronDto.getAddress());
        return Optional.of(patron);
    }

    public boolean deleteBook(Long Id) {
        Patron patron = mp.get(Id);
        if (patron == null)
            return false;
        mp.remove(Id);
        return true;
    }
}
