package org.develop.domain.TicketTests;

import java.time.LocalDateTime;

import org.develop.TicketComponent.Service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketServiceTests {

    @Test
    @DisplayName("TicketService returns correct region for kommune choice")
    public void ticketService_ReturnsCorrectRegionForKommuneChoice() {
        // Arrange
        TicketService ticketService = new TicketService();
        int kommuneChoice = 1;

        // Act
        String result = ticketService.ticketRegion(kommuneChoice);

        // Assert
        Assertions.assertEquals("By", result);
    }

    @Test
    @DisplayName("TicketService returns correct region for fylke choice")
    public void ticketService_ReturnsCorrectRegionForFylkeChoice() {
        // Arrange
        TicketService ticketService = new TicketService();
        int fylkeChoice = 2;

        // Act
        String result = ticketService.ticketRegion(fylkeChoice);

        // Assert
        Assertions.assertEquals("Fylke", result);
    }

    @Test
    @DisplayName("TicketService throws exception for invalid region choice")
    public void ticketService_ThrowsExceptionForInvalidRegionChoice() {
        // Arrange
        TicketService ticketService = new TicketService();
        int invalidChoice = 99;

        // Act & Assert
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ticketService.ticketRegion(invalidChoice);
        });
        
        Assertions.assertEquals("Ugyldig region valgt.", exception.getMessage());
    }

    @Test
    @DisplayName("TicketService calculates correct duration for engangsbillett")
    public void ticketService_CalculatesCorrectDurationForEngangsbillett() {
        // Arrange
        TicketService ticketService = new TicketService();
        LocalDateTime beforeCall = LocalDateTime.now();
        int engangsbillettChoice = 1;

        // Act
        LocalDateTime result = ticketService.ticketDuration(engangsbillettChoice);

        // Assert
        LocalDateTime expectedMinTime = beforeCall.plusHours(2);
        LocalDateTime expectedMaxTime = LocalDateTime.now().plusHours(2).plusMinutes(1);
        
        Assertions.assertTrue(result.isAfter(expectedMinTime.minusMinutes(1)));
        Assertions.assertTrue(result.isBefore(expectedMaxTime));
    }

    @Test
    @DisplayName("TicketService calculates correct duration for dagsbillett")
    public void ticketService_CalculatesCorrectDurationForDagsbillett() {
        // Arrange
        TicketService ticketService = new TicketService();
        LocalDateTime beforeCall = LocalDateTime.now();
        int dagsbillettChoice = 2;

        // Act
        LocalDateTime result = ticketService.ticketDuration(dagsbillettChoice);

        // Assert
        LocalDateTime expectedMinTime = beforeCall.plusDays(1);
        LocalDateTime expectedMaxTime = LocalDateTime.now().plusDays(1).plusMinutes(1);
        
        Assertions.assertTrue(result.isAfter(expectedMinTime.minusMinutes(1)));
        Assertions.assertTrue(result.isBefore(expectedMaxTime));
    }

    @Test
    @DisplayName("TicketService calculates correct duration for ukesbillett")
    public void ticketService_CalculatesCorrectDurationForUkesbillett() {
        // Arrange
        TicketService ticketService = new TicketService();
        LocalDateTime beforeCall = LocalDateTime.now();
        int ukesbillettChoice = 3;

        // Act
        LocalDateTime result = ticketService.ticketDuration(ukesbillettChoice);

        // Assert
        LocalDateTime expectedMinTime = beforeCall.plusDays(7);
        LocalDateTime expectedMaxTime = LocalDateTime.now().plusDays(7).plusMinutes(1);
        
        Assertions.assertTrue(result.isAfter(expectedMinTime.minusMinutes(1)));
        Assertions.assertTrue(result.isBefore(expectedMaxTime));
    }
}