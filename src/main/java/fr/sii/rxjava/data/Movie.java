package fr.sii.rxjava.data;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Comparator.comparing;

public class Movie {
    public static final Comparator<Movie> CHRONO = comparing(Movie::year);

    public final String title;
    public final int year;

    private Movie(String title, int year) {
        this.title = checkNotNull(title);
        this.year = year;
    }

    @Contract(pure = true)
    public static Movie movie(String title, int year) {return new Movie(title, year);}

    public String title() {return this.title;}

    public int year() {return this.year;}

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Movie movie = (Movie) o;

        if (year != movie.year) { return false; }
        return title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() { return title + " (" + year + ")"; }
}
