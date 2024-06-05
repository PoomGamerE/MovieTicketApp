package int103.g28.project.repository;

import int103.g28.project.domain.Movie;
import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.exception.ShowtimeAlreadyException;
import int103.g28.project.exception.ShowtimeNotFoundException;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcShowtimeRepository implements ShowtimeRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Showtime> showtimes;
    private int showtimeid = 0;
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://int103g28-int103g28.f.aivencloud.com:12917/int103";
    private static final String JDBC_USER = "avnadmin";
    private static final String JDBC_PASSWORD = "AVNS_xZ6CQNAfHG1K7lPMpvi";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Constructor

    public JdbcShowtimeRepository() {
        this.showtimes = new HashMap<>();
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadFromDB();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // Load data from database
    private void loadFromDB() {
        try (Connection conn = connect()) {
            // Check if tables not exist
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS showtimes (showtimeid VARCHAR(10) PRIMARY KEY, theater VARCHAR(50), movie BLOB, language VARCHAR(20), time VARCHAR(20), seats BLOB)");
            }

            // Load showtimes
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM showtimes")) {
                while (rs.next()) {
                    Movie movie = deserialize(rs.getBytes("movie"));
                    Map<String, Seat> seats = deserialize(rs.getBytes("seats"));
                    Showtime showtime = new Showtime(rs.getString("showtimeid"), rs.getString("theater"), movie, rs.getString("language"), rs.getString("time"), seats);
                    add(showtime);
                    showtimeid = Integer.parseInt(rs.getString("showtimeid"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save data to database
    private void saveToDB() {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            // Save showtimes
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO showtimes (showtimeid, theater, movie, language, time, seats) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE theater=VALUES(theater), movie=VALUES(movie), language=VALUES(language), time=VALUES(time), seats=VALUES(seats)")) {
                for (Showtime showtime : getAll().values()) {
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

    private <T> T deserialize(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Check if showtime already exists
    @Override
    public boolean check(Showtime showtime) {
        return showtimes.containsKey(showtime.getShowtimeid());
    }

    // Methods
    @Override
    public String showtimenextId() {
        showtimeid++;
        saveToDB();
        return String.valueOf(showtimeid);
    }

    @Override
    public void add(Showtime showtime) {
        if (check(showtime)) {
            throw new ShowtimeAlreadyException("Showtime already exists!");
        }
        showtimes.put(showtime.getShowtimeid(), showtime);
        saveToDB();
    }

    @Override
    public void remove(Showtime showtime) {
        if (check(showtime)) {
            showtimes.remove(showtime.getShowtimeid());
            saveToDB();
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
            saveToDB();
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
        saveToDB();
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
