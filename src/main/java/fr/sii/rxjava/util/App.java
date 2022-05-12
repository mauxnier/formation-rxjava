package fr.sii.rxjava.util;

import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;

import java.util.List;

public interface App {
    Observable<Command> commands(Inputs in, Services services, Scheduler scheduler);

    default List<String> description(){
        return List.of();
    }
}
