package com.example.book_rental.service;

import com.example.book_rental.entity.Borrower;
import com.example.book_rental.repository.BorrowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BorrowerRepository borrowerRepository;

    public UserService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public Borrower registerUser(String fullName, String email) {
        try{
            Borrower b = new Borrower(fullName, email);
            return borrowerRepository.save(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Borrower> getAllUsers() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getUserById(Long id) {
        try{
            return borrowerRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

