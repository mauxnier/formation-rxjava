package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.Inputs;
import fr.sii.rxjava.util.MainFrame;
import fr.sii.rxjava.util.Services;
import fr.sii.rxjava.util.cmds.Command;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

import static fr.sii.rxjava.util.Cmd.*;
import static fr.sii.rxjava.util.MainFrame.startApp;
import static fr.sii.rxjava.util.Pt.pt;
import static java.util.Collections.singletonList;
import static rx.Observable.merge;
import static rx.Observable.range;

public class Ex130_ClickCountBySlice implements MainFrame.App {

    static final int GROUP_SIZE = 50;
    static final int GROUP_COUNT = 10;

    public static void main(String... args) { startApp(new Ex130_ClickCountBySlice()); }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
                    
    }

    static Command groupText(int groupId, int sum) {return uniq("group-id-" + groupId, addText(groupId * GROUP_SIZE + 10, 300, "" + sum));}

    static Command groupLine(int group) {return addLine((group + 1) * GROUP_SIZE, 0, (group + 1) * GROUP_SIZE, 300);}

    @Override
    public List<String> description() {
        return singletonList("Deplacez vous dans les colonnes et frappez sur le clavier...");
    }
}