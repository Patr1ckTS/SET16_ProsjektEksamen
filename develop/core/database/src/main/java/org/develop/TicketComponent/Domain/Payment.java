package org.develop.TicketComponent.Domain;

import java.time.LocalDateTime;

import org.develop.TicketComponent.Port.Interface.PaymentRequirements;

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