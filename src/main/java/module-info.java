module formation.rxjava {
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.common;
    requires org.jetbrains.annotations;
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;

    exports fr.sii.rxjava;
    exports fr.sii.rxjava.exercice;
    exports fr.sii.rxjava.util;
    exports fr.sii.rxjava.util.cmds;
}