package fr.sii.rxjava.exercice;

import org.junit.Test;

import static java.util.stream.Collectors.groupingBy;

public class Ex410_ActorsWithCommonDirectorTestV2 extends BaseTest {

    @Test
    public void testCommands() throws Exception {
        testSetupFor(Ex410_ActorsWithCommonDirectorV2.class);

        Ex410_ActorsWithCommonDirectorTest.check(this, inputPusher);
    }
}