package com.example.book_rental.controller;

import com.example.book_rental.dto.BookRegisterRequest;
import com.example.book_rental.dto.GeneralAPIResponse;
import com.example.book_rental.entity.Book;
import com.example.book_rental.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/register")
    public GeneralAPIResponse<Book> register(@RequestBody BookRegisterRequest request) {
        Book book = bookService.registerBook(request.isbn(), request.title(), request.author());
        return new GeneralAPIResponse<>("Book registered successfully", book);
    }

    @GetMapping
    public GeneralAPIResponse<List<Book>> getAll() {
        List<Book> books = bookService.getAllBooks();
        return new GeneralAPIResponse<>("Books fetched successfully", books);
    }

    @GetMapping("/detail")
    public GeneralAPIResponse<Book> getByIsbn(@RequestParam String isbn) {
        Book book = bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return new GeneralAPIResponse<>("Book fetched successfully", book);
    }
}

