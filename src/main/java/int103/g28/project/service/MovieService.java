package int103.g28.project.service;

import int103.g28.project.domain.Movie;
import int103.g28.project.repository.MovieRepository;

import java.util.Map;

public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public String movienextId() {
        return movieRepository.movienextId();
    }

    public Map<String, Movie> getMovies() {
        return movieRepository.getMovies();
    }

    public void addMovie(Movie movie) {
        movieRepository.add(movie);
    }

    public void removeMovie(Movie movie) {
        //if movie use in showtime then throw exception
        movieRepository.remove(movie);
    }

    public Movie findMovies(String movieid) {
        return movieRepository.find(movieid);
    }

    public boolean findMovie(String movieid) {
        try {
            movieRepository.find(movieid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateMovie(Movie movie) {
        movieRepository.update(movie);
    }

    public boolean equalsMovie(Movie movie1, Movie movie2) {
        if (movie1.getMovieid().equals(movie2.getMovieid())) {
            return true;
        }
        return false;
    }
}
