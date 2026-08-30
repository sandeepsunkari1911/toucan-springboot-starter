package com.example.transactionstarter.controller;

import com.example.transactionstarter.service.TransactionService;
import com.example.transactionstarter.transaction.Transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(
            TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    // 1. Create transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody Transaction transaction) {

        Transaction created =
                transactionService.create(transaction);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // 2. Get transaction
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(
            @PathVariable String transactionId) {

        Transaction transaction =
                transactionService.getById(transactionId);

        return ResponseEntity.ok(transaction);
    }

    // 3. Update status
    @PatchMapping("/{transactionId}/status")
    public ResponseEntity<Transaction> updateStatus(
            @PathVariable String transactionId,
            @RequestParam String status) {

        Transaction updated =
                transactionService.updateStatus(
                        transactionId,
                        status
                );

        return ResponseEntity.ok(updated);
    }

    // 4. Get customer transactions
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Transaction>>
    getCustomerTransactions(
            @PathVariable String customerId) {

        List<Transaction> transactions =
                transactionService.getByCustomerId(customerId);

        return ResponseEntity.ok(transactions);
    }
}