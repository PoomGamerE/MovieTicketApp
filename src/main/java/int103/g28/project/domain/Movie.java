package int103.g28.project.domain;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    private String movieid;
    private String title;
    private String duration;
    private String genre;
    private String subtitle;

    // Constructor

    public Movie(String movieid, String title, String duration, String genre, String subtitle) {
        this.movieid = movieid;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.subtitle = subtitle;
    }

    //getters and setters

    public String getMovieid() {
        return movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    //equals and tostring

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(movieid, movie.movieid) && Objects.equals(title, movie.title) && Objects.equals(duration, movie.duration) && Objects.equals(genre, movie.genre) && Objects.equals(subtitle, movie.subtitle);
    }

    @Override
    public String toString() {
        return "Movie{" + "movieid=" + movieid + ", title=" + title + ", duration=" + duration + ", genre=" + genre + ", subtitle=" + subtitle + '}';
    }

}