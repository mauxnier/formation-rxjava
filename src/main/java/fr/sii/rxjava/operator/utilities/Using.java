package fr.sii.rxjava.operator.utilities;

import rx.Observable;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.io.File.createTempFile;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.*;

public class Using {
    public static void main(String... args) {
        Observable<String> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5)
                .flatMap(i -> using(
                        () -> {
                            try {
                                File tempFile = createTempFile("using-operator-", "-for-value-" + i);
                                System.out.println("File " + tempFile + " created.");
                                return tempFile;
                            } catch (IOException ex) {
                                throw propagate(ex);
                            }
                        },

                        f -> {
                            System.out.println("File " + f + " ready to use...");
                            return just(f).delay(2, SECONDS);
                        },

                        f -> {
                            f.delete();
                            System.out.println("File " + f + " deleted!");
                        })
                        .flatMap(f -> interval(2, SECONDS)
                                .take(3)
                                .map(v -> "File used : " + f.getAbsolutePath() + " / " + v)));

        createObserver(10, 350, "Observer", GREEN, ints1);
    }
}
