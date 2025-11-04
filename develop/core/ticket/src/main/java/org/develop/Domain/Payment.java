package org.develop.Domain;

import java.time.LocalDateTime;

import org.develop.Port.Interface.PaymentRequirements;

/*
    Har tenkt på følgende logikk om vi inkluderer priser. Data
    knyttet til pris vil da i tilfelle hentes fra en pris tabell 
    i databasen:
    switch (regionChoice && durationChoice) {
        case 1, 1 -> amount = 30.0;
        case 1, 1 -> amount = 30.0;
        case 1, 1 -> amount = 30.0;
        case 2, 1 -> amount = 40.0;
        case 2, 2 -> amount = 40.0;
        case 2, 3 -> amount = 40.0;
        default -> amount = 0.0;
    }
        Går vi til dette steget tenker jeg at man har prosentvis
        reduksjon basert på forhold som pensjonist- og 
        studentrabatt
    */


public class Payment implements PaymentRequirements {
    private String paymentId;
    private String userId;
    private double amount;
    private final LocalDateTime transactionTime = LocalDateTime.now();
    private String paymentMethod;
    private boolean status = false;

    public Payment(String paymentId, String userId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Payment() {
    }

    public String getPaymentId() { 
        return paymentId; 
    }

    public String getUserId() { 
        return userId; 
    }

    public double getAmount() { 
        return amount; 
    }
    
    public LocalDateTime getTransactionTime() { 
        return transactionTime; 
    }
        
    public String getPaymentMethod() { 
        return paymentMethod; 
    }

    public boolean getStatus() { 
        return status; 
    }

    @Override
    public boolean completePayment() {
        System.out.println("Processing payment of " + amount + " using " + paymentMethod);
        return status = true;
    }
}  