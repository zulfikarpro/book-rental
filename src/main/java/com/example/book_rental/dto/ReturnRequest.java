package com.example.book_rental.dto;

public class ReturnRequest {
    private Long transactionId; // optional
    private Long copyId;        // optional
    private Long userId;        // optional
    // getters/setters


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

