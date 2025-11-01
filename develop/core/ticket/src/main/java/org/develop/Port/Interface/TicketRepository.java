package org.develop.Port.Interface;

import org.develop.Domain.NewTicket;

public interface TicketRepository {
    void saveTicket(NewTicket ticket);
}
