package int103.g28.project.repository;

import int103.g28.project.domain.Movie;
import int103.g28.project.exception.MovieAlreadyException;
import int103.g28.project.exception.MovieNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InMemoryMovieRepository implements MovieRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Movie> movies;
    private int movieid = 0;

    // Constructor

    public InMemoryMovieRepository() {
        this.movies = new HashMap<>();
    }

    public InMemoryMovieRepository(Map<String, Movie> movies) {
        this.movies = movies;
    }

    //check if movie already exists
    @Override
    public boolean check(Movie movie) {
        if (movies.containsKey(movie.getMovieid())) {
            return true;
        }
        return false;
    }

    //methods
    @Override
    public String movienextId() {
        movieid++;
        return String.valueOf(movieid);
    }

    @Override
    public void add(Movie movie) {
        if (check(movie)) {
            throw new MovieAlreadyException("Movie already exists!");
        }
        movies.put(movie.getMovieid(), movie);
    }

    @Override
    public void remove(Movie movie) {
        if (check(movie)) {
            movies.remove(movie.getMovieid());
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
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    @Override
    public Map<String, Movie> getMovies() {
        return movies;
    }
}
