package fr.sii.rxjava.exercice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.exercice.Ex130_ClickCountBySlice.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

public class Ex130_ClickCountBySliceTest extends BaseTest {

    @Test
    @Disabled
    public void testCommands() throws Exception {
        testSetupFor(Ex130_ClickCountBySlice.class);

        assertStepValues(
                range(0, GROUP_COUNT).mapToObj(i -> groupLine(i)),
                range(0, GROUP_COUNT).mapToObj(i -> groupText(i, 0)));

        {
            String keys = "12345";
            inputPusher.advanceTimeBy(1, SECONDS)
                    .pushMouseXY(2 * GROUP_SIZE, 10)
                    .pushKey(keys);
            assertStepValues(range(0, keys.length()).mapToObj(i -> groupText(2, 1 + i)));
        }

        {
            String keys = "123";
            inputPusher.advanceTimeBy(1, SECONDS)
                    .pushMouseXY(3 * GROUP_SIZE, 10)
                    .pushKey(keys);
            assertStepValues(range(0, keys.length()).mapToObj(i -> groupText(3, 1 + i)));
        }

        {
            String keys = "6789";
            inputPusher.advanceTimeBy(1, SECONDS)
                    .pushMouseXY(2 * GROUP_SIZE, 10)
                    .pushKey(keys);
            assertStepValues(range(0, keys.length()).mapToObj(i -> groupText(2, 6 + i)));
        }
    }
}