package fr.sii.rxjava.operator.combining;

import rx.Observable;

import java.awt.*;

import static fr.sii.rxjava.util.rxplayground.GuiRx.createColdIntsObservable;
import static fr.sii.rxjava.util.rxplayground.GuiRx.createObserver;
import static java.awt.Color.*;
import static java.lang.Math.random;

public class Join {

    public static void main(String... args) {
        Observable<Integer> ints1 = createColdIntsObservable(10, 10, "Ints 1", RED, 0, 5);

        Observable<Integer> ints2 = createColdIntsObservable(200, 10, "Ints 2", ORANGE, 10, 15);

        Observable<String> joined = ints1.join(
                ints2,
                l -> createColdIntsObservable(10, 350, "End", randomOf(RED), l, l),
                r -> createColdIntsObservable(200, 350, "End", randomOf(ORANGE), r, r),
                (l, r) -> l + " / " + r);

        createObserver(400, 10, "Observer", GREEN, joined);
    }

    static Color randomOf(Color c) {
        double coef = 0.5 + 0.5 * random();
        return new Color((int) (c.getRed() * coef), (int) (c.getGreen() * coef), (int) (c.getBlue() * coef));
    }
}
