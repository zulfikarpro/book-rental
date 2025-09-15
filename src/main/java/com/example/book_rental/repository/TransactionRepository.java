package com.example.book_rental.repository;

import com.example.book_rental.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.copy.copyId = :copyId AND t.returnDate IS NULL")
    Optional<Transaction> findActiveByCopyId(@Param("copyId") Long copyId);
}

