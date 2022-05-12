package fr.sii.rxjava.data;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Comparator.comparing;

public class Actor {
    public enum Sex {m, f}

    public static Comparator<Actor> byNameThenFirstName = comparing(Actor::name).thenComparing(Actor::fname);

    public final String fname;
    public final String name;
    public final Sex sex;

    private Actor(String fname, String name, Sex sex) {
        this.fname = checkNotNull(fname);
        this.name = checkNotNull(name);
        this.sex = checkNotNull(sex);
    }

    @Contract(pure = true)
    public static Actor actor(String firstName, String name) {return actor(firstName, name, Sex.m);}

    @Contract(pure = true)
    public static Actor actor(String firstName, String name, Sex sex) {return new Actor(firstName, name, sex);}

    public String fname() {return this.fname;}

    public String name() {return this.name;}

    public Sex sex() { return sex; }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Actor actor = (Actor) o;

        if (!fname.equals(actor.fname)) { return false; }
        if (!name.equals(actor.name)) { return false; }
        return sex == actor.sex;
    }

    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + sex.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return fname + " " + name;
    }
}
