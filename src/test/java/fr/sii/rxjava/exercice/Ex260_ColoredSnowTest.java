package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Pt;
import fr.sii.rxjava.util.cmds.AddLineCmd;
import fr.sii.rxjava.util.cmds.Command;
import fr.sii.rxjava.util.cmds.GroupCmd;
import fr.sii.rxjava.util.cmds.UniqCmd;
import javafx.scene.paint.Color;
import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.Collectors;

import static fr.sii.rxjava.exercice.Ex260_ColoredSnow.snowFlakeId;
import static fr.sii.rxjava.util.Cmd.removeUniq;
import static fr.sii.rxjava.util.Pt.pt;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class Ex260_ColoredSnowTest extends BaseTest {

    @Test
    @Ignore
    public void testCommands() throws Exception {
        testSetupFor(Ex260_ColoredSnow.class);

        assertStepWithoutValues();

        Pt p = pt(100, 100);

        inputPusher.pushMouseXY(p)
                .pushMouseLeftClick(1)
                .advanceTimeBy(60, SECONDS);

        java.util.List<Command> allCmds = testSubscriber.values();

        // Les 256 premières commandes sont des uniq(group(lines)).
        assertTrue(allCmds.stream()
                .limit(256)
                .allMatch(cmd -> cmd instanceof UniqCmd &&
                        ((UniqCmd) cmd).getCmd() instanceof GroupCmd &&
                        ((GroupCmd) ((UniqCmd) cmd).getCmd()).getCommands().stream()
                                .allMatch(c -> c instanceof AddLineCmd)));

        // Les couleurs des lines sont de plus en plus clair.
        assertTrue(fromIterable(allCmds.stream()
                .limit(256)
                .flatMap(cmd -> ((GroupCmd) ((UniqCmd) cmd).getCmd()).getCommands().stream())
                .collect(Collectors.toList()))
                .buffer(2, 1)
                .filter(cmds -> cmds.size() == 2)
                .reduce(0, (acc, cmds) -> {
                    Command c1 = cmds.get(0);
                    Command c2 = cmds.get(1);
                    Color col1 = ((AddLineCmd) c1).getColor();
                    Color col2 = ((AddLineCmd) c2).getColor();

                    return acc + (col1.getRed() < col2.getRed() ? 1 : 0 +
                            col1.getGreen() < col2.getGreen() ? 1 : 0 +
                            col1.getBlue() < col2.getBlue() ? 1 : 0);
                })
                .blockingGet() > 0);

        // La dernière commande est la suppression du flocon.
        assertEquals(removeUniq(snowFlakeId(p)), allCmds.get(allCmds.size() - 1));
    }
}
