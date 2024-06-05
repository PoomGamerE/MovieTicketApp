package int103.g28.project.service;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.domain.Ticket;
import int103.g28.project.repository.TicketRepository;

import java.util.Map;

public class TicketService {
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void createTicket(Showtime showtime, Seat seats) {
        Ticket ticket = new Ticket(String.valueOf(ticketRepository.ticketnextId()), showtime, seats);
        ticketRepository.add(ticket);
    }

    public Map<String, Ticket> getTickets() {
        return ticketRepository.getTickets();
    }
}
