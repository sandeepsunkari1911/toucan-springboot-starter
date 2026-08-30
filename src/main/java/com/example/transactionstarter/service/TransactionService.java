package com.example.transactionstarter.service;

import com.example.transactionstarter.exception.ResourceNotFoundException;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    // 1. Create transaction
    public Transaction create(Transaction transaction) {

        if (transaction == null) {
            throw new IllegalArgumentException("Transaction is required");
        }

        if (transaction.getTransactionId() == null
                || transaction.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("Transaction ID is required");
        }

        if (transaction.getCustomerId() == null
                || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }

        if (transaction.getAmount() == null
                || transaction.getAmount().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        if (transaction.getCurrency() == null
                || transaction.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (transaction.getTransactionType() == null
                || transaction.getTransactionType().isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction type is required");
        }

        if (repository.existsById(transaction.getTransactionId())) {
            throw new IllegalArgumentException(
                    "Transaction ID already exists: "
                            + transaction.getTransactionId());
        }

        transaction.setTransactionStatus("PENDING");

        return repository.save(transaction);
    }

    // 2. Get transaction by ID
    public Transaction getById(String transactionId) {

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction ID is required");
        }

        return repository.findById(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found: " + transactionId
                        )
                );
    }

    // 3. Update transaction status
    public Transaction updateStatus(
            String transactionId,
            String newStatus) {

        Transaction transaction = getById(transactionId);

        if (!"PENDING".equals(transaction.getTransactionStatus())) {
            throw new IllegalArgumentException(
                    "Only PENDING transactions can be updated");
        }

        if (!"COMPLETED".equals(newStatus)
                && !"FAILED".equals(newStatus)) {
            throw new IllegalArgumentException(
                    "Status must be COMPLETED or FAILED");
        }

        transaction.setTransactionStatus(newStatus);

        return repository.save(transaction);
    }

    // 4. Get transactions by customer ID
    public List<Transaction> getByCustomerId(String customerId) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID is required");
        }

        return repository.findByCustomerId(customerId);
    }
}