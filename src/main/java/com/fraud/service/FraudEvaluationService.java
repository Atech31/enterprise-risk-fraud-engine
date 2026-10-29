package com.fraud.service;

import com.fraud.model.Transaction;
import com.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FraudEvaluationService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction evaluateTransaction(Transaction transaction) {
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }

        double score = 0.0;
        
        // Rule 1: High Amount Thresholds
        if (transaction.getAmount() > 10000.0) {
            score += 50.0;
        } else if (transaction.getAmount() > 5000.0) {
            score += 25.0;
        }

        // Rule 2: High Risk Categories
        if ("CRYPTO".equalsIgnoreCase(transaction.getMerchantCategory()) || 
            "GAMBLING".equalsIgnoreCase(transaction.getMerchantCategory())) {
            score += 30.0;
        }

        // Rule 3: Velocity Check (Anomalous activity in the last 10 minutes)
        LocalDateTime tenMinutesAgo = transaction.getTimestamp().minusMinutes(10);
        List<Transaction> recentTransactions = transactionRepository.findByAccountIdAndTimestampAfter(
            transaction.getAccountId(), tenMinutesAgo
        );

        if (recentTransactions.size() >= 3) {
            score += 40.0; // High velocity penalty
        }

        transaction.setRiskScore(score);

        // Assign risk status based on accumulated score
        if (score >= 50.0) {
            transaction.setRiskStatus("FLAGGED");
        } else {
            transaction.setRiskStatus("APPROVED");
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAccountHistory(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}