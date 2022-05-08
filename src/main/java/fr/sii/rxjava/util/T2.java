package fr.sii.rxjava.util;

import org.jetbrains.annotations.Contract;
import rx.functions.Func1;

import java.util.Map.Entry;

public final class T2<A, B> {
    public final A _1;
    public final B _2;

    private T2(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Contract(pure = true)
    public static <A, B> T2<A, B> fromEntry(Entry<A, B> e) { return t2(e.getKey(), e.getValue()); }

    @Contract(pure = true)
    public static <A, B> T2<A, B> t2(A a, B b) { return new T2<>(a, b); }

    @Contract(pure = true)
    public <R, U> T2<R, U> map(Func1<A, R> fa, Func1<B, U> fb) { return t2(fa.call(_1), fb.call(_2)); }

    @Contract(pure = true)
    public <R> T2<R, B> mapA(Func1<A, R> f) { return t2(f.call(_1), _2); }

    @Contract(pure = true)
    public <R> T2<A, R> mapB(Func1<B, R> f) { return t2(_1, f.call(_2)); }

    @Contract(pure = true)
    public A _1() { return _1; }

    @Contract(pure = true)
    public B _2() { return _2; }

    @Contract(pure = true)
    public <T> T2<T, B> _1(T newA) { return t2(newA, _2); }

    @Contract(pure = true)
    public <T> T2<A, T> _2(T newB) { return t2(_1, newB); }

    @Override
    public String toString() { return "T2{_1=" + _1 + ", _2=" + _2 + '}'; }
}
