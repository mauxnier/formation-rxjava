package fr.sii.rxjava.util;

import org.jetbrains.annotations.Contract;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.sqrt;

public final class Pt {
    public final int x;
    public final int y;

    private Pt(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Contract(pure = true)
    public static Pt pt(double x, double y) {
        return new Pt(x, y);
    }

    @Contract(pure = true)
    public Pt interpolate(Pt p2, double t) { return computeTime(p2, t, this); }

    @Contract(pure = true)
    public Pt extrapolate(Pt p2, double t) { return computeTime(p2, t, p2); }

    @Contract(pure = true)
    private Pt computeTime(Pt p2, double t, Pt base) {
        checkNotNull(p2);

        final double d = distance(p2);
        checkState(d != 0);

        final double ux = (p2.x - x) / d;
        final double uy = (p2.y - y) / d;

        return pt(base.x + t * ux, base.y + t * uy);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "Pt{x=" + x + ", y=" + y + '}';
    }

    @Contract(pure = true)
    public Pt move(double dx, double dy) {
        return pt(x + dx, y + dy);
    }

    @Contract(pure = true)
    public Double distance(Pt other) {
        return sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
    }
}
