package fr.sii.rxjava.exercice;

import org.junit.Test;

import static fr.sii.rxjava.exercice.Consts.GAGNE;
import static fr.sii.rxjava.exercice.Ex720_Pendu.*;
import static fr.sii.rxjava.util.Cmd.clear;
import static fr.sii.rxjava.util.Utils.imList;
import static io.reactivex.rxjava3.core.Observable.just;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex720_PenduTest extends BaseTest {

    static final String MOT = "abaac";

    @Test
    public void testWin() throws Exception {
        testSetupFor(Ex720_Pendu.class, testServ -> testServ.setWords(imList(MOT)));

        // Init
        assertStepValuesObs(text(MOT, ""));

        // 'A'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("a");
        assertStepValues(text(MOT, "a").toList().blockingGet());

        // 'Z'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("z");
        assertStepValues(captureDuring(gallows(), 5, SECONDS));

        // 'B'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("b");
        assertStepValuesObs(text(MOT, "ab"));

        // 'A' BIS
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("a");
        assertStepWithoutValues();

        // 'Y'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("y");
        assertStepValues(captureDuring(head(), 5, SECONDS));

        // 'C'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("c");
        assertStepValuesObs(text(MOT, "abc").concatWith(just(GAGNE)));
    }

    @Test
    public void testLoose() throws Exception {
        testSetupFor(Ex720_Pendu.class, testServ -> testServ.setWords(imList(MOT)));

        // Init
        assertStepValuesObs(text(MOT, ""));

        // 'A'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("a");
        assertStepValues(text(MOT, "a").toList().blockingGet());

        // 'Z'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("z");
        assertStepValues(captureDuring(gallows(), 5, SECONDS));

        // 'B'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("b");
        assertStepValuesObs(text(MOT, "ab"));

        // 'Y'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("y");
        assertStepValues(captureDuring(head(), 5, SECONDS));

        // 'X'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("x");
        assertStepValues(captureDuring(body(), 5, SECONDS));

        // 'W'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("w");
        assertStepValues(captureDuring(leftArm(), 5, SECONDS));

        // 'V'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("v");
        assertStepValues(captureDuring(rightArm(), 5, SECONDS));

        // 'A' BIS
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("a");
        assertStepWithoutValues();

        // 'U'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("u");
        assertStepValues(captureDuring(leftLeg(), 5, SECONDS));

        // 'T'
        inputPusher.advanceTimeBy(100, MILLISECONDS).pushKey("t");
        assertStepValues(captureDuring(rightLeg().concatWith(just(PERDU, clear())), 5, SECONDS));
    }
}
