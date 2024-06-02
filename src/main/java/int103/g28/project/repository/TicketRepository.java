package int103.g28.project.repository;

import int103.g28.project.exception.TicketAlreadyException;
import int103.g28.project.exception.TicketNotFoundException;
import int103.g28.project.object.Ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketRepository {
    private Map<String, Ticket> tickets;

    // Constructor

    public TicketRepository() {
        this.tickets = new HashMap<>();
    }

    public TicketRepository(Map<String, Ticket> tickets) {
        this.tickets = tickets;
    }

    //check if ticket already exists
    public boolean check(Ticket ticket) {
        if (tickets.containsKey(ticket.getTicketid())) {
            return true;
        }
        return false;
    }

    //methods
    
    public void add(Ticket ticket) {
        if (check(ticket)) {
            throw new TicketAlreadyException("Ticket already exists!");
        }
        tickets.put(ticket.getTicketid(), ticket);
    }

    public void remove(Ticket ticket) {
        if (check(ticket)) {
            tickets.remove(ticket.getTicketid());
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    public Ticket find(String ticketid) {
        if (tickets.containsKey(ticketid)) {
            return tickets.get(ticketid);
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    public void update(Ticket ticket) {
        if (check(ticket)) {
            tickets.put(ticket.getTicketid(), ticket);
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    public Map<String, Ticket> getTickets() {
        return tickets;
    }
}
