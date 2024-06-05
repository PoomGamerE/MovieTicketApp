package int103.g28.project.service;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.repository.ShowtimeRepository;

import java.util.Map;

public class ShowtimeService {
    private ShowtimeRepository showtimeRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public boolean checkMovieInShowtime(String movieid) {
        for (Showtime showtime : showtimeRepository.getAll().values()) {
            if (showtime.getMovie().getMovieid().equals(movieid)) {
                return true;
            }
        }
        return false;
    }

    public String showtimenextId() {
        return showtimeRepository.showtimenextId();
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
}
