package int103.g28.project.service;

import int103.g28.project.object.Movie;
import int103.g28.project.object.Seat;
import int103.g28.project.object.Showtime;
import int103.g28.project.object.Ticket;

import java.util.Map;

public interface Service {
    Map<String, Movie> getMovies();
    String movienextId();
    void addMovie(Movie movie);
    void removeMovie(Movie movie);
    Movie findMovies(String movieid);
    boolean findMovie(String movieid);
    void updateMovie(Movie movie);
    boolean checkMovieInShowtime(String movieid);
    boolean equalsMovie(Movie movie1, Movie movie2);

    String showtimenextId();
    Map<String, Showtime> getShowtimes();
    Map<String, Seat> getSeats(String showtimeid);
    Seat getSeat(String showtimeid, String seatid);
    int getSeatRow(String showtimeid);
    int getSeatColumn(String showtimeid);
    void addShowtime(Showtime showtime);
    void removeShowtime(Showtime showtime);
    Showtime findShowtimes(String showtimeid);
    boolean findShowtime(String showtimeid);
    void updateShowtime(Showtime showtime);

    void createTicket(Showtime showtime, Seat seats);
    Map<String, Ticket> getTickets();
}
