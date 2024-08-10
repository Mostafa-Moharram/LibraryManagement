package com.librarymanagement.library_management.controller;

import com.librarymanagement.library_management.dto.UpsertPatronDto;
import com.librarymanagement.library_management.entity.Patron;
import com.librarymanagement.library_management.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getPatronById(@PathVariable Long Id) {
        Optional<Patron> patronOptional = patronService.getPatronById(Id);
        if (patronOptional.isPresent())
            return ResponseEntity.ok(patronOptional.get());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("No patron with Is `%d` is found.", Id));
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@Valid @RequestBody UpsertPatronDto upsertPatronDto) {
        Patron patron = patronService.addPatron(upsertPatronDto);
        return ResponseEntity
                .created(URI.create(String.format("/api/patrons/%s", patron.getId())))
                .body(patron);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long Id, @Valid @RequestBody UpsertPatronDto upsertPatronDto) {
        Optional<Patron> patronOptional = patronService.updatePatron(Id, upsertPatronDto);
        if (patronOptional.isPresent())
            return ResponseEntity.ok(patronOptional.get());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("No patron with Is `%d` is found.", Id));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long Id) {
        if (patronService.deleteBook(Id))
            return ResponseEntity.ok().build();
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
