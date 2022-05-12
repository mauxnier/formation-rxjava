package fr.sii.rxjava.exercice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static fr.sii.rxjava.exercice.Ex250_ColoredLettersTest.verify;

public class Ex251_ColoredLetters2Test extends BaseTest {

    @Test
    @Disabled
    public void testCommands() throws Exception {
        testSetupFor(Ex251_ColoredLetters2.class);

        verify(this);
    }
}