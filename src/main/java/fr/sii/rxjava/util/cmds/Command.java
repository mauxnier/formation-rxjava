package fr.sii.rxjava.util.cmds;

import rx.functions.Func2;

@FunctionalInterface
public interface Command extends Func2<Drawings, Drawing, Drawings> {

    Drawing EMPTY_DRAWING = ctx -> {
    };

    default Drawing getDrawing() {
        return EMPTY_DRAWING;
    }
}
