package org.develop.Adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.develop.Domain.Payment;

/*
    -For å sende nye betalingsinformasjon til ekstern database-
    Denne skal på sikt i database-delen av prosjektet, men er 
    egentlig bare inkludert for å demonstrere at data blir
    sendt ut av systemet. Den kan på sikt slettes. 
    
    Sender data i tilknytning et Payment objekt. 
*/

public class ExternallyStoredTransactionData {
    private Connection connection;

    public ExternallyStoredTransactionData(Connection connection) {
        this.connection = connection;
    }

    public void saveTransactionData(Payment payment) {
        String sql = "INSERT INTO userTransactions (paymentId, userId, amount, transactionTime, paymentMethod) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, payment.getPaymentId());
            pstmt.setString(2, payment.getUserId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setTimestamp(4, Timestamp.valueOf(payment.getTransactionTime()));            pstmt.setString(5, payment.getPaymentMethod());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving transaction data: " + e.getMessage()) ;
        }
    } 
}
