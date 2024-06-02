package int103.g28.project.repository;

import int103.g28.project.exception.ShowtimeAlreadyException;
import int103.g28.project.exception.ShowtimeNotFoundException;
import int103.g28.project.object.Seat;
import int103.g28.project.object.Showtime;

import java.util.HashMap;
import java.util.Map;

public class ShowtimeRepository {
    private Map<String, Showtime> showtimes;

    // Constructor

    public ShowtimeRepository() {
        this.showtimes = new HashMap<>();
    }

    public ShowtimeRepository(Map<String, Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    //check if showtime already exists
    public boolean check(Showtime showtime) {
        if (showtimes.containsKey(showtime.getShowtimeid())) {
            return true;
        }
        return false;
    }

    //methods

    public void add(Showtime showtime) {
        if (check(showtime)) {
            throw new ShowtimeAlreadyException("Showtime already exists!");
        }
        showtimes.put(showtime.getShowtimeid(), showtime);
    }

    public void remove(Showtime showtime) {
        if (check(showtime)) {
            showtimes.remove(showtime.getShowtimeid());
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    public Showtime find(String showtimeid) {
        if (showtimes.containsKey(showtimeid)) {
            return showtimes.get(showtimeid);
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    public void update(Showtime showtime) {
        if (check(showtime)) {
            showtimes.put(showtime.getShowtimeid(), showtime);
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    public Map<String, Showtime> getAll() {
        return showtimes;
    }

    public void setAll(Map<String, Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public Map<String, Seat> getSeats(String showtimeid) {
        return showtimes.get(showtimeid).getSeats();
    }

    public Seat getSeat(String showtimeid, String seatid) {
        return showtimes.get(showtimeid).getSeats().get(seatid);
    }
}
