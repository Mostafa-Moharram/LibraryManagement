package com.librarymanagement.library_management.controller;

import com.librarymanagement.library_management.dto.UpsertBookDto;
import com.librarymanagement.library_management.entity.Book;
import com.librarymanagement.library_management.service.BookService;
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
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getBookById(@PathVariable Long Id) {
        Optional<Book> bookOptional = bookService.getBookById(Id);
        if (bookOptional.isPresent())
            return ResponseEntity.ok(bookOptional.get());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("No book with Id `%s` is found.", Id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody UpsertBookDto upsertBookDto) {
        Book book = bookService.addBook(upsertBookDto);
        return ResponseEntity
                .created(URI.create(String.format("/api/books/%s", book.getId())))
                .body(book);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updateBook(@PathVariable Long Id, @Valid @RequestBody UpsertBookDto upsertBookDto) {
        Optional<Book> bookOptional = bookService.updateBook(Id, upsertBookDto);
        if (bookOptional.isPresent())
            return ResponseEntity.ok(bookOptional.get());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(String.format("No book with Id %d is found.", Id));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> updateBook(@PathVariable Long Id) {
        if (bookService.deleteBook(Id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
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
