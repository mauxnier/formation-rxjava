package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.cmds.Command;
import javafx.scene.input.KeyCode;

import static fr.sii.rxjava.util.Cmd.addLog;

public interface Consts {

    Command PERDU = addLog("Perdu :-(");
    Command GAGNE = addLog("Gagné !!");

    Command YES = addLog("Yes");
    Command NO = addLog("No");

    int BACKSPACE = KeyCode.BACK_SPACE.getCode();
}
