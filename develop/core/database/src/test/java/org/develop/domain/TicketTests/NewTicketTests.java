package org.develop.domain.TicketTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.develop.TicketComponent.Domain.NewTicket;
import java.time.LocalDateTime;

public class NewTicketTests {

    @Test
    @DisplayName("NewTicket constructor stores region and userId correctly")
    public void newTicket_ConstructorStoresRegionAndUserId() {
        // Arrange
        LocalDateTime endTime = LocalDateTime.now().plusDays(7);
        int region = 2;
        int userId = 5;

        // Act
        NewTicket ticket = new NewTicket(endTime, region, userId);

        // Assert
        Assertions.assertEquals(region, ticket.getRegion());
        Assertions.assertEquals(userId, ticket.getUserId());
    }

    @Test
    @DisplayName("NewTicket formats time remaining output correctly")
    public void newTicket_FormatsTimeRemainingOutput() {
        // Arrange
        LocalDateTime futureTime = LocalDateTime.now().plusDays(2);
        NewTicket ticket = new NewTicket(futureTime, 1, 1);

        // Act
        String result = ticket.timeRemaining();

        // Assert - verify output contains expected time units
        Assertions.assertTrue(result.contains("days"));
        Assertions.assertTrue(result.contains("hours"));
        Assertions.assertTrue(result.contains("minutes"));
        Assertions.assertTrue(result.contains("seconds"));
        Assertions.assertTrue(result.contains("remaining"));
    }

    @Test
    @DisplayName("NewTicket calculates remaining hours correctly")
    public void newTicket_RemainingHoursCalculation() {
        // Arrange
        LocalDateTime endTime = LocalDateTime.now().plusHours(12);
        NewTicket ticket = new NewTicket(endTime, 3, 2);

        // Act
        int remainingHours = ticket.getRemainingHours();

        // Assert
        // Advarsel: Timing-risiko - millisekunder mellom now() og getRemainingHours() kan forårsake feil.
        // I CI/CD-miljøer kan dette føre til ustabile tester, så vurder å bruke en fast tid for testing.
        Assertions.assertEquals(12, remainingHours);
    }
}
