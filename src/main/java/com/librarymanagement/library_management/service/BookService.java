package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.dto.UpsertBookDto;
import com.librarymanagement.library_management.entity.Book;
import com.librarymanagement.library_management.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long Id) {
        return bookRepository.findById(Id);
    }

    public Book addBook(UpsertBookDto upsertBookDto) {
        Book book = new Book(
                upsertBookDto.getIsbn(),
                upsertBookDto.getTitle(),
                upsertBookDto.getAuthorName(),
                upsertBookDto.getPublicationYear(),
                upsertBookDto.getAvailableCopiesCount()
        );
        bookRepository.save(book);
        return book;
    }

    @Transactional
    public Optional<Book> updateBook(Long Id, UpsertBookDto upsertBookDto) {
        Optional<Book> bookOptional = bookRepository.findById(Id);
        if (bookOptional.isEmpty())
            return Optional.empty();
        Book book = bookOptional.get();
        book.setIsbn(upsertBookDto.getIsbn());
        book.setTitle(upsertBookDto.getTitle());
        book.setAuthorName(upsertBookDto.getAuthorName());
        book.setPublicationYear(upsertBookDto.getPublicationYear());
        book.setAvailableCopiesCount(upsertBookDto.getAvailableCopiesCount());
        return Optional.of(book);
    }

    public boolean deleteBook(Long Id) {
        if (!bookRepository.existsById(Id))
            return false;
        bookRepository.deleteById(Id);
        return true;
    }
}
