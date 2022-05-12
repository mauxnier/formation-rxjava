package fr.sii.rxjava.util.cmds;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.collect.ImmutableList.builder;
import static fr.sii.rxjava.util.Utils.imAppend;
import static fr.sii.rxjava.util.Utils.imList;

public final class Drawings {

    private final ImmutableList<Drawing> drawings;

    Drawings(ImmutableList<Drawing> drawings) {
        this.drawings = drawings;
    }

    @Contract(pure = true)
    public static Drawings empty() {
        return new Drawings(imList());
    }

    @Contract(pure = true)
    public Drawings append(Drawing newDrawing) {
        return new Drawings(imAppend(drawings, newDrawing));
    }

    @Contract(pure = true)
    public Drawings filter(Predicate<Drawing> predicate) {
        return filterAndAppend(predicate, null);
    }

    @Contract(pure = true)
    public Drawings filterAndAppend(Predicate<Drawing> predicate, Drawing newDrawing) {
        Builder<Drawing> builder = builder();

        drawings.stream().filter(predicate).forEach(builder::add);

        if (newDrawing != null) {
            builder.add(newDrawing);
        }

        return new Drawings(builder.build());
    }

    @Contract(pure = true)
    public List<Drawing> values() {
        return drawings;
    }

    public void drawAll(Consumer<Drawing> drawAction) {
        drawings.forEach(drawAction::accept);
    }
}
