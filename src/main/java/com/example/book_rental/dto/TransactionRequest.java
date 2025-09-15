package com.example.book_rental.dto;

public class TransactionRequest {
    private Long userId;
    private Long copyId;     // optional
    private String isbn;     // optional (if provided, system will pick an available copy)
    private int durationDays;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    // getters/setters/validation annotations
}
