package com.fraud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String accountId;
    private double amount;
    private String currency;
    private String merchantCategory;
    private String location;
    
    @Builder.Default
    private String riskStatus = "PENDING"; // PENDING, APPROVED, FLAGGED, BLOCKED
    
    @Builder.Default
    private double riskScore = 0.0;

    private LocalDateTime timestamp;
}
