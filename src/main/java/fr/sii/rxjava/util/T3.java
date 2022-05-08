package fr.sii.rxjava.util;

import org.jetbrains.annotations.Contract;
import rx.functions.Func1;

public final class T3<A, B, C> {
    public final A _1;
    public final B _2;
    public final C _3;

    private T3(A _1, B _2, C _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Contract(pure = true)
    public static <A, B, C> T3<A, B, C> t3(T2<A, B> t2, C c) { return t3(t2._1, t2._2, c); }

    @Contract(pure = true)
    public static <A, B, C> T3<A, B, C> t3(A a, B b, C c) { return new T3<>(a, b, c); }

    @Contract(pure = true)
    public <R, U, V> T3<R, U, V> map(Func1<A, R> fa, Func1<B, U> fb, Func1<C, V> fc) { return t3(fa.call(_1), fb.call(_2), fc.call(_3)); }

    @Contract(pure = true)
    public <R> T3<R, B, C> mapA(Func1<A, R> f) { return t3(f.call(_1), _2, _3); }

    @Contract(pure = true)
    public <R> T3<A, R, C> mapB(Func1<B, R> f) { return t3(_1, f.call(_2), _3); }

    @Contract(pure = true)
    public <R> T3<A, B, R> mapC(Func1<C, R> f) { return t3(_1, _2, f.call(_3)); }

    @Contract(pure = true)
    public A _1() { return _1; }

    @Contract(pure = true)
    public B _2() { return _2; }

    @Contract(pure = true)
    public C _3() { return _3; }

    @Contract(pure = true)
    public <T> T3<T, B, C> _1(T newA) { return t3(newA, _2, _3); }

    @Contract(pure = true)
    public <T> T3<A, T, C> _2(T newB) { return t3(_1, newB, _3); }

    @Contract(pure = true)
    public <T> T3<A, B, T> _3(T newC) { return t3(_1, _2, newC); }

    @Override
    public String toString() { return "T2{_1=" + _1 + ", _2=" + _2 + ", _3=" + _3 + '}'; }
}
