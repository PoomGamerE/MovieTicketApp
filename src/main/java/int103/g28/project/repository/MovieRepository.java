package int103.g28.project.repository;

import int103.g28.project.exception.MovieAlreadyException;
import int103.g28.project.exception.MovieNotFoundException;
import int103.g28.project.object.Movie;

import java.util.HashMap;
import java.util.Map;

public class MovieRepository {
    private Map<String, Movie> movies;

    // Constructor

    public MovieRepository() {
        this.movies = new HashMap<>();
    }

    public MovieRepository(Map<String, Movie> movies) {
        this.movies = movies;
    }

    //check if movie already exists
    public boolean check(Movie movie) {
        if (movies.containsKey(movie.getMovieid())) {
            return true;
        }
        return false;
    }

    //methods

    public void add(Movie movie) {
        if (check(movie)) {
            throw new MovieAlreadyException("Movie already exists!");
        }
        movies.put(movie.getMovieid(), movie);
    }

    public void remove(Movie movie) {
        if (check(movie)) {
            movies.remove(movie.getMovieid());
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    public Movie find(String movieid) {
        if (movies.containsKey(movieid)) {
            return movies.get(movieid);
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    public void update(Movie movie) {
        if (check(movie)) {
            movies.put(movie.getMovieid(), movie);
        } else {
            throw new MovieNotFoundException("Movie not found!");
        }
    }

    public Map<String, Movie> getMovies() {
        return movies;
    }
}
