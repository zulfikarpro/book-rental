package com.example.book_rental.dto;

public record BookRegisterRequest(String isbn, String title, String author, Integer quantity, String rackLocation) {
}
