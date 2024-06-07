package int103.g28.project.repository;

import int103.g28.project.domain.Ticket;

import java.util.Map;

public interface TicketRepository {
    boolean check(Ticket ticket);

    String ticketnextId();

    void add(Ticket ticket);

    void remove(Ticket ticket);

    Ticket find(String ticketid);

    void update(Ticket ticket);

    Map<String, Ticket> getTickets();
}
