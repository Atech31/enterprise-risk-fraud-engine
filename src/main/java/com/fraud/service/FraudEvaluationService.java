package com.fraud.service;

import com.fraud.model.Transaction;
import com.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudEvaluationService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction evaluateTransaction(Transaction transaction) {
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }

        // Rule-based heuristic risk scoring engine
        double score = 0.0;
        
        // High amount checks
        if (transaction.getAmount() > 10000.0) {
            score += 50.0;
        } else if (transaction.getAmount() > 5000.0) {
            score += 25.0;
        }

        // High risk merchant categories
        if ("CRYPTO".equalsIgnoreCase(transaction.getMerchantCategory()) || 
            "GAMBLING".equalsIgnoreCase(transaction.getMerchantCategory())) {
            score += 30.0;
        }

        transaction.setRiskScore(score);

        // Assign risk status
        if (score >= 50.0) {
            transaction.setRiskStatus("FLAGGED");
        } else {
            transaction.setRiskStatus("APPROVED");
        }

        return transactionRepository.save(transaction);
    }
}
