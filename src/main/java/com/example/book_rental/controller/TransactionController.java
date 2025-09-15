package com.example.book_rental.controller;

import com.example.book_rental.dto.GeneralAPIResponse;
import com.example.book_rental.dto.ReturnRequest;
import com.example.book_rental.dto.TransactionRequest;
import com.example.book_rental.entity.Transaction;
import com.example.book_rental.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService trxService;

    public TransactionController(TransactionService trxService) {
        this.trxService = trxService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<GeneralAPIResponse<Transaction>> borrow(@RequestBody TransactionRequest req) {
        Transaction trx = trxService.borrow(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GeneralAPIResponse<>("Success", trx));
    }

    @PostMapping("/return")
    public ResponseEntity<GeneralAPIResponse<Transaction>> returnBook(@RequestBody ReturnRequest req) {
        Transaction trx = trxService.returnBook(req);
        return ResponseEntity.ok(new GeneralAPIResponse<>("Success", trx));
    }
}