package int103.g28.project.domain;

import java.io.Serializable;
import java.util.Map;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ticketid;
    private Showtime showtime;
    private Seat seats;

    // Constructor

    public Ticket(String ticketid, Showtime showtime, Seat seats) {
        this.ticketid = ticketid;
        this.showtime = showtime;
        this.seats = seats;
    }

    //getters and setters

    public String getTicketid() {
        return ticketid;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeats() {
        return seats;
    }

    public void setSeats(Seat seats) {
        this.seats = seats;
    }
}
