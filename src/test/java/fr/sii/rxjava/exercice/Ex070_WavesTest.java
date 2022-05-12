package fr.sii.rxjava.exercice;

import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.exercice.Ex070_Waves.wave;
import static fr.sii.rxjava.util.Pt.pt;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex070_WavesTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex070_Waves.class);

        assertStepWithoutValues();

        inputPusher
                .pushMouseXY(100, 100)
                .pushMouseLeftClick(1)
                .pushMouseXY(100, 150)
                .pushMouseLeftClick(1);

        assertStepValues(captureDuring(wave(pt(100, 100), 50.0), 10, SECONDS));
    }
}