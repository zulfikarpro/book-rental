package com.example.book_rental.service;

import com.example.book_rental.dto.ReturnRequest;
import com.example.book_rental.dto.TransactionRequest;
import com.example.book_rental.entity.BookCopy;
import com.example.book_rental.entity.Borrower;
import com.example.book_rental.entity.Transaction;
import com.example.book_rental.repository.BookCopyRepository;
import com.example.book_rental.repository.BookRepository;
import com.example.book_rental.repository.BorrowerRepository;
import com.example.book_rental.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final BookCopyRepository copyRepo;
    private final BorrowerRepository borrowerRepo;
    private final TransactionRepository trxRepo;
    private final BookRepository bookRepo;

    @Autowired
    public TransactionService(BookCopyRepository copyRepo,
                              BorrowerRepository borrowerRepo,
                              TransactionRepository trxRepo,
                              BookRepository bookRepo) {
        this.copyRepo = copyRepo;
        this.borrowerRepo = borrowerRepo;
        this.trxRepo = trxRepo;
        this.bookRepo = bookRepo;
    }

    @Transactional
    public Transaction borrow(TransactionRequest req) {
        Borrower borrower = borrowerRepo.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BookCopy copy = null;
        if (req.getCopyId() != null) {
            // lock row for update
            copy = copyRepo.findByIdForUpdate(req.getCopyId())
                    .orElseThrow(() -> new EntityNotFoundException("Copy not found"));
            if (!copy.isAvailable()) throw new IllegalStateException("Copy already borrowed");
        } else if (req.getIsbn() != null && !req.getIsbn().isBlank()) {
            // find available by ISBN
            List<BookCopy> avail = copyRepo.findAvailableByIsbn(req.getIsbn());
            if (avail.isEmpty()) throw new IllegalStateException("No available copies for ISBN");
            // pick the first and lock it
            copy = copyRepo.findByIdForUpdate(avail.get(0).getCopyId())
                    .orElseThrow();
        } else {
            throw new IllegalArgumentException("Either copyId or isbn must be provided");
        }

        copy.setAvailable(false); // mark unavailable
        Transaction trx = new Transaction();
        trx.setBorrower(borrower);
        trx.setCopy(copy);
        trx.setBorrowDate(LocalDateTime.now());
        trx.setStatus("BORROWED");
        // optionally compute expected return date based on durationDays; store if needed

        copyRepo.save(copy); // persist available change
        return trxRepo.save(trx);
    }

    @Transactional
    public Transaction returnBook(ReturnRequest req) {
        Transaction trx = null;
        if (req.getTransactionId() != null) {
            trx = trxRepo.findById(req.getTransactionId())
                    .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        } else if (req.getCopyId() != null) {
            trx = trxRepo.findActiveByCopyId(req.getCopyId())
                    .orElseThrow(() -> new EntityNotFoundException("Active transaction not found for copy"));
            // optional: check userId if provided
            if (req.getUserId() != null && !trx.getBorrower().getUserId().equals(req.getUserId())) {
                throw new IllegalArgumentException("Transaction does not belong to user");
            }
        } else {
            throw new IllegalArgumentException("Provide transactionId or copyId");
        }

        trx.setReturnDate(LocalDateTime.now());
        trx.setStatus("RETURNED");
        BookCopy copy = trx.getCopy();
        copy.setAvailable(true);

        copyRepo.save(copy);
        return trxRepo.save(trx);
    }
}
