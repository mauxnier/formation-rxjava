package fr.sii.rxjava.util.rxplayground;

import org.jetbrains.annotations.Contract;
import rx.functions.Func1;
import rx.functions.Func2;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.google.common.base.Strings.repeat;
import static fr.sii.rxjava.util.Utils.imList;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;
import static java.lang.ThreadLocal.withInitial;
import static java.util.stream.Collectors.joining;

public class Data<T> {

    private static final ThreadLocal<SimpleDateFormat> SDF = withInitial(() -> new SimpleDateFormat("HH:mm:ss.SSS"));

    public final String createdOn;
    public final long creationTimestamp;
    public final T value;
    public final List<Data<?>> froms;

    private Data(T value, List<Data<?>> froms) {
        this.createdOn = currentThread().getName();
        this.creationTimestamp = currentTimeMillis();
        this.value = value;
        this.froms = froms;
    }

    public static <T> Data<T> data(final T value) { return new Data<>(value, imList()); }

    public T value() { return value; }

    @Contract(pure = true)
    public <R> Data<R> map(Func1<T, R> f) { return new Data(f.call(value), imList(this)); }

    @Contract(pure = true)
    public <U, R> Data<R> mapWith(Data<U> b, Func2<T, U, R> f) { return new Data(f.call(value, b.value), imList(this, b)); }

    @Override
    public String toString() { return details(0);}

    public String details(int indent) {
        return "\n" + repeat("  ", indent) + "[" + value + ", " + SDF.get().format(creationTimestamp) + ", " + createdOn + "]"
                + froms.stream().map(v -> v.details(indent + 1)).collect(joining());
    }
}
