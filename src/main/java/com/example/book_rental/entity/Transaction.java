package com.example.book_rental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private BookCopy copy;

    private LocalDateTime borrowDate = LocalDateTime.now();

    private LocalDateTime returnDate;

    private String status = "BORROWED";

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public void setCopy(BookCopy copy) {
        this.copy = copy;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    // getters & setters
}
