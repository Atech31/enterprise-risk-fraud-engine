package com.fraud.repository;

import com.fraud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionId(String transactionId);
    List<Transaction> findByAccountId(String accountId);
    List<Transaction> findByAccountIdAndTimestampAfter(String accountId, LocalDateTime timestamp);
}