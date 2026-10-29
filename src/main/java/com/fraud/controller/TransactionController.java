package com.fraud.controller;

import com.fraud.model.Transaction;
import com.fraud.service.FraudEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private FraudEvaluationService fraudEvaluationService;

    @PostMapping("/evaluate")
    public ResponseEntity<Transaction> evaluateTransaction(@RequestBody Transaction transaction) {
        Transaction evaluated = fraudEvaluationService.evaluateTransaction(transaction);
        return ResponseEntity.ok(evaluated);
    }
}
