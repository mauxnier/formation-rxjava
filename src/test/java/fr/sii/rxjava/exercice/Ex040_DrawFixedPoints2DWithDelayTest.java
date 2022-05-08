package fr.sii.rxjava.exercice;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D.PTS_BY_SIDE;
import static fr.sii.rxjava.exercice.Ex040_DrawFixedPoints2DWithDelay.DURATION_MS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.defer;
import static rx.Observable.just;

public class Ex040_DrawFixedPoints2DWithDelayTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex040_DrawFixedPoints2DWithDelay.class, testServ -> {
            AtomicInteger index = new AtomicInteger();

            testServ.setRandoms(defer(() -> {
                int i = index.incrementAndGet();

                if (i > 2 * PTS_BY_SIDE) { return just(1.0); }
                if (i > PTS_BY_SIDE) { return just(0.5); }
                return just(0.0);
            }));
        });

        testScheduler.advanceTimeBy(0, MILLISECONDS);
        testSubscriber.assertValueCount(PTS_BY_SIDE);

        testScheduler.advanceTimeBy((long) (DURATION_MS * 0.5) + 100, MILLISECONDS);
        testSubscriber.assertValueCount(2 * PTS_BY_SIDE);

        testScheduler.advanceTimeBy(DURATION_MS, MILLISECONDS);
        testSubscriber.assertValueCount(PTS_BY_SIDE * PTS_BY_SIDE);

        testScheduler.advanceTimeBy(DURATION_MS + 3_000, MILLISECONDS);
        assertCompleted();
    }
}