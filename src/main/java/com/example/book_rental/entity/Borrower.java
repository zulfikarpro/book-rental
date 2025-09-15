package com.example.book_rental.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "borrowers")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    private String fullName;


    @Column(unique = true, nullable = false)
    private String email;

    public Borrower() {}

    public Borrower(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }


    // getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
