package fr.sii.rxjava.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Utils {

    @Contract(pure = true)
    public static <T> ObservableTransformer<T, T> slow() {
        return o -> o.concatMap(v -> Observable.just(v).delay(1, SECONDS));
    }

    @Contract(pure = true)
    public static <T> ObservableTransformer<T, T> dataSlow() {
        return o -> o.concatMap(v -> Observable.just(v).delay(100, MILLISECONDS));
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imList(T... values) {
        return imCopyOf(asList(values));
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imList() {
        return ImmutableList.of();
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imListOf(Class<T> c) {
        return ImmutableList.of();
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imCopyOf(Iterable<T> l) {
        return ImmutableList.copyOf(l);
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imPrepend(T t, Iterable<T> l) {
        return ImmutableList.<T>builder().add(t).addAll(l).build();
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imAppend(Iterable<T> l, T t) {
        return ImmutableList.<T>builder().addAll(l).add(t).build();
    }

    @Contract(pure = true)
    public static <T> ImmutableList<T> imConcat(Iterable<T> first, Iterable<T>... others) {
        Builder<T> builder = ImmutableList.<T>builder().addAll(first);

        stream(others)
                .flatMap(iter -> StreamSupport.stream(iter.spliterator(), false))
                .forEach(builder::add);

        return builder.build();
    }

    @Contract(pure = true)
    public static <T> Collector<T, ?, ImmutableList<T>> toImList() {
        return Collector.<T, ImmutableList.Builder<T>,ImmutableList<T>>of(
                ImmutableList::builder,
                Builder::add,
                (b1, b2) -> b1.addAll(b2.build()),
                Builder::build);
    }

    @Contract(pure = true)
    public static <T> T[] concat(T[] t1, T[] t2, IntFunction<T[]> generator) {
        return concat(Stream.of(t1, t2), generator);
    }

    @Contract(pure = true)
    public static <T> T[] concat(Stream<T[]> s, IntFunction<T[]> generator) {
        return s.flatMap(Arrays::stream).toArray(generator);
    }
}