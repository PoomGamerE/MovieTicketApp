package int103.g28.project.object;

import java.io.Serializable;
import java.util.Map;

public class Showtime implements Serializable {
    private static final long serialVersionUID = 1L;
    private String showtimeid;
    private String theater;
    private Movie movie;
    private String language;
    private String time;
    private Map<String, Seat> seats;

    // Constructor

    public Showtime(String showtimeid, String theater, Movie movie, String language, String time, Map<String, Seat> seats) {
        this.showtimeid = showtimeid;
        this.theater = theater;
        this.movie = movie;
        this.language = language;
        this.time = time;
        this.seats = seats;
    }

    //getters and setters

    public String getShowtimeid() {
        return showtimeid;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }

    public void setSeats(Map<String, Seat> seats) {
        this.seats = seats;
    }

    public int seatsAmount() {
        return seats.size();
    }
}

