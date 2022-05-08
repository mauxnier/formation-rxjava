package fr.sii.rxjava.exercice;

import com.google.common.collect.ImmutableList;
import fr.sii.rxjava.util.cmds.Command;
import org.junit.Test;

import static fr.sii.rxjava.exercice.Ex710_NumbersLikeMusicScore.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Ex710_NumbersLikeMusicScoreTest extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex710_NumbersLikeMusicScore.class);

        inputPusher.pushKey("4")
                .advanceTimeBy(1, SECONDS)
                .pushKey("5")
                .advanceTimeBy(1, SECONDS)
                .pushKey("4")
                .advanceTimeBy(2, SECONDS)
                .pushKey("6")
                .advanceTimeBy(1, SECONDS)
                .pushKey("4");

        System.out.println("------------------");

        GroupeInfo group4 = new GroupeInfo(0_000L, 1);
        GroupeInfo group5 = new GroupeInfo(1_000L, 2);
        GroupeInfo group6 = new GroupeInfo(4_000L, 3);

        assertStepValues(ImmutableList.<Command>builder()
                .add(groupTxt(4))
                .addAll(dot(4, group4, new ValInfo(0L, 0L, 1)))
                .add(groupTxt(5))
                .addAll(dot(5, group5, new ValInfo(0L, 0L, 1)))
                .addAll(dot(4, group4, new ValInfo(2_000L, 2_000L, 2)))
                .add(groupTxt(6))
                .addAll(dot(6, group6, new ValInfo(0L, 0L, 1)))
                .addAll(dot(4, group4, new ValInfo(5_000L, 3_000L, 3)))
                .build());
        testSubscriber.assertNoTerminalEvent();
    }
}