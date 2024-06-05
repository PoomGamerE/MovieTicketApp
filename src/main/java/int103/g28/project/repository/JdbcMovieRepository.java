package int103.g28.project.repository;

import int103.g28.project.domain.Movie;
import int103.g28.project.exception.MovieAlreadyException;
import int103.g28.project.exception.MovieNotFoundException;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcMovieRepository implements MovieRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Movie> movies;
    private int movieid = 0;
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://int103g28-int103g28.f.aivencloud.com:12917/int103";
    private static final String JDBC_USER = "avnadmin";
    private static final String JDBC_PASSWORD = "AVNS_xZ6CQNAfHG1K7lPMpvi";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Constructor

    public JdbcMovieRepository() {
        this.movies = new HashMap<>();
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
                stmt.execute("CREATE TABLE IF NOT EXISTS movies (movieid VARCHAR(10) PRIMARY KEY, title VARCHAR(100), duration VARCHAR(10), genre VARCHAR(50), subtitle VARCHAR(100))");
            }

            // Load movies
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {
                while (rs.next()) {
                    Movie movie = new Movie(rs.getString("movieid"), rs.getString("title"), rs.getString("duration"), rs.getString("genre"), rs.getString("subtitle"));
                    add(movie);
                    movieid = Integer.parseInt(rs.getString("movieid"));
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

            // Save movies
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO movies (movieid, title, duration, genre, subtitle) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title=VALUES(title), duration=VALUES(duration), genre=VALUES(genre), subtitle=VALUES(subtitle)")) {
                for (Movie movie : getMovies().values()) {
                    pstmt.setString(1, movie.getMovieid());
                    pstmt.setString(2, movie.getTitle());
                    pstmt.setString(3, movie.getDuration());
                    pstmt.setString(4, movie.getGenre());
                    pstmt.setString(5, movie.getSubtitle());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if movie already exists
    @Override
    public boolean check(Movie movie) {
        return movies.containsKey(movie.getMovieid());
    }

    // Methods
    @Override
    public String movienextId() {
        movieid++;
        saveToDB();
        return String.valueOf(movieid);
    }

    @Override
    public void add(Movie movie) {
        if (check(movie)) {
            throw new MovieAlreadyException("Movie already exists!");
        }
        movies.put(movie.getMovieid(), movie);
        saveToDB();
    }

    @Override
    public void remove(Movie movie) {
        if (check(movie)) {
            movies.remove(movie.getMovieid());
            saveToDB();
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    @Override
    public Movie find(String movieid) {
        if (movies.containsKey(movieid)) {
            return movies.get(movieid);
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    @Override
    public void update(Movie movie) {
        if (check(movie)) {
            movies.put(movie.getMovieid(), movie);
            saveToDB();
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    @Override
    public Map<String, Movie> getMovies() {
        return movies;
    }
}