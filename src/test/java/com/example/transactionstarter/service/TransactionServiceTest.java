package com.example.transactionstarter.service;

import com.example.transactionstarter.exception.ResourceNotFoundException;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    // Test 1: transaction created successfully
    @Test
    void shouldCreateTransaction() {

        Transaction transaction = new Transaction(
                "TXN001",
                "CUST001",
                new BigDecimal("500.00"),
                "USD",
                "PAYMENT",
                "PENDING"
        );

        Transaction result =
                transactionService.create(transaction);

        assertNotNull(result);
        assertEquals("TXN001",
                result.getTransactionId());

        assertEquals("CUST001",
                result.getCustomerId());

        assertEquals(
                new BigDecimal("500.00"),
                result.getAmount()
        );

        assertEquals(
                "PENDING",
                result.getTransactionStatus()
        );
    }

    // Test 2: invalid amount rejected
    @Test
    void shouldRejectInvalidAmount() {

        Transaction transaction = new Transaction(
                "TXN002",
                "CUST001",
                BigDecimal.ZERO,
                "USD",
                "PAYMENT",
                "PENDING"
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> transactionService.create(transaction)
                );

        assertEquals(
                "Amount must be greater than zero",
                exception.getMessage()
        );
    }

    // Test 3: duplicate transaction ID rejected
    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction first = new Transaction(
                "TXN003",
                "CUST001",
                new BigDecimal("100.00"),
                "USD",
                "PAYMENT",
                "PENDING"
        );

        transactionService.create(first);

        Transaction duplicate = new Transaction(
                "TXN003",
                "CUST002",
                new BigDecimal("200.00"),
                "USD",
                "PAYMENT",
                "PENDING"
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> transactionService.create(duplicate)
                );

        assertEquals(
                "Transaction ID already exists: TXN003",
                exception.getMessage()
        );
    }

    // Test 4: transaction not found
    @Test
    void shouldThrowWhenTransactionDoesNotExist() {

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> transactionService.getById(
                                "DOESNOTEXIST"
                        )
                );

        assertEquals(
                "Transaction not found: DOESNOTEXIST",
                exception.getMessage()
        );
    }

    // Extra test: status update
    @Test
    void shouldUpdateTransactionStatus() {

        Transaction transaction = new Transaction(
                "TXN004",
                "CUST001",
                new BigDecimal("300.00"),
                "USD",
                "PAYMENT",
                "PENDING"
        );

        transactionService.create(transaction);

        Transaction updated =
                transactionService.updateStatus(
                        "TXN004",
                        "COMPLETED"
                );

        assertEquals(
                "COMPLETED",
                updated.getTransactionStatus()
        );
    }
}