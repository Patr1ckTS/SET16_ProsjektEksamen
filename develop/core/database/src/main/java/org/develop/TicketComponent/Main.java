package org.develop.TicketComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.develop.TicketComponent.Adapter.SaveTicketToDatabase;
import org.develop.TicketComponent.Domain.NewTicket;
import org.develop.TicketComponent.Domain.Payment;
import org.develop.TicketComponent.Service.TicketInputHandler;
import org.develop.TicketComponent.Service.TicketService;

/*
    Tanker for billett implementasjon:
    - Bruker velger billettvarighet og region via UI (dropdown meny el.)
    - Basert på valg, opprettes en ny billett med start- og sluttider
    - Billett lagres i database knyttet til brukerId
    - Ved behov, hentes billettinfo fra database for validering og visning
*/

public class Main {
    public static void main(String[] args) {
//     Midlertidig tilføyd kode for å teste Ticket klassen:
        String url = "jdbc:mysql://itstud.hiof.no:3306/se25_G16";
        String user = "gruppe16";
        String password = "Summer35";
        NewTicket newTicket = null;
        Payment payment = new Payment();

// Dette flyttes eventuelt til database-delen eller main:
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            SaveTicketToDatabase ticketDatabase = new SaveTicketToDatabase(connection);
            int userId = 1;

            System.out.println("Connected to database successfully!\n");

// Her legges eventuell betalingslogikk inn på sikt:
            payment.completePayment();

            if (payment.getStatus() == true) {
                try (Scanner scanner = new Scanner(System.in)) {

                    TicketService ticketService = new TicketService();

                    int durationChoice = TicketInputHandler.ticketDurationChoice(scanner);
                    int regionChoice = TicketInputHandler.ticketRegionChoice(scanner);

                    newTicket = new NewTicket(
                        ticketService.ticketDuration(durationChoice),
                        regionChoice,
                        userId
                    );

                    ticketDatabase.saveTicket(newTicket);

                    newTicket.getTicketInformation();
                    
                    System.out.print(newTicket.timeRemaining());
                }
            } 
            else {
                System.out.println("Payment failed!\n");
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
        }
    }
}