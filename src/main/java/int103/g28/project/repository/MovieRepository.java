package int103.g28.project.repository;

import int103.g28.project.domain.Movie;

import java.util.Map;

public interface MovieRepository {
    boolean check(Movie movie);

    String movienextId();

    void add(Movie movie);

    void remove(Movie movie);

    Movie find(String movieid);

    void update(Movie movie);

    Map<String, Movie> getMovies();
}
