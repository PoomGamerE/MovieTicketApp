package int103.g28.project.service;

import int103.g28.project.object.Movie;
import int103.g28.project.object.Seat;
import int103.g28.project.object.Showtime;
import int103.g28.project.object.Ticket;
import int103.g28.project.repository.MovieRepository;
import int103.g28.project.repository.ShowtimeRepository;
import int103.g28.project.repository.TicketRepository;

import java.io.*;
import java.util.Map;

public class FileService implements Service {
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;
    private TicketRepository ticketRepository;

    private int movieid = 0;
    private int showtimeid = 0;
    private int ticketid = 0;

    private static final String DATA_FILE = "data.dat";

    public FileService() {
        movieRepository = new MovieRepository();
        showtimeRepository = new ShowtimeRepository();
        ticketRepository = new TicketRepository();
        loadFromFile();
    }

    // Load data from file
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            createEmptyFile(file);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            movieRepository = (MovieRepository) ois.readObject();
            showtimeRepository = (ShowtimeRepository) ois.readObject();
            ticketRepository = (TicketRepository) ois.readObject();
            movieid = ois.readInt();
            showtimeid = ois.readInt();
            ticketid = ois.readInt();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found, starting with an empty repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(movieRepository);
            oos.writeObject(showtimeRepository);
            oos.writeObject(ticketRepository);
            oos.writeInt(movieid);
            oos.writeInt(showtimeid);
            oos.writeInt(ticketid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create an empty file if it does not exist
    private void createEmptyFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                saveToFile();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    // Movie methods
    @Override
    public Map<String, Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    public String movienextId() {
        movieid++;
        saveToFile();
        return String.valueOf(movieid);
    }

    @Override
    public void addMovie(Movie movie) {
        movieRepository.add(movie);
        saveToFile();
    }

    @Override
    public void removeMovie(Movie movie) {
        movieRepository.remove(movie);
        saveToFile();
    }

    @Override
    public Movie findMovies(String movieid) {
        return movieRepository.find(movieid);
    }

    @Override
    public boolean findMovie(String movieid) {
        try {
            movieRepository.find(movieid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.update(movie);
        saveToFile();
    }

    @Override
    public boolean checkMovieInShowtime(String movieid) {
        for (Showtime showtime : showtimeRepository.getAll().values()) {
            if (showtime.getMovie().getMovieid().equals(movieid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equalsMovie(Movie movie1, Movie movie2) {
        return movie1.getMovieid().equals(movie2.getMovieid());
    }

    // Showtime methods
    @Override
    public String showtimenextId() {
        showtimeid++;
        saveToFile();
        return String.valueOf(showtimeid);
    }

    @Override
    public Map<String, Showtime> getShowtimes() {
        return showtimeRepository.getAll();
    }

    @Override
    public Map<String, Seat> getSeats(String showtimeid) {
        return showtimeRepository.getSeats(showtimeid);
    }

    @Override
    public Seat getSeat(String showtimeid, String seatid) {
        return showtimeRepository.getSeat(showtimeid, seatid);
    }

    @Override
    public int getSeatRow(String showtimeid) {
        return showtimeRepository.find(showtimeid).getSeats().get("A1").getRowofallseat();
    }

    @Override
    public int getSeatColumn(String showtimeid) {
        return showtimeRepository.find(showtimeid).getSeats().get("A1").getColumnofallseat();
    }

    @Override
    public void addShowtime(Showtime showtime) {
        showtimeRepository.add(showtime);
        saveToFile();
    }

    @Override
    public void removeShowtime(Showtime showtime) {
        showtimeRepository.remove(showtime);
        saveToFile();
    }

    @Override
    public Showtime findShowtimes(String showtimeid) {
        return showtimeRepository.find(showtimeid);
    }

    @Override
    public boolean findShowtime(String showtimeid) {
        try {
            showtimeRepository.find(showtimeid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updateShowtime(Showtime showtime) {
        showtimeRepository.update(showtime);
        saveToFile();
    }

    // Ticket methods
    @Override
    public void createTicket(Showtime showtime, Seat seats) {
        ticketid++;
        Ticket ticket = new Ticket(String.valueOf(ticketid), showtime, seats);
        ticketRepository.add(ticket);
        saveToFile();
    }

    @Override
    public Map<String, Ticket> getTickets() {
        return ticketRepository.getTickets();
    }
}
