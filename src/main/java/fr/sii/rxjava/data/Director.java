package fr.sii.rxjava.data;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Comparator.comparing;

public class Director {

    public static final Comparator<Director> directorbyNameThenFirstName = comparing(Director::name).thenComparing(Director::fname);

    public final String fname;
    public final String name;

    private Director(String fname, String name) {
        this.fname = checkNotNull(fname);
        this.name = checkNotNull(name);
    }

    @Contract(pure = true)
    public static Director director(String firstName, String name) {return new Director(firstName, name);}

    public String fname() {return this.fname;}

    public String name() {return this.name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Director director = (Director) o;

        if (!fname.equals(director.fname)) { return false; }
        return name.equals(director.name);
    }

    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() { return fname + " " + name; }
}
