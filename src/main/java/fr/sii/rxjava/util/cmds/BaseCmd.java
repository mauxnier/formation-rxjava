package fr.sii.rxjava.util.cmds;

import javafx.scene.paint.Color;

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
    public Drawings apply(Drawings drawings, Drawing drawing) {
        return drawings.append(drawing);
    }

    protected static String colorHex(Color c) {
        return  String.format( "#%02X%02X%02X",
                (int)( c.getRed() * 255 ),
                (int)( c.getGreen() * 255 ),
                (int)( c.getBlue() * 255 ) );
    }
}
