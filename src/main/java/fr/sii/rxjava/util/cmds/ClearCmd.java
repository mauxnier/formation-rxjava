package fr.sii.rxjava.util.cmds;

import static fr.sii.rxjava.util.Cmd.addLog;
import static java.awt.Color.RED;

public final class ClearCmd implements Command {
    public final static ClearCmd INSTANCE = new ClearCmd();

    private ClearCmd() {
    }

    @Override
    public Drawings call(Drawings drawings, Drawing drawing) {
        return addLog("Cleared", RED).call(Drawings.empty(), drawing);
    }

    @Override
    public String toString() {
        return "ClearCmd{}";
    }
}
