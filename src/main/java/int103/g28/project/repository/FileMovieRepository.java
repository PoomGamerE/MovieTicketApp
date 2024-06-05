package int103.g28.project.repository;

import int103.g28.project.domain.Movie;
import int103.g28.project.exception.MovieAlreadyException;
import int103.g28.project.exception.MovieNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileMovieRepository implements MovieRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Movie> movies;
    private int movieid = 0;
    private static final String DATA_FILE = "MovieRepository.dat";

    // Constructor

    public FileMovieRepository() {
        this.movies = new HashMap<>();
        loadFromFile();
    }

    // Load data from file
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            createEmptyFile(file);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            movies = (Map<String, Movie>) ois.readObject();
            movieid = ois.readInt();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found, starting with an empty repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(movies);
            oos.writeInt(movieid);
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
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            System.out.println("Please move the location of the file to another path");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An error occurred while creating the file.");
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
        saveToFile();
        return String.valueOf(movieid);
    }

    @Override
    public void add(Movie movie) {
        if (check(movie)) {
            throw new MovieAlreadyException("Movie already exists!");
        }
        movies.put(movie.getMovieid(), movie);
        saveToFile();
    }

    @Override
    public void remove(Movie movie) {
        if (check(movie)) {
            movies.remove(movie.getMovieid());
            saveToFile();
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
            saveToFile();
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    @Override
    public Map<String, Movie> getMovies() {
        return movies;
    }
}