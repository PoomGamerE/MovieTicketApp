package int103.g28.project.repository;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;

import java.util.Map;

public interface ShowtimeRepository {
    boolean check(Showtime showtime);

    String showtimenextId();

    void add(Showtime showtime);

    void remove(Showtime showtime);

    Showtime find(String showtimeid);

    void update(Showtime showtime);

    Map<String, Showtime> getAll();

    void setAll(Map<String, Showtime> showtimes);

    Map<String, Seat> getSeats(String showtimeid);

    Seat getSeat(String showtimeid, String seatid);
}
