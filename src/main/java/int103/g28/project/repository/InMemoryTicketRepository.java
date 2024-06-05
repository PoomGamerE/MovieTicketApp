package int103.g28.project.repository;

import int103.g28.project.domain.Ticket;
import int103.g28.project.exception.TicketAlreadyException;
import int103.g28.project.exception.TicketNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTicketRepository implements TicketRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Ticket> tickets;
    private int ticketid = 0;

    // Constructor

    public InMemoryTicketRepository() {
        this.tickets = new HashMap<>();
    }

    public InMemoryTicketRepository(Map<String, Ticket> tickets) {
        this.tickets = tickets;
    }

    //check if ticket already exists
    @Override
    public boolean check(Ticket ticket) {
        if (tickets.containsKey(ticket.getTicketid())) {
            return true;
        }
        return false;
    }

    //methods
    @Override
    public String ticketnextId() {
        ticketid++;
        return String.valueOf(ticketid);
    }

    @Override
    public void add(Ticket ticket) {
        if (check(ticket)) {
            throw new TicketAlreadyException("Ticket already exists!");
        }
        tickets.put(ticket.getTicketid(), ticket);
    }

    @Override
    public void remove(Ticket ticket) {
        if (check(ticket)) {
            tickets.remove(ticket.getTicketid());
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public Ticket find(String ticketid) {
        if (tickets.containsKey(ticketid)) {
            return tickets.get(ticketid);
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public void update(Ticket ticket) {
        if (check(ticket)) {
            tickets.put(ticket.getTicketid(), ticket);
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public Map<String, Ticket> getTickets() {
        return tickets;
    }
}
