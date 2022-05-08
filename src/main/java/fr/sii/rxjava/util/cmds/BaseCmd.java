package fr.sii.rxjava.util.cmds;

import java.awt.*;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public abstract class BaseCmd implements Command {
    private final Drawing drawing;

    public BaseCmd(Drawing drawing) {
        this.drawing = requireNonNull(drawing);
    }

    public Drawing getDrawing() {
        return drawing;
    }

    @Override
    public Drawings call(Drawings drawings, Drawing drawing) {
        return drawings.append(drawing);
    }

    protected static String colorHex(Color c) {
        return format("#%06X", (0xFFFFFF & c.getRGB()));
    }
}
