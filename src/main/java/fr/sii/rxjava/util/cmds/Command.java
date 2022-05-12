package fr.sii.rxjava.util.cmds;

import java.util.function.BiFunction;

public interface Command extends BiFunction<Drawings, Drawing, Drawings> {

    Drawing EMPTY_DRAWING = ctx -> {
    };

    default Drawing getDrawing() {
        return EMPTY_DRAWING;
    }
}
