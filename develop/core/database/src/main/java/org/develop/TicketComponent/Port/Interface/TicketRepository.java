package org.develop.TicketComponent.Port.Interface;

import org.develop.TicketComponent.Domain.NewTicket;

public interface TicketRepository {
    void saveTicket(NewTicket ticket);
}
