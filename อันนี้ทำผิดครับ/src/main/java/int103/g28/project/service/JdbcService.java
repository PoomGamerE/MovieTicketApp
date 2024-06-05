package int103.g28.project.service;

import int103.g28.project.object.Movie;
import int103.g28.project.object.Seat;
import int103.g28.project.object.Showtime;
import int103.g28.project.object.Ticket;
import int103.g28.project.repository.MovieRepository;
import int103.g28.project.repository.ShowtimeRepository;
import int103.g28.project.repository.TicketRepository;

import java.io.*;
import java.sql.*;
import java.util.Map;

public class JdbcService implements Service {
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;
    private TicketRepository ticketRepository;

    private int movieid = 0;
    private int showtimeid = 0;
    private int ticketid = 0;

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://int103g28-int103g28.f.aivencloud.com:12917/int103";
    private static final String JDBC_USER = "avnadmin";
    private static final String JDBC_PASSWORD = "AVNS_xZ6CQNAfHG1K7lPMpvi";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public JdbcService() {
        movieRepository = new MovieRepository();
        showtimeRepository = new ShowtimeRepository();
        ticketRepository = new TicketRepository();
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadFromDB();
    }

    static {
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // Load data from database
    private void loadFromDB() {
        try (Connection conn = connect()) {
            // Check if tables not exist
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS movies (movieid VARCHAR(10) PRIMARY KEY, title VARCHAR(100), duration VARCHAR(10), genre VARCHAR(50), subtitle VARCHAR(100))");
                stmt.execute("CREATE TABLE IF NOT EXISTS showtimes (showtimeid VARCHAR(10) PRIMARY KEY, theater VARCHAR(50), movie BLOB, language VARCHAR(20), time VARCHAR(20), seats BLOB)");
                stmt.execute("CREATE TABLE IF NOT EXISTS tickets (ticketid VARCHAR(10) PRIMARY KEY, showtime BLOB, seats BLOB)");
            }

            // Load movies
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {
                while (rs.next()) {
                    Movie movie = new Movie(rs.getString("movieid"), rs.getString("title"), rs.getString("duration"), rs.getString("genre"), rs.getString("subtitle"));
                    movieRepository.add(movie);
                    movieid = Integer.parseInt(rs.getString("movieid"));
                }
            }

            // Load showtimes
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM showtimes")) {
                while (rs.next()) {
                    Movie movie = deserialize(rs.getBytes("movie"));
                    Map<String, Seat> seats = deserialize(rs.getBytes("seats"));
                    Showtime showtime = new Showtime(rs.getString("showtimeid"), rs.getString("theater"), movie, rs.getString("language"), rs.getString("time"), seats);
                    showtimeRepository.add(showtime);
                    showtimeid = Integer.parseInt(rs.getString("showtimeid"));
                }
            }

            // Load tickets
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM tickets")) {
                while (rs.next()) {
                    Showtime showtime = deserialize(rs.getBytes("showtime"));
                    Seat seat = deserialize(rs.getBytes("seats"));
                    Ticket ticket = new Ticket(rs.getString("ticketid"), showtime, seat);
                    ticketRepository.add(ticket);
                    ticketid = Integer.parseInt(rs.getString("ticketid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> T deserialize(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Save data to database
    private void saveToDB() {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            // Save movies
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO movies (movieid, title, duration, genre, subtitle) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title=VALUES(title), duration=VALUES(duration), genre=VALUES(genre), subtitle=VALUES(subtitle)")) {
                for (Movie movie : movieRepository.getMovies().values()) {
                    pstmt.setString(1, movie.getMovieid());
                    pstmt.setString(2, movie.getTitle());
                    pstmt.setString(3, movie.getDuration());
                    pstmt.setString(4, movie.getGenre());
                    pstmt.setString(5, movie.getSubtitle());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            // Save showtimes
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO showtimes (showtimeid, theater, movie, language, time, seats) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE theater=VALUES(theater), movie=VALUES(movie), language=VALUES(language), time=VALUES(time), seats=VALUES(seats)")) {
                for (Showtime showtime : showtimeRepository.getAll().values()) {
                    pstmt.setString(1, showtime.getShowtimeid());
                    pstmt.setString(2, showtime.getTheater());
                    pstmt.setBytes(3, serialize(showtime.getMovie()));
                    pstmt.setString(4, showtime.getLanguage());
                    pstmt.setString(5, showtime.getTime());
                    pstmt.setBytes(6, serialize(showtime.getSeats()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            // Save tickets
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO tickets (ticketid, showtime, seats) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE showtime=VALUES(showtime), seats=VALUES(seats)")) {
                for (Ticket ticket : ticketRepository.getTickets().values()) {
                    pstmt.setString(1, ticket.getTicketid());
                    pstmt.setBytes(2, serialize(ticket.getShowtime()));
                    pstmt.setBytes(3, serialize(ticket.getSeats()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private byte[] serialize(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    public String movienextId() {
        movieid++;
        saveToDB();
        return String.valueOf(movieid);
    }

    @Override
    public void addMovie(Movie movie) {
        movieRepository.add(movie);
        saveToDB();
    }

    @Override
    public void removeMovie(Movie movie) {
        movieRepository.remove(movie);
        saveToDB();
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
        saveToDB();
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

    @Override
    public String showtimenextId() {
        showtimeid++;
        saveToDB();
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
        saveToDB();
    }

    @Override
    public void removeShowtime(Showtime showtime) {
        showtimeRepository.remove(showtime);
        saveToDB();
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
        saveToDB();
    }

    @Override
    public void createTicket(Showtime showtime, Seat seats) {
        ticketid++;
        Ticket ticket = new Ticket(String.valueOf(ticketid), showtime, seats);
        ticketRepository.add(ticket);
        saveToDB();
    }

    @Override
    public Map<String, Ticket> getTickets() {
        return ticketRepository.getTickets();
    }
}
