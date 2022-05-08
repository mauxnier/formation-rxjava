package fr.sii.rxjava.util.cmds;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.util.Collection;

import static fr.sii.rxjava.util.Utils.imCopyOf;
import static fr.sii.rxjava.util.Utils.toImList;

public final class GroupCmd extends BaseCmd {

    private final Collection<Command> commands;

    public GroupCmd(Collection<Command> commands) {
        super(new GroupDrawing(commands.stream().map(Command::getDrawing).collect(toImList())));
        this.commands = commands;
    }

    @Contract(pure = true)
    public ImmutableList<Command> getCommands() { return imCopyOf(commands); }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        GroupCmd groupCmd = (GroupCmd) o;

        return commands.equals(groupCmd.commands);
    }

    @Override
    public int hashCode() {
        return commands.hashCode();
    }

    @Override
    public String toString() { return "GroupCmd{commands=" + commands + '}'; }

    private static class GroupDrawing implements Drawing {
        private final Drawings drawings;

        public GroupDrawing(ImmutableList<Drawing> drawings) {this.drawings = new Drawings(drawings);}

        @Override
        public void call(Graphics2D g) {
            drawings.drawAll(d -> d.call(g));
        }
    }
}
