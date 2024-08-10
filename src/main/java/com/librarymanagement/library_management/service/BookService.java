package com.librarymanagement.library_management.service;

import com.librarymanagement.library_management.dto.UpsertBookDto;
import com.librarymanagement.library_management.entity.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    private static long newId = 3;    private static Map<Long, Book> mp = initialize();

    private static Map<Long, Book> initialize() {
        mp = new HashMap<>();
        mp.put(1L, new Book(1L, "978-0451524935", "1984", "George Orwell", 1949));
        mp.put(2L, new Book(2L, "978-0857197689", "The Psychology of Money: Timeless Lessons on Wealth, Greed, and Happiness", "Morgan Housel", 2020));
        return mp;
    }

    public List<Book> getAllBooks() {
        return mp.values().stream().toList();
    }

    public Optional<Book> getBookById(Long Id) {
        return Optional.ofNullable(mp.get(Id));
    }

    public Book addBook(UpsertBookDto upsertBookDto) {
        Book book = new Book(
                newId,
                upsertBookDto.getIsbn(),
                upsertBookDto.getTitle(),
                upsertBookDto.getAuthorName(),
                upsertBookDto.getPublicationYear()
        );
        mp.put(newId, book);
        ++newId;
        return book;
    }

    public Optional<Book> updateBook(Long Id, UpsertBookDto upsertBookDto) {
        Book book = mp.get(Id);
        if (book == null)
            return Optional.empty();
        book.setIsbn(upsertBookDto.getIsbn());
        book.setTitle(upsertBookDto.getTitle());
        book.setAuthorName(upsertBookDto.getAuthorName());
        book.setPublicationYear(upsertBookDto.getPublicationYear());
        return Optional.of(book);
    }

    public boolean deleteBook(Long Id) {
        if (!mp.containsKey(Id))
            return false;
        mp.remove(Id);
        return true;
    }
}
