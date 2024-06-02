package int103.g28.project.service;

import int103.g28.project.object.Movie;
import int103.g28.project.object.Seat;
import int103.g28.project.object.Showtime;
import int103.g28.project.object.Ticket;
import int103.g28.project.repository.MovieRepository;
import int103.g28.project.repository.ShowtimeRepository;
import int103.g28.project.repository.TicketRepository;

import java.util.Map;

public class InMemoryService {
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;
    private TicketRepository ticketRepository;

    private int movieid = 0;
    private int showtimeid = 0;
    private int ticketid = 0;

    // Constructor
    public InMemoryService() {
        movieRepository = new MovieRepository();
        showtimeRepository = new ShowtimeRepository();
        ticketRepository = new TicketRepository();
    }

    //Movie

    public Map<String, Movie> getMovies() {
        return movieRepository.getMovies();
    }

    public String movienextId() {
        movieid++;
        return String.valueOf(movieid);
    }

    public void addMovie(Movie movie) {
        movieRepository.add(movie);
    }

    public void removeMovie(Movie movie) {
        //if movie use in showtime then throw exception
        movieRepository.remove(movie);
    }

    public Movie findMovies(String movieid) {
        return movieRepository.find(movieid);
    }

    public boolean findMovie(String movieid) {
        try {
            movieRepository.find(movieid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateMovie(Movie movie) {
        movieRepository.update(movie);
    }

    public boolean checkMovieInShowtime(String movieid) {
        for (Showtime showtime : showtimeRepository.getAll().values()) {
            if (showtime.getMovie().getMovieid().equals(movieid)) {
                return true;
            }
        }
        return false;
    }

    public boolean equalsMovie(Movie movie1, Movie movie2) {
        if (movie1.getMovieid().equals(movie2.getMovieid())) {
            return true;
        }
        return false;
    }

    //Showtime

    public String showtimenextId() {
        showtimeid++;
        return String.valueOf(showtimeid);
    }

    public Map<String, Showtime> getShowtimes() {
        return showtimeRepository.getAll();
    }

    public Map<String, Seat> getSeats(String showtimeid) {
        return showtimeRepository.getSeats(showtimeid);
    }

    public Seat getSeat(String showtimeid, String seatid) {
        return showtimeRepository.getSeat(showtimeid, seatid);
    }

    public int getSeatRow(String showtimeid) {
        return showtimeRepository.find(showtimeid).getSeats().get("A1").getRowofallseat();
    }

    public int getSeatColumn(String showtimeid) {
        return showtimeRepository.find(showtimeid).getSeats().get("A1").getColumnofallseat();
    }

    public void addShowtime(Showtime showtime) {
        showtimeRepository.add(showtime);
    }

    public void removeShowtime(Showtime showtime) {
        showtimeRepository.remove(showtime);
    }

    public Showtime findShowtimes(String showtimeid) {
        return showtimeRepository.find(showtimeid);
    }

    public boolean findShowtime(String showtimeid) {
        try {
            showtimeRepository.find(showtimeid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateShowtime(Showtime showtime) {
        showtimeRepository.update(showtime);
    }

    //Ticket

    public void createTicket(Showtime showtime, Seat seats) {
        ticketid++;
        Ticket ticket = new Ticket(String.valueOf(ticketid), showtime, seats);
        ticketRepository.add(ticket);
    }

    public Map<String, Ticket> getTickets() {
        return ticketRepository.getTickets();
    }
}
