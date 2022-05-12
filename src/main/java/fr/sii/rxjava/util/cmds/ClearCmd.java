package fr.sii.rxjava.util.cmds;

import static fr.sii.rxjava.util.Cmd.addLog;
import static javafx.scene.paint.Color.RED;

public final class ClearCmd implements Command {
    public final static ClearCmd INSTANCE = new ClearCmd();

    private ClearCmd() {
    }

    @Override
    public Drawings apply(Drawings drawings, Drawing drawing) {
        return addLog("Cleared", RED).apply(Drawings.empty(), drawing);
    }

    @Override
    public String toString() {
        return "ClearCmd{}";
    }
}
