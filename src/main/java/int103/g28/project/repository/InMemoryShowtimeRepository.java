package int103.g28.project.repository;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.exception.ShowtimeAlreadyException;
import int103.g28.project.exception.ShowtimeNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InMemoryShowtimeRepository implements ShowtimeRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Showtime> showtimes;
    private int showtimeid = 0;

    // Constructor

    public InMemoryShowtimeRepository() {
        this.showtimes = new HashMap<>();
    }

    public InMemoryShowtimeRepository(Map<String, Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    //check if showtime already exists
    @Override
    public boolean check(Showtime showtime) {
        if (showtimes.containsKey(showtime.getShowtimeid())) {
            return true;
        }
        return false;
    }

    //methods
    @Override
    public String showtimenextId() {
        showtimeid++;
        return String.valueOf(showtimeid);
    }

    @Override
    public void add(Showtime showtime) {
        if (check(showtime)) {
            throw new ShowtimeAlreadyException("Showtime already exists!");
        }
        showtimes.put(showtime.getShowtimeid(), showtime);
    }

    @Override
    public void remove(Showtime showtime) {
        if (check(showtime)) {
            showtimes.remove(showtime.getShowtimeid());
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public Showtime find(String showtimeid) {
        if (showtimes.containsKey(showtimeid)) {
            return showtimes.get(showtimeid);
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public void update(Showtime showtime) {
        if (check(showtime)) {
            showtimes.put(showtime.getShowtimeid(), showtime);
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public Map<String, Showtime> getAll() {
        return showtimes;
    }

    @Override
    public void setAll(Map<String, Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    @Override
    public Map<String, Seat> getSeats(String showtimeid) {
        return showtimes.get(showtimeid).getSeats();
    }

    @Override
    public Seat getSeat(String showtimeid, String seatid) {
        return showtimes.get(showtimeid).getSeats().get(seatid);
    }
}
