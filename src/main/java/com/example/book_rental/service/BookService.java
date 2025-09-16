package com.example.book_rental.service;

import com.example.book_rental.entity.Book;
import com.example.book_rental.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book registerBook(String isbn, String title, String author) {
        Optional<Book> existing = bookRepository.findByIsbn(isbn);
        if (existing.isPresent()) {
            Book book = existing.get();
            return bookRepository.save(book);
        }
        Book newBook = new Book(isbn, title, author);
        return bookRepository.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
