package fr.sii.rxjava.util.cmds;

import org.jetbrains.annotations.Contract;

public final class NoOpCmd extends BaseCmd {

    public NoOpCmd() {
        super(EMPTY_DRAWING);
    }

    @Override
    @Contract(pure = true)
    public Drawings apply(Drawings drawings, Drawing newDrawing) {
        return drawings;
    }

    @Override
    public String toString() {
        return "NoOpCmd{}";
    }
}
