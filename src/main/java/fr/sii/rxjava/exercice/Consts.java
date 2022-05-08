package fr.sii.rxjava.exercice;

import fr.sii.rxjava.util.cmds.Command;

import java.awt.event.KeyEvent;

import static fr.sii.rxjava.util.Cmd.addLog;

public interface Consts {

    Command PERDU = addLog("Perdu :-(");
    Command GAGNE = addLog("Gagné !!");

    Command YES = addLog("Yes");
    Command NO = addLog("No");

    int BACKSPACE = KeyEvent.VK_BACK_SPACE;
}
